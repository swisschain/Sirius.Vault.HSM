package io.swisschain.crypto.altcoins.litecoin;

public class TestNet3Params extends org.bitcoinj.params.TestNet3Params {
  public TestNet3Params() {
    super();
    packetMagic = 0xdab5bffa;
    addressHeader = 111;
    p2shHeader = 58;
    targetTimespan = TARGET_TIMESPAN;
    dumpedPrivateKeyHeader = 239;
    segwitAddressHrp = "rltc";
    spendableCoinbaseDepth = 100;
    dnsSeeds = null;
    addrSeeds = null;
    bip32HeaderP2PKHpub = 0x043587cf; // The 4 byte header that serializes in base58 to "tpub".
    bip32HeaderP2PKHpriv = 0x04358394; // The 4 byte header that serializes in base58 to "tprv"

    id = ID_TESTNET;

    majorityEnforceBlockUpgrade = MainNetParams.MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
    majorityRejectBlockOutdated = MainNetParams.MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
    majorityWindow = MainNetParams.MAINNET_MAJORITY_WINDOW;
  }

  private static TestNet3Params instance;
  public static synchronized TestNet3Params get() {
    if (instance == null) {
      instance = new TestNet3Params();
    }
    return instance;
  }

  @Override
  public String getPaymentProtocolId() {
    return PAYMENT_PROTOCOL_ID_REGTEST;
  }
}
