package io.swisschain.tasks;

import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.signers.TransactionSigner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class SmartContractInvocationConsumerTask implements Runnable {
  private final Logger logger = LogManager.getLogger();
  private final BlockingQueue<TransactionSigningRequest> queue;
  private final TransactionSigner transactionSigner;

  public SmartContractInvocationConsumerTask(
      BlockingQueue<TransactionSigningRequest> queue, TransactionSigner transactionSigner) {
    this.queue = queue;
    this.transactionSigner = transactionSigner;
  }

  @Override
  public void run() {
    while (true) {
      try {
        var transactionSigningRequest = queue.take();
        transactionSigner.sign(transactionSigningRequest);
      } catch (OperationExhaustedException exception) {
        // ignore
      } catch (OperationFailedException exception) {
        logger.error(
            "Operation failed while processing smart contract invocation signing requests.",
            exception);
      } catch (InterruptedException exception) {
        logger.warn("Operation interrupted.", exception);
        return;
      } catch (Exception exception) {
        logger.error(
            "An unexpected error occurred while processing smart contract invocation signing requests.",
            exception);
      }
    }
  }
}
