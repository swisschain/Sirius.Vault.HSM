package io.swisschain.crypto;

public enum BlockchainProtocolCodes {
  bitcoin("bitcoin"),
  litecoin("litecoin"),
  ethereum("ethereum"),
  stellar("stellar"),
  bitcoinCash("bitcoin-cash");

  private final String name;

  BlockchainProtocolCodes(String name) {
    this.name = name;
  }

  public static BlockchainProtocolCodes fromString(String name) {
    for (BlockchainProtocolCodes b : BlockchainProtocolCodes.values()) {
      if (b.name.equalsIgnoreCase(name)) {
        return b;
      }
    }
    return null;
  }
}
