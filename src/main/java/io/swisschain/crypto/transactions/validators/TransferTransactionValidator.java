package io.swisschain.crypto.transactions.validators;

import io.swisschain.contracts.documents.transfers.TransferDocument;
import io.swisschain.contracts.transfers.Transfer;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;

public abstract class TransferTransactionValidator {
  protected final BlockchainProtocolCodes blockchainProtocol;
  private final JsonSerializer jsonSerializer;

  protected TransferTransactionValidator(
      BlockchainProtocolCodes blockchainProtocol, JsonSerializer jsonSerializer) {
    this.blockchainProtocol = blockchainProtocol;
    this.jsonSerializer = jsonSerializer;
  }

  protected Transfer getTransfer(String document) throws InvalidDocumentException {
    TransferDocument transferDocument;

    try {
      transferDocument = jsonSerializer.deserialize(document, TransferDocument.class);
    } catch (Exception exception) {
      throw new InvalidDocumentException(exception);
    }

    return transferDocument.getTransfer();
  }

  public TransactionValidationResult validate(Transfer transfer, NetworkType networkType) {

    if (!transfer.getBlockchain().getProtocolId().equals(blockchainProtocol.getName())) {
      return TransactionValidationResult.CreateInvalid(
          String.format(
              "Invalid blockchain: %s, expected %s",
              transfer.getBlockchain().getProtocolId(), blockchainProtocol.getName()));
    }

    if (!transfer.getBlockchain().getNetworkType().name().equals(networkType.name())) {
      return TransactionValidationResult.CreateInvalid(
          String.format(
              "Invalid networkType: %s, expected %s",
              transfer.getBlockchain().getNetworkType(), networkType));
    }

    if (!transfer.getFee().getAsset().getSymbol().equals(blockchainProtocol.getCoin())) {
      return TransactionValidationResult.CreateInvalid(
          String.format(
              "Invalid fee asset: %s, expected %s",
              transfer.getValue().getAsset().getSymbol(), blockchainProtocol.getCoin()));
    }

    return TransactionValidationResult.CreateValid();
  }
}
