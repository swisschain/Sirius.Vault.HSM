package io.swisschain.crypto.transaction.signing.signers;

import io.swisschain.config.HsmConfig;
import io.swisschain.contracts.TransferDetails;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.NetworkMapper;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.transaction.signing.CoinsTransactionSigner;
import io.swisschain.crypto.transaction.signing.TransactionSigningResult;
import io.swisschain.crypto.transaction.signing.exceptions.InvalidInputsException;
import io.swisschain.crypto.transaction.signing.exceptions.TransactionSignException;
import io.swisschain.crypto.transaction.signing.exceptions.TransferDetailsValidationException;
import io.swisschain.crypto.transaction.signing.exceptions.UnsupportedScriptException;
import io.swisschain.primitives.NetworkType;
import io.swisschain.services.Coin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.List;

public class HsmLitecoinCoinTransactionSigner extends HsmBitcoinBasedTransactionSigner
    implements CoinsTransactionSigner {
  public static final String LTC = "LTC";
  private static final Logger logger = LogManager.getLogger();

  private final BlockchainProtocolCodes blockchain;

  public HsmLitecoinCoinTransactionSigner(HsmConfig hsmConfig, BlockchainProtocolCodes blockchain) {
    super(hsmConfig, blockchain);
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
    var result =
        sign(
            unsignedTransaction,
            coins,
            privateKey,
            publicKey,
            NetworkMapper.mapToLitecoinNetworkType(networkType),
            transferDetails,
            BlockchainProtocolCodes.litecoin.getName(),
            networkType.name(),
            blockchain.getCoin());
    logger.debug("TxId: {}", result.getTransactionId());
    logger.debug("Signed: {}", Hex.toHexString(result.getSignedTransaction()));
    return result;
  }
}
