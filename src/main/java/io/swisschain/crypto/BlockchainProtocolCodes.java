package io.swisschain.crypto;

public enum BlockchainProtocolCodes {
  bitcoin("bitcoin"),
  litecoin("litecoin"),
  ethereum("ethereum"),
  stellar("stellar");

  private final String name;

  BlockchainProtocolCodes(String name) {
    this.name = name;
  }
}
