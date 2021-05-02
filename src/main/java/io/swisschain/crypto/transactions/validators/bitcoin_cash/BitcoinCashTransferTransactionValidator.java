package io.swisschain.crypto.transactions.validators.bitcoin_cash;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.NetworkMapper;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.transactions.CoinsTransactionValidator;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.crypto.transactions.validators.TransferTransactionValidator;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.domain.transactions.TransactionRejectionReason;
import io.swisschain.services.JsonSerializer;
import org.bitcoinjcash.core.Coin;
import org.bitcoinjcash.core.Transaction;
import org.bitcoinjcash.core.TransactionOutput;
import org.bitcoinjcash.core.Utils;
import org.bitcoinjcash.script.ScriptPattern;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class BitcoinCashTransferTransactionValidator extends TransferTransactionValidator
    implements CoinsTransactionValidator {

  public BitcoinCashTransferTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.bitcoinCash, jsonSerializer);
  }

  public TransactionValidationResult validate(
      byte[] unsignedTransaction, NetworkType networkType, String publicKey, String document)
      throws BlockchainNotSupportedException, UnknownNetworkTypeException,
          InvalidDocumentException {

    var transfer = getTransfer(document);

    var validationResult = validate(transfer, networkType);

    if (!validationResult.isValid()) return validationResult;

    final var transaction =
        new Transaction(
            NetworkMapper.mapToBitcoinCashNetworkType(networkType), unsignedTransaction);

    final var outputsSumMap = new HashMap<String, Long>();

    for (TransactionOutput output : transaction.getOutputs()) {
      final var destination = getDestinationAddress(output);
      outputsSumMap.put(
          destination, outputsSumMap.getOrDefault(destination, 0L) + output.getValue().value);
    }

    if (!outputsSumMap.containsKey(transfer.getDestination().getAddress())) {
      return TransactionValidationResult.CreateInvalid(
          TransactionRejectionReason.Other, "Invalid destination address");
    }

    final var transactionAmount =
        outputsSumMap.getOrDefault(transfer.getDestination().getAddress(), 0L);

    final var transferDetailsAmount =
        transfer.getValue().getAmount().multiply(BigDecimal.valueOf(Coin.COIN.value)).longValue();

    final var transferDetailsFeeLimit =
        transfer.getFee().getAmount().multiply(BigDecimal.valueOf(Coin.COIN.value)).longValue();

    if (transactionAmount > transferDetailsAmount) {
      return TransactionValidationResult.CreateInvalid(
          TransactionRejectionReason.Other,
          String.format(
              "Invalid transaction amount: %s, expected %s",
              BigDecimal.valueOf(transactionAmount)
                  .divide(
                      BigDecimal.valueOf(Coin.COIN.value),
                      Coin.COIN.smallestUnitExponent(),
                      RoundingMode.CEILING)
                  .toString(),
              transfer.getValue().getAmount().toString()));
    }

    final var payedFee = transferDetailsAmount - transactionAmount;

    if (payedFee > transferDetailsFeeLimit) {
      return TransactionValidationResult.CreateInvalid(
          TransactionRejectionReason.Other,
          String.format(
              "Transaction fee exceeded limit: %s, fee limit %s",
              BigDecimal.valueOf(payedFee)
                  .divide(
                      BigDecimal.valueOf(Coin.COIN.value),
                      Coin.COIN.smallestUnitExponent(),
                      RoundingMode.CEILING)
                  .toString(),
              transfer.getFee().getAmount().toString()));
    }

    return TransactionValidationResult.CreateValid();
  }

  private static String getDestinationAddress(TransactionOutput output)
      throws BlockchainNotSupportedException {
    final var script = output.getScriptPubKey();
    if (!ScriptPattern.isP2PKH(script) && !ScriptPattern.isP2SH(script)) {
      if (ScriptPattern.isP2PK(script)) {
        return Utils.HEX.encode(ScriptPattern.extractKeyFromP2PK(script));
      } else if (ScriptPattern.isSentToMultisig(script)) {
        throw new BlockchainNotSupportedException("Multisig is not supported");
      } else {
        throw new BlockchainNotSupportedException("Unknown type of ");
      }
    } else {
      return script.getToAddress(output.getParams()).toCash().toString();
    }
  }
}
