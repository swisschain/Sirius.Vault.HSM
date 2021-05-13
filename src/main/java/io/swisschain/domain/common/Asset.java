package io.swisschain.domain.common;

public class Asset {
  private long id;
  private String symbol;
  private String address;

  public Asset() {}

  public Asset(long id, String symbol, String address) {
    this.id = id;
    this.symbol = symbol;
    this.address = address;
  }

  public long getId() {
    return id;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getAddress() {
    return address;
  }
}
