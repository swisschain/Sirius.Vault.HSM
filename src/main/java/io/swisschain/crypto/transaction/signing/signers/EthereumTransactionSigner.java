package io.swisschain.crypto.transaction.signing.signers;

import io.swisschain.crypto.transaction.signing.CoinsTransactionSigner;
import io.swisschain.crypto.transaction.signing.TransactionSigningResult;
import io.swisschain.primitives.NetworkType;
import io.swisschain.services.Coin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.*;

import java.util.List;

public class EthereumTransactionSigner implements CoinsTransactionSigner {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public TransactionSigningResult sign(
      byte[] unsignedTransaction,
      List<Coin> coins,
      String privateKey,
      String publicKey,
      NetworkType networkType) {
    final RawTransaction transaction = TransactionDecoder.decode(new String(unsignedTransaction));
    final var signedMessage =
        TransactionEncoder.signMessage(transaction, Credentials.create(privateKey));

    final var result =
        new TransactionSigningResult(Hex.toHexString(Hash.sha3(signedMessage)), signedMessage);
    logger.debug("TxId: {}", result.getTransactionId());
    logger.debug("Signed: {}", Hex.toHexString(signedMessage));

    return result;
  }
}
