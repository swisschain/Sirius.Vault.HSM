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
        new AddressGeneratorRetryDecorator(new HsmBitcoinAddressGenerator(config.clients.ibmApi)));
    generatorMap.put(
        BlockchainProtocolCodes.ethereum,
        new AddressGeneratorRetryDecorator(
            new HsmEthereumAddressGenerator(
                config.clients.ibmApi, BlockchainProtocolCodes.ethereum)));
    generatorMap.put(
        BlockchainProtocolCodes.ethereumClassic,
        new AddressGeneratorRetryDecorator(
            new HsmEthereumAddressGenerator(
                config.clients.ibmApi, BlockchainProtocolCodes.ethereumClassic)));
    generatorMap.put(
        BlockchainProtocolCodes.litecoin,
        new AddressGeneratorRetryDecorator(new HsmLitecoinAddressGenerator(config.clients.ibmApi)));
    generatorMap.put(
        BlockchainProtocolCodes.stellar,
        new AddressGeneratorRetryDecorator(new HsmStellarAddressGenerator(config.clients.ibmApi)));
    generatorMap.put(
        BlockchainProtocolCodes.bitcoinCash,
        new AddressGeneratorRetryDecorator(
            new HsmBitcoinCashAddressGenerator(config.clients.ibmApi)));
    generatorMap.put(
            BlockchainProtocolCodes.tezos,
            new AddressGeneratorRetryDecorator(
                    new HsmTezosAddressGenerator(config.clients.ibmApi)));
  }

  public AddressGenerator get(BlockchainProtocolCodes code) throws BlockchainNotSupportedException {
    if (!generatorMap.containsKey(code)) throw new BlockchainNotSupportedException();

    return generatorMap.get(code);
  }
}
