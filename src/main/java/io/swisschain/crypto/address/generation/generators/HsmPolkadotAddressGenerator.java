package io.swisschain.crypto.address.generation.generators;

import io.swisschain.config.clients.IbmApiConfig;
import io.swisschain.crypto.address.generation.AddressGenerationResult;
import io.swisschain.crypto.address.generation.AddressGenerator;
import io.swisschain.crypto.exceptions.InvalidPublicKeyException;
import io.swisschain.crypto.hsm.HsmConnector;
import io.swisschain.crypto.utils.polkadot.AddressCodec;
import io.swisschain.crypto.utils.polkadot.PolkadotSS58Format;
import io.swisschain.domain.primitives.NetworkType;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class HsmPolkadotAddressGenerator extends HsmConnector implements AddressGenerator {
  private static final Logger logger = LogManager.getLogger();

  public HsmPolkadotAddressGenerator(IbmApiConfig ibmApiConfig) {
    super(ibmApiConfig);
  }

  @Override
  public AddressGenerationResult generate(NetworkType networkType)
      throws InvalidPublicKeyException, IOException, NoSuchAlgorithmException {

    final var keyPair = generateED25519KeyPair();

    byte prefix;
    switch (networkType){
      case Private:
      case Test:
        prefix= PolkadotSS58Format.westend;
        break;
      case Public:
        prefix= PolkadotSS58Format.polkadot;
        break;
      default:
        throw new InvalidPublicKeyException("Unexpected network type " + networkType.name());
    }
    var address = AddressCodec.encodeAddress(keyPair.publicKey, prefix);
    var publicKey = Hex.encodeHexString(keyPair.publicKey);
    logger.info("[" + networkType.name() + "] New polkadot address generated by HSM: " + address);

    return new AddressGenerationResult(
        address, Hex.encodeHexString(keyPair.encryptedPrivateKey), null, publicKey);
  }
}
