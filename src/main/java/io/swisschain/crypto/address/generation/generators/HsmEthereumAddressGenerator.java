package io.swisschain.crypto.address.generation.generators;

import io.swisschain.config.clients.HsmApiConfig;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.address.generation.AddressGenerationResult;
import io.swisschain.crypto.address.generation.AddressGenerator;
import io.swisschain.crypto.exceptions.InvalidPublicKeyException;
import io.swisschain.crypto.hsm.HsmConnector;
import io.swisschain.primitives.NetworkType;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.web3j.crypto.Keys;

import java.io.IOException;

public class HsmEthereumAddressGenerator extends HsmConnector implements AddressGenerator {
  private static final Logger logger = LogManager.getLogger();
  private static final int PUBLIC_KEY_LENGTH = 130;

  private final BlockchainProtocolCodes blockchain;

  public HsmEthereumAddressGenerator(HsmApiConfig hsmConfig, BlockchainProtocolCodes blockchain) {
    super(hsmConfig);
    this.blockchain = blockchain;
  }

  @Override
  public AddressGenerationResult generate(NetworkType networkType)
      throws InvalidPublicKeyException, IOException {
    final var keyPair = generateECDSAKeyPair();
    String publicKey = Hex.encodeHexString(keyPair.publicKey);

    if (publicKey.length() != PUBLIC_KEY_LENGTH) {
      logger.error("Invalid public key length {}", publicKey.length());
      throw new InvalidPublicKeyException(
          String.format(
              "Expected length: %s, actual length: %s, key: %s",
              PUBLIC_KEY_LENGTH, publicKey.length(), publicKey));
    }

    var address = Keys.toChecksumAddress(Keys.getAddress(publicKey.substring(2)));
    logger.info(
        "["
            + networkType.name()
            + "]New "
            + blockchain.getName()
            + " address generated by HSM: "
            + address);
    return new AddressGenerationResult(
        address, Hex.encodeHexString(keyPair.encryptedPrivateKey), null, publicKey);
  }
}
