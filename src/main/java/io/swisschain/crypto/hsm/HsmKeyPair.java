package io.swisschain.crypto.hsm;

public class HsmKeyPair {
  public final byte[] encryptedPrivateKey;
  public final byte[] publicKey;

  public HsmKeyPair(byte[] encryptedPrivateKey, byte[] publicKey) {
    this.encryptedPrivateKey = encryptedPrivateKey;
    this.publicKey = publicKey;
  }
}
