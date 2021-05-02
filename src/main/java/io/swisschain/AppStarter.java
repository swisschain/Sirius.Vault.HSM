package io.swisschain;

import io.swisschain.config.loaders.ConfigLoader;
import io.swisschain.crypto.address.generation.AddressGeneratorFactory;
import io.swisschain.crypto.asymmetric.AsymmetricEncryptionService;
import io.swisschain.crypto.transactions.TransactionSignerFactory;
import io.swisschain.crypto.transactions.TransactionValidatorFactory;
import io.swisschain.domain.document.GuardianKey;
import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.domain.wallet.WalletGenerationRequest;
import io.swisschain.isAlive.IsAliveService;
import io.swisschain.repositories.DbConnectionFactory;
import io.swisschain.repositories.DbMigration;
import io.swisschain.repositories.wallets.WalletRepositoryImp;
import io.swisschain.repositories.wallets.WalletRepositoryRetryDecorator;
import io.swisschain.services.*;
import io.swisschain.signers.DocumentValidator;
import io.swisschain.signers.SmartContractDeploymentSigner;
import io.swisschain.signers.TransferSigner;
import io.swisschain.sirius.vaultApi.ChannelFactory;
import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.tasks.*;
import io.swisschain.common.AppVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AppStarter {
  private static final Logger logger = LogManager.getLogger();

  public static void main(String[] args) throws InterruptedException {
    var config = ConfigLoader.loadConfig();

    if (config == null) {
      logger.error("Can not load config");
      return;
    }

    if (!DbMigration.migrateDatabase(
        config.db.url, config.db.user, config.db.password, config.db.schema)) {
      return;
    }

    // API clients

    var vaultApiClient =
        new VaultApiClient(
            ChannelFactory.create(
                config.clients.vaultApi.host,
                config.clients.vaultApi.port,
                config.clients.vaultApi.ssl,
                config.clients.vaultApi.apiKey));

    // Repositories

    var connectionFactory =
        new DbConnectionFactory(
            config.db.url, config.db.user, config.db.password, config.db.schema);
    var walletRepository =
        new WalletRepositoryRetryDecorator(new WalletRepositoryImp(connectionFactory));

    // Services

    var hostProcessId = getHostProcessId();

    var asymmetricEncryptionService = new AsymmetricEncryptionService();

    var jsonSerializer = new JsonSerializer();

    var addressGeneratorFactory = new AddressGeneratorFactory(config);
    var transactionSignerFactory = new TransactionSignerFactory(config);
    var transactionValidatorFactory = new TransactionValidatorFactory(jsonSerializer);

    var smartContractDeploymentApiService =
        new SmartContractDeploymentApiServiceRetryDecorator(
            new SmartContractDeploymentApiServiceImp(vaultApiClient, hostProcessId));

    var transferApiService =
        new TransferApiServiceRetryDecorator(
            new TransferApiServiceImp(vaultApiClient, hostProcessId));

    var walletApiService =
        new WalletApiServiceRetryDecorator(new WalletApiServiceImp(vaultApiClient, hostProcessId));

    var guardianKey = new GuardianKey(config.keys.guardian.publicKey);

    var documentValidator = new DocumentValidator(asymmetricEncryptionService, guardianKey);

    var walletService =
        new WalletService(walletRepository, walletApiService, addressGeneratorFactory);

    // Signers

    var smartContractDeploymentSigner =
        new SmartContractDeploymentSigner(
            smartContractDeploymentApiService,
            documentValidator,
            transactionSignerFactory,
            transactionValidatorFactory,
            walletRepository);

    var transferSigner =
        new TransferSigner(
            transferApiService,
            documentValidator,
            transactionSignerFactory,
            transactionValidatorFactory,
            walletRepository);

    // Thread pools

    var apiThreadPool = Executors.newScheduledThreadPool(4);
    var smartContractDeploymentSigningThreadPool =
        Executors.newFixedThreadPool(
            config.tasks.smartContractDeploymentSigningConsumer.threadsCount);
    var transferSigningThreadPool =
        Executors.newFixedThreadPool(config.tasks.transferSigningConsumer.threadsCount);
    var walletConsumersThreadPool =
        Executors.newFixedThreadPool(config.tasks.walletGenerationConsumer.threadsCount);

    // Tasks

    var transferRequestQueue =
        new ArrayBlockingQueue<TransactionSigningRequest>(
            config.tasks.transferSigningPublisher.queueSize);

    var smartContractDeploymentRequestQueue =
        new ArrayBlockingQueue<TransactionSigningRequest>(
            config.tasks.smartContractDeploymentSigningPublisher.queueSize);

    var walletRequestQueue =
        new ArrayBlockingQueue<WalletGenerationRequest>(
            config.tasks.walletGenerationPublisher.queueSize);

    for (var i = 0; i < config.tasks.smartContractDeploymentSigningConsumer.threadsCount; i++) {
      smartContractDeploymentSigningThreadPool.execute(
          new SmartContractDeploymentConsumerTask(
              smartContractDeploymentRequestQueue, smartContractDeploymentSigner));
    }

    for (var i = 0; i < config.tasks.transferSigningConsumer.threadsCount; i++) {
      transferSigningThreadPool.execute(
          new TransferRequestConsumerTask(transferRequestQueue, transferSigner));
    }

    for (var i = 0; i < config.tasks.walletGenerationConsumer.threadsCount; i++) {
      walletConsumersThreadPool.execute(
          new WalletRequestConsumerTask(walletRequestQueue, walletService));
    }

    apiThreadPool.scheduleWithFixedDelay(
        new SmartContractDeploymentPublisherTask(
            smartContractDeploymentApiService, smartContractDeploymentRequestQueue),
        0,
        config.tasks != null
                && config.tasks.smartContractDeploymentSigningPublisher.periodInSeconds > 0
            ? config.tasks.smartContractDeploymentSigningPublisher.periodInSeconds
            : 1,
        TimeUnit.SECONDS);

    apiThreadPool.scheduleWithFixedDelay(
        new TransferRequestPublisherTask(transferApiService, transferRequestQueue),
        0,
        config.tasks != null && config.tasks.transferSigningPublisher.periodInSeconds > 0
            ? config.tasks.transferSigningPublisher.periodInSeconds
            : 1,
        TimeUnit.SECONDS);

    apiThreadPool.scheduleWithFixedDelay(
        new WalletRequestPublisherTask(walletApiService, walletRequestQueue),
        0,
        config.tasks != null && config.tasks.walletGenerationPublisher.periodInSeconds > 0
            ? config.tasks.walletGenerationPublisher.periodInSeconds
            : 1,
        TimeUnit.SECONDS);

    apiThreadPool.scheduleWithFixedDelay(
        new MonitoringTask(vaultApiClient),
        0,
        config.tasks != null && config.tasks.monitoringPeriodInSeconds > 0
            ? config.tasks.monitoringPeriodInSeconds
            : 1,
        TimeUnit.SECONDS);

    initShutdownHook();

    new IsAliveService(5000).start();

    while (true) {
      Thread.sleep(10000);
    }
  }

  static String getHostProcessId() {
    var hostName = "";

    try {
      hostName = InetAddress.getLocalHost().getHostName();
    } catch (Exception exception) {
      logger.error("Can not get host name", exception);
    }

    return String.format("%s-%d", hostName, ProcessHandle.current().pid());
  }

  static void initShutdownHook() {
    Runtime.getRuntime()
        .addShutdownHook(new Thread(() -> logger.info("Stopping " + AppVersion.NAME)));
  }
}
