package io.swisschain.services;

public class BlockchainAssetId {
  private final String symbol;
  private final String address;

  public BlockchainAssetId(String symbol, String address) {

    this.symbol = symbol;
    this.address = address;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getAddress() {
    return address;
  }

  @Override
  public String toString() {
    return "BlockchainAssetId{" + "symbol='" + symbol + '\'' + ", address='" + address + '\'' + '}';
  }
}
