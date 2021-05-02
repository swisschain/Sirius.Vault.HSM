package io.swisschain.crypto.transactions;

import com.evanlennick.retry4j.exception.RetriesExhaustedException;
import com.evanlennick.retry4j.exception.UnexpectedException;
import io.swisschain.common.durability.RetryPolicies;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.common.Coin;
import io.swisschain.domain.primitives.NetworkType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CoinsTransactionSignerRetryDecorator implements CoinsTransactionSigner {
  private final Logger logger = LogManager.getLogger();
  private final CoinsTransactionSigner coinsTransactionSigner;

  public CoinsTransactionSignerRetryDecorator(CoinsTransactionSigner coinsTransactionSigner) {
    this.coinsTransactionSigner = coinsTransactionSigner;
  }

  @Override
  public TransactionSigningResult sign(
      byte[] unsignedTransaction,
      List<Coin> coins,
      String privateKey,
      String publicKey,
      NetworkType networkType)
      throws OperationFailedException, OperationExhaustedException {
    try {
      var status =
          RetryPolicies.ExecuteWithDefaultHsmConfig(
              o -> logger.warn("Failed to sign transaction.", o.getLastExceptionThatCausedRetry()),
              () ->
                  coinsTransactionSigner.sign(
                      unsignedTransaction, coins, privateKey, publicKey, networkType));
      return status.getResult();
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to sign transaction. Retries exhausted with total tries %d duration %d ms.",
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error("An unexpected error occurred while signing transaction.", exception);
      throw new OperationFailedException(exception);
    }
  }
}
