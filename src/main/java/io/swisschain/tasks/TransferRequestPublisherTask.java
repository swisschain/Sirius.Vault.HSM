package io.swisschain.tasks;

import io.swisschain.domain.transfers.TransferSigningRequest;
import io.swisschain.services.TransferApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class TransferRequestPublisherTask implements Runnable {
  private static final Logger logger = LogManager.getLogger();

  private final TransferApiService transferApiService;
  private final BlockingQueue<TransferSigningRequest> queue;

  public TransferRequestPublisherTask(
      TransferApiService transferApiService, BlockingQueue<TransferSigningRequest> queue) {
    this.transferApiService = transferApiService;
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      for (var transferSigningRequest : transferApiService.get()) {
        queue.put(transferSigningRequest);
      }
    } catch (InterruptedException exception) {
      logger.warn("Operation interrupted.", exception);
    } catch (Exception exception) {
      logger.error(
          "An unexpected error occurred while publishing transfer signing requests.", exception);
    }
  }
}
