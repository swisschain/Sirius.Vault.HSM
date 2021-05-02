package io.swisschain.crypto.transactions.validators;

import io.swisschain.contracts.transfers.Transfer;
import io.swisschain.contracts.transfers.TransferDocument;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.domain.transactions.TransactionRejectionReason;
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
          TransactionRejectionReason.Other,
          String.format(
              "Invalid blockchain: %s, expected %s",
              blockchainProtocol.getName(), transfer.getBlockchain().getProtocolId()));
    }

    if (!transfer.getBlockchain().getNetworkType().name().equals(networkType.name())) {
      return TransactionValidationResult.CreateInvalid(
          TransactionRejectionReason.Other,
          String.format(
              "Invalid networkType: %s, expected %s",
              networkType, transfer.getBlockchain().getNetworkType()));
    }

    if (!transfer.getValue().getAsset().getSymbol().equals(blockchainProtocol.getCoin())) {
      return TransactionValidationResult.CreateInvalid(
          TransactionRejectionReason.Other,
          String.format(
              "Invalid asset: %s, expected %s",
              blockchainProtocol.getCoin(), transfer.getValue().getAsset().getSymbol()));
    }

    return TransactionValidationResult.CreateValid();
  }
}
