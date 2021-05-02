package io.swisschain.crypto.transactions.validators;

import io.swisschain.contracts.smart_contracts.deployment.SmartContractDeployment;
import io.swisschain.contracts.smart_contracts.deployment.SmartContractDeploymentDocument;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.domain.transactions.TransactionRejectionReason;
import io.swisschain.services.JsonSerializer;

public class SmartContractDeploymentTransactionValidator {
  protected final BlockchainProtocolCodes blockchainProtocol;
  private final JsonSerializer jsonSerializer;

  protected SmartContractDeploymentTransactionValidator(
      BlockchainProtocolCodes blockchainProtocol, JsonSerializer jsonSerializer) {
    this.blockchainProtocol = blockchainProtocol;
    this.jsonSerializer = jsonSerializer;
  }

  protected SmartContractDeployment getSmartContractDeployment(String document)
      throws InvalidDocumentException {
    SmartContractDeploymentDocument smartContractDeploymentDocument;

    try {
      smartContractDeploymentDocument =
          jsonSerializer.deserialize(document, SmartContractDeploymentDocument.class);
    } catch (Exception exception) {
      throw new InvalidDocumentException(exception);
    }

    return smartContractDeploymentDocument.getDeployment();
  }

  public TransactionValidationResult validate(
      SmartContractDeployment smartContractDeployment, NetworkType networkType) {

    if (!smartContractDeployment
        .getBlockchain()
        .getProtocolId()
        .equals(blockchainProtocol.getName())) {
      return TransactionValidationResult.CreateInvalid(
          TransactionRejectionReason.Other,
          String.format(
              "Invalid blockchain: %s, expected %s",
              blockchainProtocol.getName(),
              smartContractDeployment.getBlockchain().getProtocolId()));
    }

    if (!smartContractDeployment
        .getBlockchain()
        .getNetworkType()
        .name()
        .equals(networkType.name())) {
      return TransactionValidationResult.CreateInvalid(
          TransactionRejectionReason.Other,
          String.format(
              "Invalid networkType: %s, expected %s",
              networkType, smartContractDeployment.getBlockchain().getNetworkType()));
    }

    return TransactionValidationResult.CreateValid();
  }
}
