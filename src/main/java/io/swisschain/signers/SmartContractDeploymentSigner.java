package io.swisschain.signers;

import io.swisschain.crypto.transactions.TransactionSignerFactory;
import io.swisschain.crypto.transactions.TransactionValidatorFactory;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.repositories.wallets.WalletRepository;
import io.swisschain.services.SmartContractDeploymentApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class SmartContractDeploymentSigner extends TransactionSigner {
  private static final Logger logger = LogManager.getLogger();

  private final SmartContractDeploymentApiService smartContractDeploymentApiService;

  public SmartContractDeploymentSigner(
      SmartContractDeploymentApiService smartContractDeploymentApiService,
      DocumentValidator documentValidator,
      TransactionSignerFactory transactionSignerFactory,
      TransactionValidatorFactory transactionValidatorFactory,
      WalletRepository walletRepository) {
    super(
        documentValidator,
        transactionSignerFactory,
        transactionValidatorFactory,
        walletRepository,
        logger);
    this.smartContractDeploymentApiService = smartContractDeploymentApiService;
  }

  @Override
  public void sign(TransactionSigningRequest transactionSigningRequest)
      throws OperationFailedException, OperationExhaustedException, SQLException {
    super.sign(transactionSigningRequest);

    if (transactionSigningRequest.isRejected()) {
      smartContractDeploymentApiService.reject(transactionSigningRequest);
    } else {
      smartContractDeploymentApiService.confirm(transactionSigningRequest);
    }
  }
}
