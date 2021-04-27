package io.swisschain.crypto;

public enum BlockchainProtocolCodes {
  bitcoin("bitcoin", "BTC"),
  tezos("tezos", "XTZ"),
  litecoin("litecoin", "LTC"),
  ethereum("ethereum", "ETH"),
  ethereumClassic("ethereum-classic", "ETC"),
  stellar("stellar", "XLM"),
  bitcoinCash("bitcoin-cash", "BCH");

  private final String name;
  private final String coin;

  BlockchainProtocolCodes(String name, String coin) {
    this.name = name;
    this.coin = coin;
  }

  public static BlockchainProtocolCodes fromString(String name) {
    for (BlockchainProtocolCodes b : BlockchainProtocolCodes.values()) {
      if (b.name.equalsIgnoreCase(name)) {
        return b;
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }

  public String getCoin() {
    return coin;
  }
}
