package io.swisschain.crypto.transactions.validators.tezos;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.CoinsTransactionValidator;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.crypto.transactions.exceptions.InvalidInputsException;
import io.swisschain.crypto.transactions.validators.TransferTransactionValidator;
import io.swisschain.crypto.utils.tezos.forging.LocalForge;
import io.swisschain.crypto.utils.tezos.forging.operations.RevealContent;
import io.swisschain.crypto.utils.tezos.forging.operations.TransactionContent;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

public class TezosTransferTransactionValidator extends TransferTransactionValidator
    implements CoinsTransactionValidator {

  public TezosTransferTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.tezos, jsonSerializer);
  }

  public TransactionValidationResult validate(
      byte[] unsignedTransaction, NetworkType networkType, String publicKey, String document)
      throws IOException, InvalidInputsException, NoSuchAlgorithmException,
          InvalidDocumentException {
    var transfer = getTransfer(document);

    var validationResult = validate(transfer, networkType);

    if (!validationResult.isValid()) return validationResult;

    var unforged = new LocalForge().UnforgeOperations(unsignedTransaction);

    if (unforged.size() > 2) {
      return TransactionValidationResult.CreateInvalid(
          "Unexpected operation count. "
              + "Expected 2 (reveal + transaction) at max, "
              + "but got "
              + unforged.size());
    }

    var totalFee = 0;

    if (unforged.size() == 2) {
      var reveal =
          (RevealContent)
              unforged.stream()
                  .filter((op) -> op instanceof RevealContent)
                  .findFirst()
                  .orElseThrow();
      totalFee += reveal.Fee;

      if (!reveal.PublicKey.equals(publicKey)) {
        return TransactionValidationResult.CreateInvalid(
            "Unexpected public key revelation."
                + " Expected "
                + publicKey
                + ", "
                + "but got "
                + reveal.PublicKey);
      }
    }

    var transaction =
        (TransactionContent)
            unforged.stream()
                .filter((op) -> op instanceof TransactionContent)
                .findFirst()
                .orElseThrow();
    totalFee += transaction.Fee;

    if (totalFee > ToMicrotez(transfer.getFee().getAmount())) {
      return TransactionValidationResult.CreateInvalid(
          "Unexpected fee value."
              + " Expected "
              + ToMicrotez(transfer.getFee().getAmount())
              + " microtez, "
              + "but got "
              + totalFee
              + " microtez");
    }

    if (!transaction.Source.equals(transfer.getSource().getAddress())) {
      return TransactionValidationResult.CreateInvalid(
          "Unexpected source address. "
              + "Expected "
              + transfer.getSource().getAddress()
              + ", "
              + "but got "
              + transaction.Source);
    }

    if (!transaction.Destination.equals(transfer.getDestination().getAddress())) {
      return TransactionValidationResult.CreateInvalid(
          "Unexpected destination address. "
              + "Expected "
              + transfer.getDestination().getAddress()
              + ", "
              + "but got "
              + transaction.Destination);
    }

    if (transaction.Amount > ToMicrotez(transfer.getValue().getAmount())) {
      return TransactionValidationResult.CreateInvalid(
          "Unexpected amount. "
              + "Expected "
              + ToMicrotez(transfer.getValue().getAmount())
              + " microtez, "
              + "but got "
              + transaction.Amount
              + " microtez");
    }

    return TransactionValidationResult.CreateValid();
  }

  private static long ToMicrotez(BigDecimal source) {
    return source.multiply(BigDecimal.valueOf(Math.pow(10, 6))).longValue();
  }
}
