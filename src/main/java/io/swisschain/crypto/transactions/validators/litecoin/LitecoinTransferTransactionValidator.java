package io.swisschain.crypto.transactions.validators.litecoin;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.CoinsTransactionValidator;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.crypto.transactions.validators.TransferTransactionValidator;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;

public class LitecoinTransferTransactionValidator extends TransferTransactionValidator
    implements CoinsTransactionValidator {

  public LitecoinTransferTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.stellar, jsonSerializer);
  }

  public TransactionValidationResult validate(
      byte[] unsignedTransaction, NetworkType networkType, String publicKey, String document)
      throws InvalidDocumentException {

    var transfer = getTransfer(document);

    return validate(transfer, networkType);
  }
}
