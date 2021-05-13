package io.swisschain.tasks;

import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.services.TransactionSigningApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class SmartContractDeploymentPublisherTask implements Runnable {
  private static final Logger logger = LogManager.getLogger();

  private final TransactionSigningApiService transactionSigningApiService;
  private final BlockingQueue<TransactionSigningRequest> queue;

  public SmartContractDeploymentPublisherTask(
      TransactionSigningApiService transactionSigningApiService,
      BlockingQueue<TransactionSigningRequest> queue) {
    this.transactionSigningApiService = transactionSigningApiService;
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      for (var signingRequest : transactionSigningApiService.get()) {
        queue.put(signingRequest);
      }
    } catch (InterruptedException exception) {
      logger.warn("Operation interrupted.", exception);
    } catch (Exception exception) {
      logger.error(
          "An unexpected error occurred while publishing smart contract deployment signing requests.",
          exception);
    }
  }
}
