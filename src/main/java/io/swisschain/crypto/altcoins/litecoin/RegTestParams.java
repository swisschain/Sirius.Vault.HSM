package io.swisschain.crypto.altcoins.litecoin;

public class RegTestParams extends org.bitcoinj.params.RegTestParams {
  private static RegTestParams instance;

  public RegTestParams() {
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

    id = "ltc-reg";

    majorityEnforceBlockUpgrade = MainNetParams.MAINNET_MAJORITY_ENFORCE_BLOCK_UPGRADE;
    majorityRejectBlockOutdated = MainNetParams.MAINNET_MAJORITY_REJECT_BLOCK_OUTDATED;
    majorityWindow = MainNetParams.MAINNET_MAJORITY_WINDOW;
  }

  public static synchronized RegTestParams get() {
    if (instance == null) {
      instance = new RegTestParams();
    }
    return instance;
  }

  @Override
  public String getPaymentProtocolId() {
    return PAYMENT_PROTOCOL_ID_REGTEST;
  }
}
