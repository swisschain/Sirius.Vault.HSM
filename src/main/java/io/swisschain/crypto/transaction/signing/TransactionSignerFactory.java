package io.swisschain.crypto.transaction.signing;

import io.swisschain.config.AppConfig;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.transaction.signing.signers.*;

import java.util.HashMap;
import java.util.Map;

public class TransactionSignerFactory {
  private final AppConfig config;
  private final Map<BlockchainProtocolCodes, CoinsTransactionSigner> coinTransactionSignersMap =
      new HashMap<>();

  public TransactionSignerFactory(AppConfig config) {
    this.config = config;
    initCoinTransactionSigners();
  }

  private void initCoinTransactionSigners() {
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.bitcoin,
        new HsmBitcoinCoinTransactionSigner(config.clients.hsmApi, BlockchainProtocolCodes.bitcoin));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.ethereum,
        new HsmEthereumTransactionSigner(config.clients.hsmApi, BlockchainProtocolCodes.ethereum));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.ethereumClassic,
        new HsmEthereumTransactionSigner(
            config.clients.hsmApi, BlockchainProtocolCodes.ethereumClassic));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.litecoin,
        new HsmLitecoinCoinTransactionSigner(config.clients.hsmApi, BlockchainProtocolCodes.litecoin));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.stellar,
        new HsmStellarTransactionSigner(config.clients.hsmApi, BlockchainProtocolCodes.stellar));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.bitcoinCash,
        new HsmBitcoinCashCoinTransactionSigner(
            config.clients.hsmApi, BlockchainProtocolCodes.bitcoinCash));
  }

  public CoinsTransactionSigner getCoinsTransactionSigner(BlockchainProtocolCodes code)
      throws BlockchainNotSupportedException {
    if (!coinTransactionSignersMap.containsKey(code)) throw new BlockchainNotSupportedException();

    return coinTransactionSignersMap.get(code);
  }
}
