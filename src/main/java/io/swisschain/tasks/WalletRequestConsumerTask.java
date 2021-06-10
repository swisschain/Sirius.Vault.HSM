package io.swisschain.tasks;

import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.wallet.WalletGenerationRequest;
import io.swisschain.services.WalletService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class WalletRequestConsumerTask implements Runnable {

  private final Logger logger = LogManager.getLogger();
  private final BlockingQueue<WalletGenerationRequest> queue;
  private final WalletService walletService;

  public WalletRequestConsumerTask(
      BlockingQueue<WalletGenerationRequest> queue, WalletService walletService) {
    this.queue = queue;
    this.walletService = walletService;
  }

  @Override
  public void run() {
    while (true) {
      try {
        var walletGenerationRequest = queue.take();
        walletService.generate(walletGenerationRequest);
      } catch (OperationExhaustedException exception) {
        // ignore
      } catch (OperationFailedException exception) {
        logger.error("Operation failed while processing wallet generation requests.", exception);
      } catch (InterruptedException exception) {
        logger.warn("Operation interrupted.", exception);
        return;
      } catch (Exception exception) {
        logger.error(
            "An unexpected error occurred while processing wallet generation requests.", exception);
      }
    }
  }
}
