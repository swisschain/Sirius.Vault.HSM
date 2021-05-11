package io.swisschain.crypto.transactions.validators.stellar;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.NetworkMapper;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.transactions.CoinsTransactionValidator;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.crypto.transactions.validators.TransferTransactionValidator;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;
import org.stellar.sdk.Operation;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.xdr.TransactionEnvelope;
import org.stellar.sdk.xdr.XdrDataInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class StellarTransferTransactionValidator extends TransferTransactionValidator
    implements CoinsTransactionValidator {

  public StellarTransferTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.stellar, jsonSerializer);
  }

  public TransactionValidationResult validate(
      byte[] unsignedTransaction, NetworkType networkType, String publicKey, String document)
      throws UnknownNetworkTypeException, IOException, InvalidDocumentException {
    var transfer = getTransfer(document);

    var validationResult = validate(transfer, networkType);

    if (!validationResult.isValid()) return validationResult;

    if (!transfer.getValue().getAsset().getSymbol().equals(blockchainProtocol.getCoin())) {
      return TransactionValidationResult.CreateInvalid(
          String.format(
              "Invalid asset: %s, expected %s",
              blockchainProtocol.getCoin(), transfer.getValue().getAsset().getSymbol()));
    }

    final var network = NetworkMapper.mapToStellarNetworkType(networkType);
    final var transactionEnvelope =
        TransactionEnvelope.decode(
            new XdrDataInputStream(new ByteArrayInputStream(unsignedTransaction)));
    final var transaction = (Transaction) Transaction.fromEnvelopeXdr(transactionEnvelope, network);

    if (!transfer.getSource().getAddress().equals(transaction.getSourceAccount())) {
      return TransactionValidationResult.CreateInvalid(
          String.format(
              "Invalid source address: %s, expected %s",
              transaction.getSourceAccount(), transfer.getSource().getAddress()));
    }

    for (Operation operation : transaction.getOperations()) {
      if (operation instanceof PaymentOperation) {
        final var paymentOperation = (PaymentOperation) operation;
        if (!transfer.getSource().getAddress().equals(paymentOperation.getSourceAccount())) {
          return TransactionValidationResult.CreateInvalid(
              String.format(
                  "Invalid source address: %s, expected %s",
                  operation.getSourceAccount(), transfer.getSource().getAddress()));
        }

        if (!transfer.getDestination().getAddress().equals(paymentOperation.getDestination())) {
          return TransactionValidationResult.CreateInvalid(
              String.format(
                  "Invalid destination address: %s, expected %s",
                  ((PaymentOperation) operation).getDestination(),
                  transfer.getDestination().getAddress()));
        }

        final var amount = new BigDecimal(paymentOperation.getAmount());
        if (transfer.getValue().getAmount().compareTo(amount) != 0) {
          return TransactionValidationResult.CreateInvalid(
              String.format(
                  "Invalid transaction amount: %s, expected %s",
                  amount.toString(), transfer.getValue().getAmount().toString()));
        }
      }
    }

    return TransactionValidationResult.CreateValid();
  }
}
