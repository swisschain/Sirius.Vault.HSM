package io.swisschain.crypto.transaction.signing.signers;

import io.swisschain.config.HsmConfig;
import io.swisschain.contracts.TransferDetails;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.hsm.HsmConnector;
import io.swisschain.crypto.transaction.signing.CoinsTransactionSigner;
import io.swisschain.crypto.transaction.signing.TransactionSigningResult;
import io.swisschain.crypto.transaction.signing.exceptions.InvalidInputsException;
import io.swisschain.crypto.transaction.signing.exceptions.TransactionSignException;
import io.swisschain.crypto.transaction.signing.exceptions.TransferDetailsValidationException;
import io.swisschain.crypto.transaction.signing.exceptions.UnsupportedScriptException;
import io.swisschain.crypto.transaction.signing.validators.EthereumTransactionValidator;
import io.swisschain.primitives.NetworkType;
import io.swisschain.services.Coin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.*;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.codec.binary.Hex.encodeHexString;

public class HsmEthereumTransactionSigner extends HsmConnector implements CoinsTransactionSigner {
  private static final Logger logger = LogManager.getLogger();

  private final BlockchainProtocolCodes blockchain;

  public HsmEthereumTransactionSigner(HsmConfig hsmConfig, BlockchainProtocolCodes blockchain) {
    super(hsmConfig);
    this.blockchain = blockchain;
  }

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
    final var transaction = TransactionDecoder.decode(encodeHexString(unsignedTransaction));

    EthereumTransactionValidator.validate(
        transaction,
        transferDetails,
        BlockchainProtocolCodes.ethereum.getName(),
        networkType.name(),
        blockchain.getCoin());

    final var signedTransaction = sign(transaction, Hex.decode(privateKey), Hex.decode(publicKey));

    final var result =
        new TransactionSigningResult(
            "0x" + Hex.toHexString(Hash.sha3(signedTransaction)), signedTransaction);

    logger.debug("TxId: {}", result.getTransactionId());
    logger.debug("Signed: {}", Hex.toHexString(result.getSignedTransaction()));

    return result;
  }

  private byte[] sign(RawTransaction rawTransaction, byte[] privateKey, byte[] publicKey)
      throws IOException, TransactionSignException {
    final var encodedTransaction = TransactionEncoder.encode(rawTransaction);

    final var signatureData = signMessage(encodedTransaction, privateKey, publicKey);

    final var values = TransactionEncoder.asRlpValues(rawTransaction, signatureData);
    final var rlpList = new RlpList(values);
    return RlpEncoder.encode(rlpList);
  }

  private Sign.SignatureData signMessage(byte[] message, byte[] privateKey, byte[] publicKeyBytes)
      throws IOException, TransactionSignException {
    final var publicKey =
        new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));
    final var messageHash = Hash.sha3(message);

    final var signature = signECDSA(messageHash, privateKey);
    if (signature.length != 64) {
      throw new TransactionSignException(
          "Invalid signature from the key vault signing service, must be 64 bytes long");
    }

    final var R = new BigInteger(1, Arrays.copyOfRange(signature, 0, 32));
    final var S = new BigInteger(1, Arrays.copyOfRange(signature, 32, 64));

    final var initialSignature = new ECDSASignature(R, S);
    final var canonicalSignature = initialSignature.toCanonicalised();

    // Now we have to work backwards to figure out the recId needed to recover the signature.
    final var recId = recoverKeyIndex(canonicalSignature, messageHash, publicKey);
    if (recId == -1) {
      throw new TransactionSignException(
          "Could not construct a recoverable key. Are your credentials valid?");
    }

    final var headerByte = recId + 27;

    // 1 header + 32 bytes for R + 32 bytes for S
    final var v = new byte[] {(byte) headerByte};
    final var r = Numeric.toBytesPadded(canonicalSignature.r, 32);
    final var s = Numeric.toBytesPadded(canonicalSignature.s, 32);

    return new Sign.SignatureData(v, r, s);
  }

  private int recoverKeyIndex(
      final ECDSASignature sig, final byte[] hash, final BigInteger publicKey) {
    for (int i = 0; i < 4; i++) {
      final var k = Sign.recoverFromSignature(i, sig, hash);
      logger.trace("recovered key: {}", k);
      if (k != null && k.equals(publicKey)) {
        return i;
      }
    }
    return -1;
  }
}
