package io.swisschain.crypto.altcoins.litecoin;

public class RegTestParams extends org.bitcoinj.params.RegTestParams {
  private static RegTestParams instance;

  public RegTestParams() {
    super();
    packetMagic = 0xdab5bffa;
    segwitAddressHrp = "rltc";
    id = "ltc-reg";
  }

  public static synchronized RegTestParams get() {
    if (instance == null) {
      instance = new RegTestParams();
    }
    return instance;
  }
}
