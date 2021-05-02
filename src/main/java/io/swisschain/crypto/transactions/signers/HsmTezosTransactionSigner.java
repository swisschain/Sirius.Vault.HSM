package io.swisschain.crypto.transactions.signers;

import io.swisschain.config.clients.HsmApiConfig;
import io.swisschain.crypto.hsm.HsmConnector;
import io.swisschain.crypto.transactions.CoinsTransactionSigner;
import io.swisschain.crypto.transactions.TransactionSigningResult;
import io.swisschain.crypto.utils.tezos.Base58Helper;
import io.swisschain.crypto.utils.tezos.Blacke2bHelper;
import io.swisschain.crypto.utils.tezos.BytesHelper;
import io.swisschain.crypto.utils.tezos.Prefix;
import io.swisschain.domain.common.Coin;
import io.swisschain.domain.primitives.NetworkType;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class HsmTezosTransactionSigner extends HsmConnector implements CoinsTransactionSigner {
  public HsmTezosTransactionSigner(HsmApiConfig hsmConfig) {
    super(hsmConfig);
  }

  @Override
  public TransactionSigningResult sign(
      byte[] unsignedTransaction,
      List<Coin> coins,
      String privateKey,
      String publicKey,
      NetworkType networkType)
      throws IOException, NoSuchAlgorithmException {
    var signed = sign(unsignedTransaction, Hex.decode(privateKey));
    return new TransactionSigningResult(getTransactionId(signed), signed);
  }

  private byte[] sign(byte[] unsignedTransaction, byte[] privateKey) throws IOException {
    var operation = BytesHelper.concat(new byte[] {3}, unsignedTransaction);
    var signature = signEd25519(Blacke2bHelper.getDigest(operation, 256), privateKey);

    return BytesHelper.concat(unsignedTransaction, signature);
  }

  private String getTransactionId(byte[] transaction) throws NoSuchAlgorithmException {
    var hash = Blacke2bHelper.getDigest(transaction, 256);
    return Base58Helper.convert(hash, Prefix.o);
  }
}
