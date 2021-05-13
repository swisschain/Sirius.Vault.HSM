package io.swisschain.crypto.transactions.validators.tezos;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.CoinsTransactionValidator;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.crypto.transactions.exceptions.InvalidInputsException;
import io.swisschain.crypto.transactions.validators.SmartContractDeploymentTransactionValidator;
import io.swisschain.crypto.transactions.validators.SmartContractInvocationTransactionValidator;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class TezosSmartContractInvocationTransactionValidator
    extends SmartContractInvocationTransactionValidator implements CoinsTransactionValidator {

  public TezosSmartContractInvocationTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.tezos, jsonSerializer);
  }

  public TransactionValidationResult validate(
      byte[] unsignedTransaction, NetworkType networkType, String publicKey, String document)
      throws InvalidDocumentException {
    var smartContractInvocation = getSmartContractInvocation(document);

    var validationResult = validate(smartContractInvocation, networkType);

    if (!validationResult.isValid()) return validationResult;

    return TransactionValidationResult.CreateValid();
  }
}
