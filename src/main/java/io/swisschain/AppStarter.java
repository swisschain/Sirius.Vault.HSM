package io.swisschain;

import io.swisschain.config.Config;
import io.swisschain.config.ConfigLoader;
import io.swisschain.isAlive.IsAliveService;
import io.swisschain.repositories.DbConnectionFactory;
import io.swisschain.repositories.DbMigration;
import io.swisschain.repositories.wallets.WalletRepository;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppStarter {
  private static final Logger logger = LogManager.getLogger();

  public static void main(String[] args) throws InterruptedException {
    Config config = ConfigLoader.loadConfig();

    if (config == null) {
      logger.error("Can not load config");
      return;
    }

    if (!DbMigration.migrateDatabase(
        config.db.url, config.db.user, config.db.password, config.db.schema)) {
      return;
    }

    // API clients

    VaultApiClient vaultApiClient =
        new VaultApiClient(
            ChannelFactory.create(
                config.vaultApi.host,
                config.vaultApi.port,
                config.vaultApi.ssl,
                config.vaultApi.apiKey));

    // Repositories

    DbConnectionFactory connectionFactory =
        new DbConnectionFactory(
            config.db.url, config.db.user, config.db.password, config.db.schema);
    WalletRepository walletRepository = new WalletRepository(connectionFactory);

    // Services

    var walletService = new WalletService(walletRepository, config);
    var transactionService = new TransactionService(walletRepository, config);

    // Tasks

    var hostProcessId = getHostProcessId();

    ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

    service.scheduleWithFixedDelay(
        new WalletGenerationTask(vaultApiClient, walletService, hostProcessId),
        0,
        1,
        TimeUnit.SECONDS);
    service.scheduleWithFixedDelay(
        new TransferSigningTask(vaultApiClient, transactionService, hostProcessId),
        0,
        1,
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
