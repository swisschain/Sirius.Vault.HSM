package io.swisschain.tasks;

import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.services.TransferApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class TransferRequestPublisherTask implements Runnable {
  private static final Logger logger = LogManager.getLogger();

  private final TransferApiService transferApiService;
  private final BlockingQueue<TransactionSigningRequest> queue;

  public TransferRequestPublisherTask(
      TransferApiService transferApiService, BlockingQueue<TransactionSigningRequest> queue) {
    this.transferApiService = transferApiService;
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      for (var signingRequest : transferApiService.get()) {
        queue.put(signingRequest);
      }
    } catch (InterruptedException exception) {
      logger.warn("Operation interrupted.", exception);
    } catch (Exception exception) {
      logger.error(
          "An unexpected error occurred while publishing transfer signing requests.", exception);
    }
  }
}
