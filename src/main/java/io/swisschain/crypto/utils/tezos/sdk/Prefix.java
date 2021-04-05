package io.swisschain.crypto.utils.tezos.sdk;

public class Prefix {

    /// <summary>
    /// Private key prefix for Ed25519 (tz1)
    /// </summary>
    public static byte[] edsk = { 13, 15, 58, 7 };

    /// <summary>
    /// Address prefix for Ed25519
    /// </summary>
    public static byte[] tz1 = { 6, (byte) 161, (byte) 159};

    /// <summary>
    /// Public key prefix for Ed25519 (tz1)
    /// </summary>
    public static byte[] edpk = new byte[] { 13, 15, 37, (byte) 217};

    /// <summary>
    /// Operation hash prefix
    /// </summary>
    public static byte[] o = new byte[] { 5, 116 };
}
