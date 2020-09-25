package io.swisschain.crypto.altcoins.litecoin;

public class MainNetParams extends org.bitcoinj.params.MainNetParams {
  private static MainNetParams instance;

  public MainNetParams() {
    super();
    packetMagic = 0xdbb6c0fb;
    segwitAddressHrp = "ltc";
    id = "ltc-main";
  }

  public static synchronized MainNetParams get() {
    if (instance == null) {
      instance = new MainNetParams();
    }
    return instance;
  }
}
