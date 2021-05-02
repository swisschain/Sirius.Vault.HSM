package io.swisschain.tasks;

import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.services.SmartContractDeploymentApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class SmartContractDeploymentPublisherTask implements Runnable {
  private static final Logger logger = LogManager.getLogger();

  private final SmartContractDeploymentApiService smartContractDeploymentApiService;
  private final BlockingQueue<TransactionSigningRequest> queue;

  public SmartContractDeploymentPublisherTask(
      SmartContractDeploymentApiService smartContractDeploymentApiService,
      BlockingQueue<TransactionSigningRequest> queue) {
    this.smartContractDeploymentApiService = smartContractDeploymentApiService;
    this.queue = queue;
  }

  @Override
  public void run() {
    try {
      for (var signingRequest : smartContractDeploymentApiService.get()) {
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
