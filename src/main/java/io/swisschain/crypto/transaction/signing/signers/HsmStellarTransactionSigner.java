package io.swisschain.crypto.transaction.signing.signers;

import io.swisschain.config.HsmConfig;
import io.swisschain.crypto.NetworkMapper;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.hsm.HsmConnector;
import io.swisschain.crypto.transaction.signing.CoinsTransactionSigner;
import io.swisschain.crypto.transaction.signing.TransactionSigningResult;
import io.swisschain.primitives.NetworkType;
import io.swisschain.services.Coin;
import org.apache.commons.codec.binary.Base32;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;
import org.postgresql.util.Base64;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.xdr.DecoratedSignature;
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
      throws IOException, UnknownNetworkTypeException {
    final var network = NetworkMapper.mapToStellarNetworkType(networkType);
    final var transactionEnvelope =
        TransactionEnvelope.decode(
            new XdrDataInputStream(new ByteArrayInputStream(unsignedTransaction)));
    final var rawTransaction = Transaction.fromEnvelopeXdr(transactionEnvelope, network);

    final var txHash = rawTransaction.hash();
    final var signature = sign(txHash, Hex.decode(privateKey), new Base32().decode(publicKey));

    final var transactionEnvelopeV1 = transactionEnvelope.getV1();
    transactionEnvelopeV1.setSignatures(new DecoratedSignature[] {signature});
    final var transaction = Transaction.fromV1EnvelopeXdr(transactionEnvelopeV1, network);

    final var transactionId = transaction.hashHex();
    final var signedBytes = Base64.decode(transaction.toEnvelopeXdrBase64());

    final var result = new TransactionSigningResult(transactionId, signedBytes);
    logger.debug("TxId: {}", result.getTransactionId());
    logger.debug("Signed: {}", Hex.toHexString(result.getSignedTransaction()));

    return result;
  }

  private DecoratedSignature sign(byte[] hash, byte[] privateKey, byte[] publicKey)
      throws IOException {
    final var signature = new Signature();
    signature.setSignature(signEd25519(hash, privateKey));

    DecoratedSignature decoratedSignature = new DecoratedSignature();
    decoratedSignature.setHint(KeyPair.fromPublicKey(publicKey).getSignatureHint());
    decoratedSignature.setSignature(signature);

    return decoratedSignature;
  }
}
