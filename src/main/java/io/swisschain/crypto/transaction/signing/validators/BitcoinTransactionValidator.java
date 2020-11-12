package io.swisschain.crypto.transaction.signing.validators;

import io.swisschain.contracts.TransferDetails;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.transaction.signing.exceptions.TransferDetailsValidationException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Utils;
import org.bitcoinj.script.ScriptPattern;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class BitcoinTransactionValidator {
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

    final var outputsSumMap = new HashMap<String, Long>();
    for (TransactionOutput output : transaction.getOutputs()) {
      final var destination = getDestinationAddress(output);
      outputsSumMap.put(
          destination, outputsSumMap.getOrDefault(destination, 0L) + output.getValue().value);
    }

    final var transactionAmount =
        outputsSumMap.getOrDefault(transferDetails.getDestinationAddress().getAddress(), 0L);
    if (transferDetails
            .getAmount()
            .multiply(BigDecimal.valueOf(org.bitcoinj.core.Coin.COIN.value))
            .longValue()
        != transactionAmount) {
      throw new TransferDetailsValidationException(
          String.format(
              "Invalid transaction amount: %s, expected %s",
              BigDecimal.valueOf(transactionAmount)
                  .divide(
                      BigDecimal.valueOf(org.bitcoinj.core.Coin.COIN.value),
                      Coin.COIN.smallestUnitExponent(),
                      RoundingMode.CEILING)
                  .toString(),
              transferDetails.getAmount().toString()));
    }
    return true;
  }

  private static String getDestinationAddress(TransactionOutput output)
      throws BlockchainNotSupportedException {
    final var script = output.getScriptPubKey();
    if (!ScriptPattern.isP2PKH(script)
        && !ScriptPattern.isP2WPKH(script)
        && !ScriptPattern.isP2SH(script)) {
      if (ScriptPattern.isP2PK(script)) {
        return Utils.HEX.encode(ScriptPattern.extractKeyFromP2PK(script));
      } else if (ScriptPattern.isSentToMultisig(script)) {
        throw new BlockchainNotSupportedException("Multisig is not supported");
      } else {
        throw new BlockchainNotSupportedException("Unknown type of ");
      }
    } else {
      return script.getToAddress(output.getParams()).toString();
    }
  }
}
