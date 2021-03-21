package io.swisschain.crypto.transaction.signing.signers;

import io.swisschain.contracts.TransferDetails;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.transaction.signing.CoinsTransactionSigner;
import io.swisschain.crypto.transaction.signing.TransactionSigningResult;
import io.swisschain.crypto.transaction.signing.exceptions.InvalidInputsException;
import io.swisschain.crypto.transaction.signing.exceptions.TransactionSignException;
import io.swisschain.crypto.transaction.signing.exceptions.TransferDetailsValidationException;
import io.swisschain.crypto.transaction.signing.exceptions.UnsupportedScriptException;
import io.swisschain.primitives.NetworkType;
import io.swisschain.domain.transfers.Coin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.*;

import java.io.IOException;
import java.util.List;

public class EthereumTransactionSigner implements CoinsTransactionSigner {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public TransactionSigningResult sign(
      byte[] unsignedTransaction,
      List<Coin> coins,
      String privateKey,
      String publicKey,
      NetworkType networkType,
      TransferDetails transferDetails)
      throws UnknownNetworkTypeException, InvalidInputsException, IOException,
          UnsupportedScriptException, TransactionSignException, BlockchainNotSupportedException,
          TransferDetailsValidationException {
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
