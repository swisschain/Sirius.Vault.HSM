package io.swisschain.services;

import com.evanlennick.retry4j.exception.RetriesExhaustedException;
import com.evanlennick.retry4j.exception.UnexpectedException;
import io.swisschain.common.durability.RetryPolicies;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.transactions.TransactionSigningRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class TransferApiServiceRetryDecorator implements TransferApiService {
  private final Logger logger = LogManager.getLogger();
  private final TransferApiService transferApiService;

  public TransferApiServiceRetryDecorator(TransferApiService transferApiService) {
    this.transferApiService = transferApiService;
  }

  @Override
  public List<TransactionSigningRequest> get()
      throws OperationFailedException, OperationExhaustedException {
    try {
      var status =
          RetryPolicies.ExecuteWithDefaultGrpcConfig(
              o ->
                  logger.warn(
                      "Failed to get transfer signing requests.",
                      o.getLastExceptionThatCausedRetry()),
              transferApiService::get);
      return status.getResult();
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to get transfer signing requests. Retries exhausted with total tries %d duration %d ms.",
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error(
          "An unexpected error occurred while getting transfer signing requests.", exception);
      throw new OperationFailedException(exception);
    }
  }

  @Override
  public void confirm(TransactionSigningRequest signingRequest)
      throws OperationFailedException, OperationExhaustedException {
    try {
      RetryPolicies.ExecuteWithDefaultGrpcConfig(
          o ->
              logger.warn(
                  String.format(
                      "Failed to confirm transfer signing request %d.", signingRequest.getId()),
                  o.getLastExceptionThatCausedRetry()),
          () -> {
            transferApiService.confirm(signingRequest);
            return null;
          });
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to confirm transfer signing request %d. Retries exhausted with total tries %d duration %d ms.",
              signingRequest.getId(),
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error(
          String.format(
              "An unexpected error occurred while confirming transfer signing request %d.",
              signingRequest.getId()),
          exception);
      throw new OperationFailedException(exception);
    }
  }

  @Override
  public void reject(TransactionSigningRequest signingRequest)
      throws OperationFailedException, OperationExhaustedException {
    try {
      RetryPolicies.ExecuteWithDefaultGrpcConfig(
          o ->
              logger.warn(
                  String.format(
                      "Failed to reject transfer signing request %d.", signingRequest.getId()),
                  o.getLastExceptionThatCausedRetry()),
          () -> {
            transferApiService.reject(signingRequest);
            return null;
          });
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to reject transfer signing request %d. Retries exhausted with total tries %d duration %d ms.",
              signingRequest.getId(),
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error(
          String.format(
              "An unexpected error occurred while rejecting transfer signing request %d.",
              signingRequest.getId()),
          exception);
      throw new OperationFailedException(exception);
    }
  }
}
