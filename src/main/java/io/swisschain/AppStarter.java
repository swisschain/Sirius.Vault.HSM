package io.swisschain;

import io.swisschain.config.loaders.ConfigLoader;
import io.swisschain.crypto.address.generation.AddressGeneratorFactory;
import io.swisschain.crypto.asymmetric.AsymmetricEncryptionService;
import io.swisschain.crypto.transaction.signing.TransactionSignerFactory;
import io.swisschain.domain.document.GuardianKey;
import io.swisschain.isAlive.IsAliveService;
import io.swisschain.repositories.DbConnectionFactory;
import io.swisschain.repositories.DbMigration;
import io.swisschain.repositories.wallets.WalletRepositoryImp;
import io.swisschain.repositories.wallets.WalletRepositoryRetryDecorator;
import io.swisschain.services.*;
import io.swisschain.sirius.vaultApi.ChannelFactory;
import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.tasks.MonitoringTask;
import io.swisschain.tasks.TransferSigningTask;
import io.swisschain.tasks.WalletGenerationTask;
import io.swisschain.common.AppVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
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

    var addressGeneratorFactory = new AddressGeneratorFactory(config);
    var transactionSignerFactory = new TransactionSignerFactory(config);

    var walletApiService =
        new WalletApiServiceRetryDecorator(new WalletApiServiceImp(vaultApiClient, hostProcessId));

    var transferApiService =
        new TransferApiServiceRetryDecorator(
            new TransferApiServiceImp(vaultApiClient, hostProcessId));

    var guardianKey = new GuardianKey(config.keys.guardian.publicKey);

    var documentValidator = new DocumentValidator(asymmetricEncryptionService, guardianKey);

    // Tasks

    var service = Executors.newScheduledThreadPool(2);

    service.scheduleWithFixedDelay(
        new WalletGenerationTask(walletRepository, walletApiService, addressGeneratorFactory),
        0,
        config.tasks != null && config.tasks.walletGenerationPeriodInSeconds > 0
            ? config.tasks.walletGenerationPeriodInSeconds
            : 1,
        TimeUnit.SECONDS);

    service.scheduleWithFixedDelay(
        new TransferSigningTask(
            transferApiService, documentValidator, transactionSignerFactory, walletRepository),
        0,
        config.tasks != null && config.tasks.transferSigningPeriodInSeconds > 0
            ? config.tasks.transferSigningPeriodInSeconds
            : 1,
        TimeUnit.SECONDS);

    service.scheduleWithFixedDelay(
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
