package io.swisschain.crypto.transaction.signing.validators;

import io.swisschain.contracts.TransferDetails;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.transaction.signing.exceptions.TransferDetailsValidationException;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.RawTransaction;
import org.web3j.utils.Convert;

public class EthereumTransactionValidator {
  public static boolean validate(
      RawTransaction transaction,
      TransferDetails transferDetails,
      String blockchain,
      String networkType,
      String asset)
      throws TransferDetailsValidationException, BlockchainNotSupportedException {
    if (!transferDetails.getBlockchain().getProtocolId().equalsIgnoreCase(blockchain)) {
      throw new TransferDetailsValidationException(
          String.format(
              "Invalid blockchain: %s, expected %s",
              transferDetails.getBlockchain().getProtocolId(), blockchain));
    }

    if (!transferDetails.getBlockchain().getNetworkType().name().equalsIgnoreCase(networkType)) {
      throw new TransferDetailsValidationException(
          String.format(
              "Invalid networkType: %s, expected %s",
              transferDetails.getBlockchain().getNetworkType(), networkType));
    }

    if (transferDetails.getAsset().getAddress() == null) {
      // ETH transaction
      if (!transferDetails.getAsset().getSymbol().equalsIgnoreCase(asset)) {
        throw new TransferDetailsValidationException(
            String.format(
                "Invalid asset: %s, expected %s", asset, transferDetails.getAsset().getSymbol()));
      }

      if (!transferDetails
          .getDestinationAddress()
          .getAddress()
          .equalsIgnoreCase(transaction.getTo())) {
        throw new TransferDetailsValidationException(
            String.format(
                "Invalid destination address: %s, expected %s",
                transaction.getTo(), transferDetails.getDestinationAddress().getAddress()));
      }

      final var amount = Convert.fromWei(transaction.getValue().toString(), Convert.Unit.ETHER);
      if (transferDetails.getAmount().compareTo(amount) < 0) {
        throw new TransferDetailsValidationException(
            String.format(
                "Invalid transaction amount: %s, expected %s",
                amount.toString(), transferDetails.getAmount().toString()));
      }

      final var fee = Convert.fromWei(transaction.getGasLimit().toString(), Convert.Unit.GWEI);
      if (transferDetails.getFeeLimit().compareTo(fee) < 0) {
        throw new TransferDetailsValidationException(
            String.format(
                "Invalid transaction fee: %s, expected %s",
                amount.toString(), transferDetails.getAmount().toString()));
      }
      return true;
    } else {
      // Smart contract transaction
      if (!transferDetails.getAsset().getAddress().equalsIgnoreCase(transaction.getTo())) {
        throw new TransferDetailsValidationException(
            String.format(
                "Invalid smart contract address: %s, expected %s",
                transaction.getTo(), transferDetails.getAsset().getAddress()));
      }

      final var lowerCaseData = transaction.getData().toLowerCase();

      final var paddedAddress =
          Hex.toHexString(
                  padZeros(
                      Hex.decode(
                          transferDetails
                              .getDestinationAddress()
                              .getAddress()
                              .toLowerCase()
                              .substring(2)),
                      32))
              .toLowerCase();

      if (!lowerCaseData.contains(paddedAddress)) {
        throw new TransferDetailsValidationException(
            "No destination address detected in contract params");
      }

      final var paddedAmount =
          Hex.toHexString(
                  padZeros(
                      Convert.toWei(transferDetails.getAmount(), Convert.Unit.ETHER)
                          .toBigInteger()
                          .toByteArray(),
                      32))
              .toLowerCase();
      if (!lowerCaseData.contains(paddedAmount)) {
        throw new TransferDetailsValidationException("No amount detected in contract params");
      }
      return true;
    }
  }

  private static byte[] padZeros(byte[] src, int padSize) {
    byte[] result = new byte[padSize];
    System.arraycopy(src, 0, result, padSize - src.length, src.length);
    return result;
  }
}
