package io.swisschain.crypto.transaction.signing.validators;

import io.swisschain.contracts.TransferDetails;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.transaction.signing.exceptions.TransferDetailsValidationException;
import org.stellar.sdk.Operation;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Transaction;

import java.math.BigDecimal;

public class StellarTransactionValidator {
  public static boolean validate(
      Transaction transaction,
      TransferDetails transferDetails,
      String blockchain,
      String networkType,
      String asset)
      throws TransferDetailsValidationException, BlockchainNotSupportedException {
    if (!transferDetails.getBlockchain().getProtocolId().equals(blockchain)) {
      throw new TransferDetailsValidationException(
          String.format(
              "Invalid blockchain: %s, expected %s",
              blockchain, transferDetails.getBlockchain().getProtocolId()));
    }

    if (!transferDetails.getBlockchain().getNetworkType().name().equals(networkType)) {
      throw new TransferDetailsValidationException(
          String.format(
              "Invalid networkType: %s, expected %s",
              networkType, transferDetails.getBlockchain().getNetworkType()));
    }

    if (!transferDetails.getAsset().getSymbol().equals(asset)) {
      throw new TransferDetailsValidationException(
          String.format(
              "Invalid asset: %s, expected %s", asset, transferDetails.getAsset().getSymbol()));
    }

    if (!transferDetails.getSourceAddress().getAddress().equals(transaction.getSourceAccount())) {
      throw new TransferDetailsValidationException(
          String.format(
              "Invalid source address: %s, expected %s",
              transaction.getSourceAccount(), transferDetails.getSourceAddress().getAddress()));
    }

    for (Operation operation : transaction.getOperations()) {
      if (operation instanceof PaymentOperation) {
        final var paymentOperation = (PaymentOperation) operation;
        if (!transferDetails
            .getSourceAddress()
            .getAddress()
            .equals(paymentOperation.getSourceAccount())) {
          throw new TransferDetailsValidationException(
              String.format(
                  "Invalid source address: %s, expected %s",
                  operation.getSourceAccount(), transferDetails.getSourceAddress().getAddress()));
        }

        if (!transferDetails
            .getDestinationAddress()
            .getAddress()
            .equals(paymentOperation.getDestination())) {
          throw new TransferDetailsValidationException(
              String.format(
                  "Invalid destination address: %s, expected %s",
                  ((PaymentOperation) operation).getDestination(),
                  transferDetails.getDestinationAddress().getAddress()));
        }

        final var amount = new BigDecimal(paymentOperation.getAmount());
        if (transferDetails.getAmount().compareTo(amount) != 0) {
          throw new TransferDetailsValidationException(
              String.format(
                  "Invalid transaction amount: %s, expected %s",
                  amount.toString(), transferDetails.getAmount().toString()));
        }
      }
    }

    return true;
  }
}
