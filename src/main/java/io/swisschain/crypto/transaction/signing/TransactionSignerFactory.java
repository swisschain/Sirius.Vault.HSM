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
        new CoinsTransactionSignerRetryDecorator(
            new HsmBitcoinCoinTransactionSigner(
                config.clients.hsmApi, BlockchainProtocolCodes.bitcoin)));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.ethereum,
        new CoinsTransactionSignerRetryDecorator(
            new HsmEthereumTransactionSigner(
                config.clients.hsmApi, BlockchainProtocolCodes.ethereum)));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.ethereumClassic,
        new CoinsTransactionSignerRetryDecorator(
            new HsmEthereumTransactionSigner(
                config.clients.hsmApi, BlockchainProtocolCodes.ethereumClassic)));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.litecoin,
        new CoinsTransactionSignerRetryDecorator(
            new HsmLitecoinCoinTransactionSigner(
                config.clients.hsmApi, BlockchainProtocolCodes.litecoin)));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.stellar,
        new CoinsTransactionSignerRetryDecorator(
            new HsmStellarTransactionSigner(
                config.clients.hsmApi, BlockchainProtocolCodes.stellar)));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.bitcoinCash,
        new CoinsTransactionSignerRetryDecorator(
            new HsmBitcoinCashCoinTransactionSigner(
                config.clients.hsmApi, BlockchainProtocolCodes.bitcoinCash)));
  }

  public CoinsTransactionSigner getCoinsTransactionSigner(BlockchainProtocolCodes code)
      throws BlockchainNotSupportedException {
    if (!coinTransactionSignersMap.containsKey(code)) throw new BlockchainNotSupportedException();

    return coinTransactionSignersMap.get(code);
  }
}
