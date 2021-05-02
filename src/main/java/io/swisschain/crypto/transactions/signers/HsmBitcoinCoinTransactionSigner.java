package io.swisschain.crypto.transactions.signers;

import io.swisschain.config.clients.HsmApiConfig;
import io.swisschain.crypto.NetworkMapper;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.transactions.CoinsTransactionSigner;
import io.swisschain.crypto.transactions.TransactionSigningResult;
import io.swisschain.crypto.transactions.exceptions.InvalidInputsException;
import io.swisschain.crypto.transactions.exceptions.UnsupportedScriptException;
import io.swisschain.domain.common.Coin;
import io.swisschain.domain.primitives.NetworkType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.List;

public class HsmBitcoinCoinTransactionSigner extends HsmBitcoinBasedTransactionSigner
    implements CoinsTransactionSigner {
  private static final Logger logger = LogManager.getLogger();

  public HsmBitcoinCoinTransactionSigner(HsmApiConfig hsmConfig) {
    super(hsmConfig);
  }

  @Override
  public TransactionSigningResult sign(
      byte[] unsignedTransaction,
      List<Coin> coins,
      String privateKeys,
      String publicKey,
      NetworkType networkType)
      throws UnknownNetworkTypeException, InvalidInputsException, IOException,
          UnsupportedScriptException {
    var result =
        sign(
            unsignedTransaction,
            coins,
            privateKeys,
            publicKey,
            NetworkMapper.mapToBitcoinNetworkType(networkType));
    logger.debug("TxId: {}", result.getTransactionId());
    logger.debug("Signed: {}", Hex.toHexString(result.getSignedTransaction()));
    return result;
  }
}
