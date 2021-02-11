package io.swisschain;

import io.swisschain.config.loaders.ConfigLoader;
import io.swisschain.crypto.asymmetric.AsymmetricEncryptionService;
import io.swisschain.domain.document.GuardianKey;
import io.swisschain.isAlive.IsAliveService;
import io.swisschain.repositories.DbConnectionFactory;
import io.swisschain.repositories.DbMigration;
import io.swisschain.repositories.wallets.WalletRepository;
import io.swisschain.services.DocumentValidator;
import io.swisschain.services.TransactionService;
import io.swisschain.services.WalletService;
import io.swisschain.sirius.vaultApi.ChannelFactory;
import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.tasks.TransferSigningTask;
import io.swisschain.tasks.WalletGenerationTask;
import io.swisschain.utils.AppVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
    var walletRepository = new WalletRepository(connectionFactory);

    // Services

    var asymmetricEncryptionService = new AsymmetricEncryptionService();

    var walletService = new WalletService(walletRepository, config);
    var transactionService = new TransactionService(walletRepository, config);

    var guardianKey = new GuardianKey(config.keys.guardian.publicKey);

    var documentValidator = new DocumentValidator(asymmetricEncryptionService, guardianKey);

    // Tasks

    var hostProcessId = getHostProcessId();

    var service = Executors.newScheduledThreadPool(2);

    service.scheduleWithFixedDelay(
        new WalletGenerationTask(vaultApiClient, walletService, hostProcessId),
        0,
        config.tasks != null && config.tasks.walletGenerationPeriodInSeconds > 0
            ? config.tasks.walletGenerationPeriodInSeconds
            : 1,
        TimeUnit.SECONDS);
    service.scheduleWithFixedDelay(
        new TransferSigningTask(vaultApiClient, transactionService, documentValidator, hostProcessId),
        0,
        config.tasks != null && config.tasks.transferSigningPeriodInSeconds > 0
            ? config.tasks.transferSigningPeriodInSeconds
            : 1,
        TimeUnit.SECONDS);

    initShutdownHook();

    new IsAliveService(5001).start();

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
