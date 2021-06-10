package io.swisschain.repositories.wallets;

import com.evanlennick.retry4j.exception.RetriesExhaustedException;
import com.evanlennick.retry4j.exception.UnexpectedException;
import io.swisschain.common.durability.RetryPolicies;
import io.swisschain.domain.wallet.Wallet;
import io.swisschain.repositories.PostgresErrorCodes;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.exceptions.WalletAlreadyExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class WalletRepositoryRetryDecorator implements WalletRepository {
  private final Logger logger = LogManager.getLogger();

  private final WalletRepository walletRepository;

  public WalletRepositoryRetryDecorator(WalletRepository walletRepository) {
    this.walletRepository = walletRepository;
  }

  @Override
  public Wallet find(String address, String group, String tenantId)
      throws OperationFailedException, OperationExhaustedException {
    try {
      var status =
          RetryPolicies.ExecuteWithDefaultRepositoryConfig(
              o ->
                  logger.warn(
                      String.format(
                          "Failed to find wallet by address %s by group %s by tenant %s: %s",
                          address,
                          group,
                          tenantId,
                          o.getLastExceptionThatCausedRetry().getMessage())),
              () -> walletRepository.find(address, group, tenantId));
      return status.getResult();
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to find wallet by address %s by group %s by tenant %s. Retries exhausted with total tries %d duration %d ms.",
              address,
              group,
              tenantId,
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error(
          String.format(
              "An unexpected error occurred while find wallet by address %s by group %s by tenant %s.",
              address, group, tenantId),
          exception);
      throw new OperationFailedException(exception);
    }
  }

  @Override
  public Wallet getByRequestId(Long walletGenerationRequestId)
      throws OperationFailedException, OperationExhaustedException {
    try {
      var status =
          RetryPolicies.ExecuteWithDefaultRepositoryConfig(
              o ->
                  logger.warn(
                      String.format(
                          "Failed to get wallet by wallet generation request %d: %s",
                          walletGenerationRequestId,
                          o.getLastExceptionThatCausedRetry().getMessage())),
              () -> walletRepository.getByRequestId(walletGenerationRequestId));
      return status.getResult();
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to get wallet by wallet generation request %d. Retries exhausted with total tries %d duration %d ms.",
              walletGenerationRequestId,
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      logger.error(
          String.format(
              "An unexpected error occurred while getting wallet by wallet generation request %d.",
              walletGenerationRequestId),
          exception);
      throw new OperationFailedException(exception);
    }
  }

  @Override
  public boolean insert(Wallet wallet)
      throws WalletAlreadyExistsException, OperationFailedException, OperationExhaustedException {
    try {
      var status =
          RetryPolicies.ExecuteWithDefaultRepositoryConfig(
              o ->
                  logger.warn(
                      String.format(
                          "Failed to save wallet with wallet generation request %d: %s",
                          wallet.getWalletGenerationRequestId(),
                          o.getLastExceptionThatCausedRetry().getMessage())),
              () -> walletRepository.insert(wallet));
      return status.getResult();
    } catch (RetriesExhaustedException exception) {
      logger.error(
          String.format(
              "Failed to save wallet with wallet generation request %d. Retries exhausted with total tries %d duration %d ms.",
              wallet.getWalletGenerationRequestId(),
              exception.getStatus().getTotalTries(),
              exception.getStatus().getTotalElapsedDuration().toMillis()));
      throw new OperationExhaustedException(exception);
    } catch (UnexpectedException exception) {
      if (exception.getCause() != null
          && SQLException.class.isAssignableFrom(exception.getCause().getClass())) {
        if (((SQLException) exception.getCause())
            .getSQLState()
            .equals(PostgresErrorCodes.UniqueViolationErrorCode)) {
          throw new WalletAlreadyExistsException(exception);
        }
      }
      logger.error(
          String.format(
              "An unexpected error occurred while saving wallet with wallet generation request %d.",
              wallet.getWalletGenerationRequestId()),
          exception);
      throw new OperationFailedException(exception);
    }
  }
}
