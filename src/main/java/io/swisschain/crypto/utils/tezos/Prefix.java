package io.swisschain.crypto.utils.tezos;

public class Prefix {

  /// <summary>
  /// Private key prefix for Ed25519 (tz1)
  /// </summary>
  public static final byte[] edsk = {13, 15, 58, 7};

  /// <summary>
  /// Address prefix for Ed25519
  /// </summary>
  public static final byte[] tz1 = {6, (byte) 161, (byte) 159};

  /// <summary>
  /// Address prefix for Secp256k1
  /// </summary>
  public static final byte[] tz2 = new byte[] {6, (byte) 161, (byte) 161};

  /// <summary>
  /// Address prefix for Nistp256
  /// </summary>
  public static final byte[] tz3 = new byte[] {6, (byte) 161, (byte) 164};

  /// <summary>
  /// Address prefix for originated contract
  /// </summary>
  public static final byte[] KT1 = new byte[] {2, 90, 121};

  /// <summary>
  /// Public key prefix for Ed25519 (tz1)
  /// </summary>
  public static final byte[] edpk = new byte[] {13, 15, 37, (byte) 217};

  /// <summary>
  /// Public key prefix for Secp256k1 (tz2)
  /// </summary>
  public static byte[] sppk = new byte[] {3, (byte) 254, (byte) 226, 86};

  /// <summary>
  /// Public key prefix for Nistp256 (tz3)
  /// </summary>
  public static byte[] p2pk = new byte[] {3, (byte) 178, (byte) 139, 127};

  /// <summary>
  /// Operation hash prefix
  /// </summary>
  public static final byte[] o = new byte[] {5, 116};

  /// <summary>
  /// Block hash prefix
  /// </summary>
  public static final byte[] B = new byte[] {1, 52};
}
