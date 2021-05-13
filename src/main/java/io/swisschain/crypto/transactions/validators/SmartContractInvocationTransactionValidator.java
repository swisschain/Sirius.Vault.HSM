package io.swisschain.crypto.transactions.validators;

import io.swisschain.contracts.documents.smart_contracts.invocation.SmartContractInvocationDocument;
import io.swisschain.contracts.smart_contracts.invocation.SmartContractInvocation;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;

public class SmartContractInvocationTransactionValidator {
  protected final BlockchainProtocolCodes blockchainProtocol;
  private final JsonSerializer jsonSerializer;

  protected SmartContractInvocationTransactionValidator(
      BlockchainProtocolCodes blockchainProtocol, JsonSerializer jsonSerializer) {
    this.blockchainProtocol = blockchainProtocol;
    this.jsonSerializer = jsonSerializer;
  }

  protected SmartContractInvocation getSmartContractInvocation(String document)
      throws InvalidDocumentException {
    SmartContractInvocationDocument smartContractInvocationDocument;

    try {
      smartContractInvocationDocument =
          jsonSerializer.deserialize(document, SmartContractInvocationDocument.class);
    } catch (Exception exception) {
      throw new InvalidDocumentException(exception);
    }

    return smartContractInvocationDocument.getInvocation();
  }

  public TransactionValidationResult validate(
      SmartContractInvocation smartContractInvocation, NetworkType networkType) {

    if (!smartContractInvocation
        .getBlockchain()
        .getProtocolId()
        .equals(blockchainProtocol.getName())) {
      return TransactionValidationResult.CreateInvalid(
          String.format(
              "Invalid blockchain: %s, expected %s",
              blockchainProtocol.getName(),
              smartContractInvocation.getBlockchain().getProtocolId()));
    }

    if (!smartContractInvocation
        .getBlockchain()
        .getNetworkType()
        .name()
        .equals(networkType.name())) {
      return TransactionValidationResult.CreateInvalid(
          String.format(
              "Invalid networkType: %s, expected %s",
              networkType, smartContractInvocation.getBlockchain().getNetworkType()));
    }

    return TransactionValidationResult.CreateValid();
  }
}
