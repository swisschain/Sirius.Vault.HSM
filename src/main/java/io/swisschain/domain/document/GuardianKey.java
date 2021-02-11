package io.swisschain.domain.document;

public class GuardianKey {
  private String publicKey;

  public GuardianKey(String publicKey) {
    this.publicKey = publicKey;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }
}
