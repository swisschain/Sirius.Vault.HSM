package io.swisschain.crypto.transaction.signing.signers;

import io.swisschain.config.HsmConfig;
import io.swisschain.crypto.NetworkMapper;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.hsm.HsmConnector;
import io.swisschain.crypto.transaction.signing.CoinsTransactionSigner;
import io.swisschain.crypto.transaction.signing.TransactionSigningResult;
import io.swisschain.crypto.transaction.signing.exceptions.TransactionSignException;
import io.swisschain.primitives.NetworkType;
import io.swisschain.services.Coin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;
import org.postgresql.util.Base64;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.xdr.Signature;
import org.stellar.sdk.xdr.TransactionEnvelope;
import org.stellar.sdk.xdr.XdrDataInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class HsmStellarTransactionSigner extends HsmConnector implements CoinsTransactionSigner {
  private static final Logger logger = LogManager.getLogger();

  public HsmStellarTransactionSigner(HsmConfig hsmConfig) {
    super(hsmConfig);
  }

  @Override
  public TransactionSigningResult sign(
      byte[] unsignedTransaction,
      List<Coin> coins,
      String privateKey,
      String publicKey,
      NetworkType networkType)
      throws IOException, TransactionSignException, UnknownNetworkTypeException {
    final var network = NetworkMapper.mapToStellarNetworkType(networkType);
    final var transactionEnvelope =
        TransactionEnvelope.decode(
            new XdrDataInputStream(new ByteArrayInputStream(unsignedTransaction)));
    final var transaction = Transaction.fromEnvelopeXdr(transactionEnvelope, network);

    final var txHash = transaction.hash();
    final var signature = sign(txHash, Hex.decode(privateKey));
    transaction.sign(signature.getSignature());

    final var transactionId = transaction.hashHex();
    final var signedBytes = Base64.decode(transaction.toEnvelopeXdrBase64());

    final var result = new TransactionSigningResult(transactionId, signedBytes);
    logger.debug("TxId: {}", result.getTransactionId());
    logger.debug("Signed: {}", Hex.toHexString(result.getSignedTransaction()));

    return result;
  }

  private Signature sign(byte[] hash, byte[] privateKey) throws IOException {
    final var signature = new Signature();
    signature.setSignature(signEd25519(hash, privateKey));
    return signature;
  }
}
