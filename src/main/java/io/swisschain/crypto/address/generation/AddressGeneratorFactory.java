package io.swisschain.crypto.address.generation;

import io.swisschain.config.Config;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.address.generation.generators.*;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;

import java.util.HashMap;
import java.util.Map;

public class AddressGeneratorFactory {
  private final Config config;
  private final Map<BlockchainProtocolCodes, AddressGenerator> generatorMap = new HashMap<>();

  public AddressGeneratorFactory(Config config) {
    this.config = config;
    initGenerators();
  }

  private void initGenerators() {
//    generatorMap.put(
//        BlockchainProtocolCodes.bitcoin, new HsmBitcoinAddressGenerator(config.hsmConfig));
    generatorMap.put(
        BlockchainProtocolCodes.ethereum, new HsmEthereumAddressGenerator(config.hsmConfig));
    generatorMap.put(
        BlockchainProtocolCodes.litecoin, new HsmLitecoinAddressGenerator(config.hsmConfig));
    generatorMap.put(
        BlockchainProtocolCodes.stellar, new HsmStellarAddressGenerator(config.hsmConfig));
    generatorMap.put(
        BlockchainProtocolCodes.bitcoinCash, new HsmBitcoinCashAddressGenerator(config.hsmConfig));
  }

  public AddressGenerator get(BlockchainProtocolCodes code) throws BlockchainNotSupportedException {
    if (!generatorMap.containsKey(code)) throw new BlockchainNotSupportedException();

    return generatorMap.get(code);
  }
}
