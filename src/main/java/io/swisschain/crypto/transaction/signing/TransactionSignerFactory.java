package io.swisschain.crypto.transaction.signing;

import io.swisschain.config.Config;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
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
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.bitcoin,
        new HsmBitcoinCoinTransactionSigner(config.hsmConfig, BlockchainProtocolCodes.bitcoin));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.ethereum,
        new HsmEthereumTransactionSigner(config.hsmConfig, BlockchainProtocolCodes.ethereum));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.ethereumClassic,
        new HsmEthereumTransactionSigner(
            config.hsmConfig, BlockchainProtocolCodes.ethereumClassic));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.litecoin,
        new HsmLitecoinCoinTransactionSigner(config.hsmConfig, BlockchainProtocolCodes.litecoin));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.stellar,
        new HsmStellarTransactionSigner(config.hsmConfig, BlockchainProtocolCodes.stellar));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.bitcoinCash,
        new HsmBitcoinCashCoinTransactionSigner(
            config.hsmConfig, BlockchainProtocolCodes.bitcoinCash));
  }

  public CoinsTransactionSigner getCoinsTransactionSigner(BlockchainProtocolCodes code)
      throws BlockchainNotSupportedException {
    if (!coinTransactionSignersMap.containsKey(code)) throw new BlockchainNotSupportedException();

    return coinTransactionSignersMap.get(code);
  }
}
