package io.swisschain.crypto.address.generation;

import com.evanlennick.retry4j.exception.RetriesExhaustedException;
import com.evanlennick.retry4j.exception.UnexpectedException;
import io.swisschain.common.durability.RetryPolicies;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.primitives.NetworkType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressGeneratorRetryDecorator implements AddressGenerator {
  private final Logger logger = LogManager.getLogger();
  private final AddressGenerator addressGenerator;

  public AddressGeneratorRetryDecorator(AddressGenerator addressGenerator) {
    this.addressGenerator = addressGenerator;
  }

  @Override
  public AddressGenerationResult generate(NetworkType networkType)
      throws OperationFailedException, OperationExhaustedException {
    try {
      var status =
          RetryPolicies.ExecuteWithDefaultHsmConfig(
              o ->
                  logger.warn(
                      String.format("Failed to generate address %s.", networkType.name()),
                      o.getLastExceptionThatCausedRetry()),
              () -> addressGenerator.generate(networkType));
      return status.getResult();
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to generate address %s. Retries exhausted with total tries %d duration %d ms.",
              networkType.name(),
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error("An unexpected error occurred while generating address.", exception);
      throw new OperationFailedException(exception);
    }
  }
}
