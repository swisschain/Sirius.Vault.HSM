package io.swisschain.crypto.altcoins.litecoin;

public class MainNetParams extends org.bitcoinj.params.MainNetParams {
  public MainNetParams() {
    super();
    packetMagic = 0xdbb6c0fb;
    addressHeader = 48;
    p2shHeader = 50;
    targetTimespan = TARGET_TIMESPAN;
    dumpedPrivateKeyHeader = 176;
    segwitAddressHrp = "ltc";
    spendableCoinbaseDepth = 100;
    dnsSeeds = null;
    addrSeeds = null;
    bip32HeaderP2PKHpub = 0x0488b21e; // The 4 byte header that serializes in base58 to "tpub".
    bip32HeaderP2PKHpriv = 0x0488ade4; // The 4 byte header that serializes in base58 to "tprv"

    id = ID_MAINNET;
  }

  private static MainNetParams instance;
  public static synchronized MainNetParams get() {
    if (instance == null) {
      instance = new MainNetParams();
    }
    return instance;
  }

  @Override
  public String getPaymentProtocolId() {
    return PAYMENT_PROTOCOL_ID_MAINNET;
  }
}
