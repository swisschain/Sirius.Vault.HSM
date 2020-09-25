package io.swisschain.crypto.utils;

import org.bitcoinj.core.ECKey;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.BigIntegers;

import java.math.BigInteger;

public class BTCUtils {
  public static ECPoint keyToPoint(byte[] publicKey) {
    BigInteger x = BigIntegers.fromUnsignedByteArray(publicKey, 1, 32);
    BigInteger y = BigIntegers.fromUnsignedByteArray(publicKey, 33, 32);
    return ECKey.CURVE.getCurve().createPoint(x, y);
  }

  public static ECPoint signatureToPoint(byte[] signature) {
    BigInteger x = BigIntegers.fromUnsignedByteArray(signature, 0, 32);
    BigInteger y = BigIntegers.fromUnsignedByteArray(signature, 32, 32);
    return ECKey.CURVE.getCurve().createPoint(x, y);
  }
}
