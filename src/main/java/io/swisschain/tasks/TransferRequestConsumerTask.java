package io.swisschain.tasks;

import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.transfers.TransferSigningRequest;
import io.swisschain.services.TransferService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class TransferRequestConsumerTask implements Runnable {
  private final Logger logger = LogManager.getLogger();
  private final BlockingQueue<TransferSigningRequest> queue;
  private final TransferService transferService;

  public TransferRequestConsumerTask(
      BlockingQueue<TransferSigningRequest> queue, TransferService transferService) {
    this.queue = queue;
    this.transferService = transferService;
  }

  @Override
  public void run() {
    while (true) {
      try {
        var transferSigningRequest = queue.take();
        transferService.sign(transferSigningRequest);
      } catch (OperationExhaustedException exception) {
        logger.error("Operation exhausted while processing transfer signing requests.", exception);
      } catch (OperationFailedException exception) {
        logger.error("Operation failed while processing transfer signing requests.", exception);
      } catch (InterruptedException exception) {
        logger.warn("Operation interrupted.", exception);
        return;
      } catch (Exception exception) {
        logger.error(
            "An unexpected error occurred while processing transfer signing requests.", exception);
      }
    }
  }
}
