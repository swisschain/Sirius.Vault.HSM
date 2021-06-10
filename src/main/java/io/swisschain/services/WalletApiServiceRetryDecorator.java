package io.swisschain.services;

import com.evanlennick.retry4j.exception.RetriesExhaustedException;
import com.evanlennick.retry4j.exception.UnexpectedException;
import io.swisschain.common.durability.RetryPolicies;
import io.swisschain.domain.wallet.WalletGenerationRequest;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class WalletApiServiceRetryDecorator implements WalletApiService {
  private final Logger logger = LogManager.getLogger();
  private final WalletApiService walletApiService;

  public WalletApiServiceRetryDecorator(WalletApiService walletApiService) {
    this.walletApiService = walletApiService;
  }

  @Override
  public List<WalletGenerationRequest> get()
      throws OperationFailedException, OperationExhaustedException {
    try {
      var status =
          RetryPolicies.ExecuteWithDefaultGrpcConfig(
              o ->
                  logger.warn(
                      String.format(
                          "Failed to get wallet generation requests: %s",
                          o.getLastExceptionThatCausedRetry().getMessage())),
              walletApiService::get);
      return status.getResult();
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to get wallet generation requests. Retries exhausted with total tries %d duration %d ms.",
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error(
          "An unexpected error occurred while getting wallet generation requests.", exception);
      throw new OperationFailedException(exception);
    }
  }

  @Override
  public void confirm(WalletGenerationRequest walletGenerationRequest)
      throws OperationFailedException, OperationExhaustedException {
    try {
      RetryPolicies.ExecuteWithDefaultGrpcConfig(
          o ->
              logger.warn(
                  String.format(
                      "Failed to confirm wallet generation request %d: %s",
                      walletGenerationRequest.getId(),
                      o.getLastExceptionThatCausedRetry().getMessage())),
          () -> {
            walletApiService.confirm(walletGenerationRequest);
            return null;
          });
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to confirm wallet generation request %d. Retries exhausted with total tries %d duration %d ms.",
              walletGenerationRequest.getId(),
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error(
          String.format(
              "An unexpected error occurred while confirming wallet generation request %d.",
              walletGenerationRequest.getId()),
          exception);
      throw new OperationFailedException(exception);
    }
  }

  @Override
  public void reject(WalletGenerationRequest walletGenerationRequest)
      throws OperationFailedException, OperationExhaustedException {
    try {
      RetryPolicies.ExecuteWithDefaultGrpcConfig(
          o ->
              logger.warn(
                  String.format(
                      "Failed to reject wallet generation request %d: %s",
                      walletGenerationRequest.getId(),
                      o.getLastExceptionThatCausedRetry().getMessage())),
          () -> {
            walletApiService.reject(walletGenerationRequest);
            return null;
          });
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to reject wallet generation request %d. Retries exhausted with total tries %d duration %d ms.",
              walletGenerationRequest.getId(),
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error(
          String.format(
              "An unexpected error occurred while rejecting wallet generation request %d.",
              walletGenerationRequest.getId()),
          exception);
      throw new OperationFailedException(exception);
    }
  }
}
