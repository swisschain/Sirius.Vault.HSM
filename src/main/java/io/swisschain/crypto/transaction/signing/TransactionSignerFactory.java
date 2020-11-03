package io.swisschain.crypto.transaction.signing;

import io.swisschain.config.Config;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transaction.signing.signers.*;

import java.util.HashMap;
import java.util.Map;

public class TransactionSignerFactory {
  private final Config config;
  private final Map<BlockchainProtocolCodes, CoinsTransactionSigner> coinTransactionSignersMap =
      new HashMap<>();

  public TransactionSignerFactory(Config config) {
    this.config = config;
    initCoinTransactionSigners();
  }

  private void initCoinTransactionSigners() {
//    coinTransactionSignersMap.put(
//        BlockchainProtocolCodes.bitcoin, new HsmBitcoinCoinTransactionSigner(config.hsmConfig));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.ethereum, new HsmEthereumTransactionSigner(config.hsmConfig));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.litecoin, new HsmLitecoinCoinTransactionSigner(config.hsmConfig));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.stellar, new HsmStellarTransactionSigner(config.hsmConfig));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.bitcoinCash,
        new HsmBitcoinCashCoinTransactionSigner(config.hsmConfig));
  }

  public CoinsTransactionSigner getCoinsTransactionSigner(BlockchainProtocolCodes code) {
    return coinTransactionSignersMap.get(code);
  }
}
