package io.swisschain.crypto.altcoins.litecoin;

public class TestNet3Params extends org.bitcoinj.params.TestNet3Params {
  private static TestNet3Params instance;

  public TestNet3Params() {
    super();
    packetMagic = 0xf1c8d2fd;
    segwitAddressHrp = "tltc";
    id = "ltc-test";
  }

  public static synchronized TestNet3Params get() {
    if (instance == null) {
      instance = new TestNet3Params();
    }
    return instance;
  }
}
