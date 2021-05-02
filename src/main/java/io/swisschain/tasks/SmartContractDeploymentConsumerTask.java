package io.swisschain.tasks;

import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.signers.SmartContractDeploymentSigner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class SmartContractDeploymentConsumerTask implements Runnable {
  private final Logger logger = LogManager.getLogger();
  private final BlockingQueue<TransactionSigningRequest> queue;
  private final SmartContractDeploymentSigner smartContractDeploymentSigner;

  public SmartContractDeploymentConsumerTask(
      BlockingQueue<TransactionSigningRequest> queue,
      SmartContractDeploymentSigner smartContractDeploymentSigner) {
    this.queue = queue;
    this.smartContractDeploymentSigner = smartContractDeploymentSigner;
  }

  @Override
  public void run() {
    while (true) {
      try {
        var transactionSigningRequest = queue.take();
        smartContractDeploymentSigner.sign(transactionSigningRequest);
      } catch (OperationExhaustedException exception) {
        logger.error(
            "Operation exhausted while processing smart contract deployment signing requests.",
            exception);
      } catch (OperationFailedException exception) {
        logger.error(
            "Operation failed while processing smart contract deployment signing requests.",
            exception);
      } catch (InterruptedException exception) {
        logger.warn("Operation interrupted.", exception);
        return;
      } catch (Exception exception) {
        logger.error(
            "An unexpected error occurred while processing smart contract deployment signing requests.",
            exception);
      }
    }
  }
}
