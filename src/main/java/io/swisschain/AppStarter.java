package io.swisschain;

import com.sun.net.httpserver.HttpServer;
import io.swisschain.common.AppVersion;
import io.swisschain.crypto.address.generation.AddressGeneratorFactory;
import io.swisschain.crypto.asymmetric.AsymmetricEncryptionService;
import io.swisschain.crypto.symmetric.SymmetricEncryptionService;
import io.swisschain.crypto.transactions.TransactionSignerFactory;
import io.swisschain.crypto.transactions.TransactionValidatorFactory;
import io.swisschain.domain.document.GuardianKey;
import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.domain.wallet.WalletGenerationRequest;
import io.swisschain.http_handlers.IsAliveHttpHandler;
import io.swisschain.http_handlers.SettingsHttpHandler;
import io.swisschain.repositories.DbConnectionFactory;
import io.swisschain.repositories.DbMigration;
import io.swisschain.repositories.wallets.WalletRepositoryImp;
import io.swisschain.repositories.wallets.WalletRepositoryRetryDecorator;
import io.swisschain.services.*;
import io.swisschain.signers.DocumentValidator;
import io.swisschain.signers.TransactionSigner;
import io.swisschain.sirius.vaultApi.ChannelFactory;
import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.tasks.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AppStarter {
  private static final Logger logger = LogManager.getLogger();

  public static void main(String[] args) throws Exception {
    int httpPort = 5000;
    var httpPortValue = System.getenv("HTTP_PORT");
    var settingsUrl = System.getenv("SETTINGS_URL");

    if (httpPortValue != null && !httpPortValue.isEmpty()) {
      try {
        httpPort = Integer.parseInt(httpPortValue);
      } catch (NumberFormatException exception) {
        logger.error("Invalid http port value");
      }
    }

    logger.info(String.format("HTTP PORT: %d", httpPort));
    logger.info(String.format("Settings URL: %s", settingsUrl));

    var jsonSerializer = new JsonSerializer();
    var asymmetricEncryptionService = new AsymmetricEncryptionService();
    var symmetricEncryptionService = new SymmetricEncryptionService();
    var settingsKeyService = new SettingsKeysService(asymmetricEncryptionService, jsonSerializer);

    settingsKeyService.init();

    logger.info(String.format("Settings Public Key: %s", settingsKeyService.getPublic()));

    var settingsService =
            new SettingsService(
                    asymmetricEncryptionService,
                    symmetricEncryptionService,
                    settingsKeyService,
                    jsonSerializer,
                    settingsUrl);

    logger.info("HTTP server starting...");

    try {
      final var server = HttpServer.create();
      server.bind(new InetSocketAddress(httpPort), 0);
      server.createContext("/api/isalive", new IsAliveHttpHandler(jsonSerializer));
      server.createContext(
              "/api/settings", new SettingsHttpHandler(settingsService, settingsKeyService, jsonSerializer));
      server.start();
    } catch (IOException exception) {
      logger.info("Unable to start HTTP server.", exception);
      return;
    }

    logger.info("HTTP server started...");

    logger.info("Settings loading...");

    var appConfig = settingsService.load();

    if (appConfig == null) {
      logger.error("Can not load configuration");
      return;
    }

    if (!DbMigration.migrateDatabase(
        appConfig.db.url, appConfig.db.user, appConfig.db.password, appConfig.db.schema)) {
      return;
    }

    // API clients

    var vaultApiClient =
        new VaultApiClient(
            ChannelFactory.create(
                appConfig.clients.vaultApi.host,
                appConfig.clients.vaultApi.port,
                appConfig.clients.vaultApi.ssl,
                appConfig.clients.vaultApi.apiKey));

    // Repositories

    var connectionFactory =
        new DbConnectionFactory(
            appConfig.db.url, appConfig.db.user, appConfig.db.password, appConfig.db.schema);
    var walletRepository =
        new WalletRepositoryRetryDecorator(new WalletRepositoryImp(connectionFactory));

    // Services

    var hostProcessId = getHostProcessId();

    var addressGeneratorFactory = new AddressGeneratorFactory(appConfig);
    var transactionSignerFactory = new TransactionSignerFactory(appConfig);
    var transactionValidatorFactory = new TransactionValidatorFactory(jsonSerializer);

    var smartContractDeploymentApiService =
        new TransactionSigningApiServiceRetryDecorator(
            new SmartContractDeploymentApiServiceImp(vaultApiClient, hostProcessId));

    var smartContractInvocationApiService =
        new TransactionSigningApiServiceRetryDecorator(
            new SmartContractInvocationApiServiceImp(vaultApiClient, hostProcessId));

    var transferApiService =
        new TransactionSigningApiServiceRetryDecorator(
            new TransferApiServiceImp(vaultApiClient, hostProcessId));

    var walletApiService =
        new WalletApiServiceRetryDecorator(new WalletApiServiceImp(vaultApiClient, hostProcessId));

    var guardianKey = new GuardianKey(appConfig.keys.guardian.publicKey);

    var documentValidator = new DocumentValidator(asymmetricEncryptionService, guardianKey);

    var walletService =
        new WalletService(walletRepository, walletApiService, addressGeneratorFactory);

    // Signers

    var smartContractDeploymentSigner =
        new TransactionSigner(
            smartContractDeploymentApiService,
            documentValidator,
            transactionSignerFactory,
            transactionValidatorFactory,
            walletRepository);

    var smartContractInvocationSigner =
        new TransactionSigner(
            smartContractInvocationApiService,
            documentValidator,
            transactionSignerFactory,
            transactionValidatorFactory,
            walletRepository);

    var transferSigner =
        new TransactionSigner(
            transferApiService,
            documentValidator,
            transactionSignerFactory,
            transactionValidatorFactory,
            walletRepository);

    // Thread pools

    var apiThreadPool = Executors.newScheduledThreadPool(5);
    var smartContractDeploymentSigningThreadPool =
        Executors.newFixedThreadPool(
            appConfig.tasks.smartContractDeploymentSigningConsumer.threadsCount);
    var smartContractInvocationSigningThreadPool =
        Executors.newFixedThreadPool(
            appConfig.tasks.smartContractInvocationSigningConsumer.threadsCount);
    var transferSigningThreadPool =
        Executors.newFixedThreadPool(appConfig.tasks.transferSigningConsumer.threadsCount);
    var walletConsumersThreadPool =
        Executors.newFixedThreadPool(appConfig.tasks.walletGenerationConsumer.threadsCount);

    // Tasks

    var transferRequestQueue =
        new ArrayBlockingQueue<TransactionSigningRequest>(
            appConfig.tasks.transferSigningPublisher.queueSize);

    var smartContractDeploymentRequestQueue =
        new ArrayBlockingQueue<TransactionSigningRequest>(
            appConfig.tasks.smartContractDeploymentSigningPublisher.queueSize);

    var smartContractInvocationRequestQueue =
        new ArrayBlockingQueue<TransactionSigningRequest>(
            appConfig.tasks.smartContractInvocationSigningPublisher.queueSize);

    var walletRequestQueue =
        new ArrayBlockingQueue<WalletGenerationRequest>(
            appConfig.tasks.walletGenerationPublisher.queueSize);

    for (var i = 0; i < appConfig.tasks.smartContractDeploymentSigningConsumer.threadsCount; i++) {
      smartContractDeploymentSigningThreadPool.execute(
          new SmartContractDeploymentConsumerTask(
              smartContractDeploymentRequestQueue, smartContractDeploymentSigner));
    }

    for (var i = 0; i < appConfig.tasks.smartContractInvocationSigningConsumer.threadsCount; i++) {
      smartContractInvocationSigningThreadPool.execute(
          new SmartContractInvocationConsumerTask(
              smartContractInvocationRequestQueue, smartContractInvocationSigner));
    }

    for (var i = 0; i < appConfig.tasks.transferSigningConsumer.threadsCount; i++) {
      transferSigningThreadPool.execute(
          new TransferRequestConsumerTask(transferRequestQueue, transferSigner));
    }

    for (var i = 0; i < appConfig.tasks.walletGenerationConsumer.threadsCount; i++) {
      walletConsumersThreadPool.execute(
          new WalletRequestConsumerTask(walletRequestQueue, walletService));
    }

    apiThreadPool.scheduleWithFixedDelay(
        new SmartContractDeploymentPublisherTask(
            smartContractDeploymentApiService, smartContractDeploymentRequestQueue),
        0,
        appConfig.tasks != null
                && appConfig.tasks.smartContractDeploymentSigningPublisher.periodInSeconds > 0
            ? appConfig.tasks.smartContractDeploymentSigningPublisher.periodInSeconds
            : 1,
        TimeUnit.SECONDS);

    apiThreadPool.scheduleWithFixedDelay(
        new SmartContractInvocationPublisherTask(
            smartContractInvocationApiService, smartContractInvocationRequestQueue),
        0,
        appConfig.tasks != null
                && appConfig.tasks.smartContractInvocationSigningPublisher.periodInSeconds > 0
            ? appConfig.tasks.smartContractInvocationSigningPublisher.periodInSeconds
            : 1,
        TimeUnit.SECONDS);

    apiThreadPool.scheduleWithFixedDelay(
        new TransferRequestPublisherTask(transferApiService, transferRequestQueue),
        0,
        appConfig.tasks != null && appConfig.tasks.transferSigningPublisher.periodInSeconds > 0
            ? appConfig.tasks.transferSigningPublisher.periodInSeconds
            : 1,
        TimeUnit.SECONDS);

    apiThreadPool.scheduleWithFixedDelay(
        new WalletRequestPublisherTask(walletApiService, walletRequestQueue),
        0,
        appConfig.tasks != null && appConfig.tasks.walletGenerationPublisher.periodInSeconds > 0
            ? appConfig.tasks.walletGenerationPublisher.periodInSeconds
            : 1,
        TimeUnit.SECONDS);

    apiThreadPool.scheduleWithFixedDelay(
        new MonitoringTask(vaultApiClient),
        0,
        appConfig.tasks != null && appConfig.tasks.monitoringPeriodInSeconds > 0
            ? appConfig.tasks.monitoringPeriodInSeconds
            : 1,
        TimeUnit.SECONDS);

    initShutdownHook();

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
