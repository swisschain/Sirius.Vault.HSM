package io.swisschain.domain.common;

import java.math.BigDecimal;

public class Coin {
  private final CoinId id;
  private final Asset asset;
  private final BigDecimal value;
  private final String address;
  private final String redeem;

  public Coin(CoinId id, Asset asset, BigDecimal value, String address, String redeem) {

    this.id = id;
    this.asset = asset;
    this.value = value;
    this.address = address;
    this.redeem = redeem;
  }

  public CoinId getId() {
    return id;
  }

  public Asset getAsset() {
    return asset;
  }

  public BigDecimal getValue() {
    return value;
  }

  public String getAddress() {
    return address;
  }

  public String getRedeem() {
    return redeem;
  }
}
