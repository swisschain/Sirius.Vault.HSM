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

public class SmartContractDeploymentApiServiceRetryDecorator
    implements SmartContractDeploymentApiService {
  private final Logger logger = LogManager.getLogger();
  private final SmartContractDeploymentApiService service;

  public SmartContractDeploymentApiServiceRetryDecorator(
      SmartContractDeploymentApiService service) {
    this.service = service;
  }

  @Override
  public List<TransactionSigningRequest> get()
      throws OperationFailedException, OperationExhaustedException {
    try {
      var status =
          RetryPolicies.ExecuteWithDefaultGrpcConfig(
              o ->
                  logger.warn(
                      "Failed to get smart contract deployment signing requests.",
                      o.getLastExceptionThatCausedRetry()),
              service::get);
      return status.getResult();
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to get smart contract deployment signing requests. Retries exhausted with total tries %d duration %d ms.",
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error(
          "An unexpected error occurred while getting smart contract deployment signing requests.",
          exception);
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
                      "Failed to confirm smart contract deployment signing request %d.",
                      signingRequest.getId()),
                  o.getLastExceptionThatCausedRetry()),
          () -> {
            service.confirm(signingRequest);
            return null;
          });
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to confirm smart contract deployment signing request %d. Retries exhausted with total tries %d duration %d ms.",
              signingRequest.getId(),
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error(
          String.format(
              "An unexpected error occurred while confirming smart contract deployment signing request %d.",
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
                      "Failed to reject smart contract deployment signing request %d.",
                      signingRequest.getId()),
                  o.getLastExceptionThatCausedRetry()),
          () -> {
            service.reject(signingRequest);
            return null;
          });
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to reject smart contract deployment signing request %d. Retries exhausted with total tries %d duration %d ms.",
              signingRequest.getId(),
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error(
          String.format(
              "An unexpected error occurred while rejecting smart contract deployment signing request %d.",
              signingRequest.getId()),
          exception);
      throw new OperationFailedException(exception);
    }
  }
}
