package io.swisschain.crypto.address.generation.generators;

import io.swisschain.config.HsmConfig;
import io.swisschain.crypto.address.generation.AddressGenerationResult;
import io.swisschain.crypto.address.generation.AddressGenerator;
import io.swisschain.crypto.hsm.HsmConnector;
import io.swisschain.crypto.utils.BTCUtils;
import org.apache.commons.codec.binary.Hex;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.SegwitAddress;

import java.io.IOException;

public abstract class HsmBitcoinBasedAddressGenerator extends HsmConnector
    implements AddressGenerator {

  public HsmBitcoinBasedAddressGenerator(HsmConfig hsmConfig) {
    super(hsmConfig);
  }

  public AddressGenerationResult generate(NetworkParameters network) throws IOException {
    var keyPair = generateECDSAKeyPair();

    ECKey pubKey =
        ECKey.fromPublicOnly(
            ECKey.compressPoint(
                ECKey.CURVE.validatePublicPoint(BTCUtils.keyToPoint(keyPair.publicKey))));
    SegwitAddress segwitAddress = SegwitAddress.fromHash(network, pubKey.getPubKeyHash());
    return new AddressGenerationResult(
        segwitAddress.toBech32(),
        Hex.encodeHexString(keyPair.encryptedPrivateKey),
        null,
        pubKey.getPublicKeyAsHex());
  }
}
