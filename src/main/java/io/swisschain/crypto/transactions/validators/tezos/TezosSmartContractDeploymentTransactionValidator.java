package io.swisschain.crypto.transactions.validators.tezos;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.CoinsTransactionValidator;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.crypto.transactions.validators.SmartContractDeploymentTransactionValidator;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;

public class TezosSmartContractDeploymentTransactionValidator
    extends SmartContractDeploymentTransactionValidator implements CoinsTransactionValidator {

  public TezosSmartContractDeploymentTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.tezos, jsonSerializer);
  }

  public TransactionValidationResult validate(
      byte[] unsignedTransaction, NetworkType networkType, String publicKey, String document)
      throws InvalidDocumentException {
    var smartContractDeployment = getSmartContractDeployment(document);

    var validationResult = validate(smartContractDeployment, networkType);

    if (!validationResult.isValid()) return validationResult;

    return TransactionValidationResult.CreateValid();
  }
}
