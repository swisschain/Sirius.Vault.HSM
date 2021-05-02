package io.swisschain.crypto.transactions;

import io.swisschain.config.AppConfig;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.transactions.signers.*;

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
            new HsmBitcoinCoinTransactionSigner(config.clients.hsmApi)));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.ethereum,
        new CoinsTransactionSignerRetryDecorator(
            new HsmEthereumTransactionSigner(config.clients.hsmApi)));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.ethereumClassic,
        new CoinsTransactionSignerRetryDecorator(
            new HsmEthereumTransactionSigner(config.clients.hsmApi)));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.litecoin,
        new CoinsTransactionSignerRetryDecorator(
            new HsmLitecoinCoinTransactionSigner(config.clients.hsmApi)));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.stellar,
        new CoinsTransactionSignerRetryDecorator(
            new HsmStellarTransactionSigner(config.clients.hsmApi)));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.bitcoinCash,
        new CoinsTransactionSignerRetryDecorator(
            new HsmBitcoinCashCoinTransactionSigner(config.clients.hsmApi)));
    coinTransactionSignersMap.put(
        BlockchainProtocolCodes.tezos,
        new CoinsTransactionSignerRetryDecorator(
            new HsmTezosTransactionSigner(config.clients.hsmApi)));
  }

  public CoinsTransactionSigner get(BlockchainProtocolCodes code)
      throws BlockchainNotSupportedException {
    if (!coinTransactionSignersMap.containsKey(code)) throw new BlockchainNotSupportedException();

    return coinTransactionSignersMap.get(code);
  }
}
