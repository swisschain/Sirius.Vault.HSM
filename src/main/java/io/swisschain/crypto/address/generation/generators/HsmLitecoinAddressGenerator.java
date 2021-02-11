package io.swisschain.crypto.address.generation.generators;

import io.swisschain.config.clients.HsmApiConfig;
import io.swisschain.crypto.NetworkMapper;
import io.swisschain.crypto.address.generation.AddressGenerationResult;
import io.swisschain.crypto.address.generation.AddressGenerator;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.primitives.NetworkType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class HsmLitecoinAddressGenerator extends HsmBitcoinBasedAddressGenerator
    implements AddressGenerator {
  private static final Logger logger = LogManager.getLogger();

  public HsmLitecoinAddressGenerator(HsmApiConfig hsmConfig) {
    super(hsmConfig);
  }

  @Override
  public AddressGenerationResult generate(NetworkType networkType)
      throws UnknownNetworkTypeException, IOException {
    var result = generateSegwitAddress(NetworkMapper.mapToLitecoinNetworkType(networkType));
    logger.info(
        "["
            + networkType.name()
            + "]New litecoin address generated by HSM: "
            + result.getAddress());
    return result;
  }
}
