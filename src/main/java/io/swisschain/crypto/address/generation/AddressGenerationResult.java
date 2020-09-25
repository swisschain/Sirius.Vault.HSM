package io.swisschain.crypto.address.generation;

import java.util.Objects;

public class AddressGenerationResult {
  private String address;
  private String privateKey;
  private String bip39Mnemonic;
  private String publicKey;

  public AddressGenerationResult(
      String address, String privateKey, String bip39Mnemonic, String publicKey) {
    this.address = address;
    this.privateKey = privateKey;
    this.bip39Mnemonic = bip39Mnemonic;
    this.publicKey = publicKey;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  public String getBip39Mnemonic() {
    return bip39Mnemonic;
  }

  public void setBip39Mnemonic(String bip39Mnemonic) {
    this.bip39Mnemonic = bip39Mnemonic;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddressGenerationResult that = (AddressGenerationResult) o;
    return Objects.equals(address, that.address)
        && Objects.equals(privateKey, that.privateKey)
        && Objects.equals(bip39Mnemonic, that.bip39Mnemonic)
        && Objects.equals(publicKey, that.publicKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, privateKey, bip39Mnemonic, publicKey);
  }

  @Override
  public String toString() {
    return "AddressGenerationResult{"
        + "address='"
        + address
        + '\''
        + ", privateKey='"
        + privateKey
        + '\''
        + ", bip39Mnemonic='"
        + bip39Mnemonic
        + '\''
        + ", publicKey='"
        + publicKey
        + '\''
        + '}';
  }
}
