package io.swisschain.tasks;

import io.swisschain.domain.wallet.WalletGenerationRequest;
import io.swisschain.services.WalletApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class WalletRequestPublisherTask implements Runnable {

  private final Logger logger = LogManager.getLogger();
  private final WalletApiService walletApiService;
  private final BlockingQueue<WalletGenerationRequest> queue;

  public WalletRequestPublisherTask(
      WalletApiService walletApiService, BlockingQueue<WalletGenerationRequest> queue) {
    this.walletApiService = walletApiService;
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      for (var walletGenerationRequest : walletApiService.get()) {
        queue.put(walletGenerationRequest);
      }
    } catch (InterruptedException exception) {
      logger.warn("Operation interrupted.", exception);
    } catch (Exception exception) {
      logger.error(
          "An unexpected error occurred while publishing wallet generation requests.", exception);
    }
  }
}
