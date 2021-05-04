package io.swisschain.crypto.transactions.validators.ethereum;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.CoinsTransactionValidator;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.crypto.transactions.validators.TransferTransactionValidator;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;
import org.web3j.crypto.TransactionDecoder;
import org.web3j.utils.Convert;

import static org.apache.commons.codec.binary.Hex.encodeHexString;

public abstract class BaseEthereumTransferTransactionValidator extends TransferTransactionValidator
    implements CoinsTransactionValidator {

  public BaseEthereumTransferTransactionValidator(
      BlockchainProtocolCodes blockchainProtocol, JsonSerializer jsonSerializer) {
    super(blockchainProtocol, jsonSerializer);
  }

  public TransactionValidationResult validate(
      byte[] unsignedTransaction, NetworkType networkType, String publicKey, String document)
      throws InvalidDocumentException {
    var transfer = getTransfer(document);

    var validationResult = validate(transfer, networkType);

    if (!validationResult.isValid()) return validationResult;

    final var transaction = TransactionDecoder.decode(encodeHexString(unsignedTransaction));

    if (!transfer.getDestination().getAddress().equalsIgnoreCase(transaction.getTo())) {
      return TransactionValidationResult.CreateInvalid(
          String.format(
              "Invalid destination address: %s, expected %s",
              transaction.getTo(), transfer.getDestination().getAddress()));
    }

    final var amount = Convert.fromWei(transaction.getValue().toString(), Convert.Unit.ETHER);
    if (transfer.getValue().getAmount().compareTo(amount) < 0) {
      return TransactionValidationResult.CreateInvalid(
          String.format(
              "Invalid transaction amount: %s, expected %s",
              amount.toString(), transfer.getValue().getAmount().toString()));
    }

    final var fee = Convert.fromWei(transaction.getGasLimit().toString(), Convert.Unit.GWEI);
    if (transfer.getFee().getAmount().compareTo(fee) < 0) {
      return TransactionValidationResult.CreateInvalid(
          String.format(
              "Invalid transaction fee: %s, expected %s",
              fee.toString(), transfer.getFee().getAmount().toString()));
    }
    return TransactionValidationResult.CreateValid();
  }
}
