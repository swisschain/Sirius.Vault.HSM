package io.swisschain.crypto.address.generation;

import io.swisschain.config.AppConfig;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.address.generation.generators.*;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;

import java.util.HashMap;
import java.util.Map;

public class AddressGeneratorFactory {
  private final AppConfig config;
  private final Map<BlockchainProtocolCodes, AddressGenerator> generatorMap = new HashMap<>();

  public AddressGeneratorFactory(AppConfig config) {
    this.config = config;
    initGenerators();
  }

  private void initGenerators() {
    generatorMap.put(
        BlockchainProtocolCodes.bitcoin,
        new AddressGeneratorRetryDecorator(new HsmBitcoinAddressGenerator(config.clients.hsmApi)));
    generatorMap.put(
        BlockchainProtocolCodes.ethereum,
        new AddressGeneratorRetryDecorator(
            new HsmEthereumAddressGenerator(
                config.clients.hsmApi, BlockchainProtocolCodes.ethereum)));
    generatorMap.put(
        BlockchainProtocolCodes.ethereumClassic,
        new AddressGeneratorRetryDecorator(
            new HsmEthereumAddressGenerator(
                config.clients.hsmApi, BlockchainProtocolCodes.ethereumClassic)));
    generatorMap.put(
        BlockchainProtocolCodes.litecoin,
        new AddressGeneratorRetryDecorator(new HsmLitecoinAddressGenerator(config.clients.hsmApi)));
    generatorMap.put(
        BlockchainProtocolCodes.stellar,
        new AddressGeneratorRetryDecorator(new HsmStellarAddressGenerator(config.clients.hsmApi)));
    generatorMap.put(
        BlockchainProtocolCodes.bitcoinCash,
        new AddressGeneratorRetryDecorator(
            new HsmBitcoinCashAddressGenerator(config.clients.hsmApi)));
  }

  public AddressGenerator get(BlockchainProtocolCodes code) throws BlockchainNotSupportedException {
    if (!generatorMap.containsKey(code)) throw new BlockchainNotSupportedException();

    return generatorMap.get(code);
  }
}
