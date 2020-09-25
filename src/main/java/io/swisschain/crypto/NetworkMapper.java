package io.swisschain.crypto;

import io.swisschain.crypto.altcoins.litecoin.MainNetParams;
import io.swisschain.crypto.altcoins.litecoin.RegTestParams;
import io.swisschain.crypto.altcoins.litecoin.TestNet3Params;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.primitives.NetworkType;
import org.bitcoinj.core.NetworkParameters;
import org.stellar.sdk.Network;

import java.util.HashMap;
import java.util.Map;

public class NetworkMapper {

  private static final Map<NetworkParameters, NetworkType> bitcoinToInternalNetworkDictionary =
      new HashMap<>();
  private static final Map<NetworkType, NetworkParameters> internalToBitcoinNetworkDictionary =
      new HashMap<>();

  private static final Map<NetworkParameters, NetworkType> litecoinToInternalNetworkDictionary =
      new HashMap<>();
  private static final Map<NetworkType, NetworkParameters> internalToLitecoinNetworkDictionary =
      new HashMap<>();

  private static final Map<Network, NetworkType> stellarToInternalNetworkDictionary =
      new HashMap<>();
  private static final Map<NetworkType, Network> internalToStellarNetworkDictionary =
      new HashMap<>();

  static {
    bitcoinToInternalNetworkDictionary.put(
        NetworkParameters.fromID(NetworkParameters.ID_REGTEST), NetworkType.Private);
    bitcoinToInternalNetworkDictionary.put(
        NetworkParameters.fromID(NetworkParameters.ID_TESTNET), NetworkType.Test);
    bitcoinToInternalNetworkDictionary.put(
        NetworkParameters.fromID(NetworkParameters.ID_MAINNET), NetworkType.Public);

    for (Map.Entry<NetworkParameters, NetworkType> entry :
        bitcoinToInternalNetworkDictionary.entrySet()) {
      internalToBitcoinNetworkDictionary.put(entry.getValue(), entry.getKey());
    }

    litecoinToInternalNetworkDictionary.put(RegTestParams.get(), NetworkType.Private);
    litecoinToInternalNetworkDictionary.put(TestNet3Params.get(), NetworkType.Test);
    litecoinToInternalNetworkDictionary.put(MainNetParams.get(), NetworkType.Public);

    for (Map.Entry<NetworkParameters, NetworkType> entry :
        litecoinToInternalNetworkDictionary.entrySet()) {
      internalToLitecoinNetworkDictionary.put(entry.getValue(), entry.getKey());
    }

    stellarToInternalNetworkDictionary.put(Network.TESTNET, NetworkType.Test);
    stellarToInternalNetworkDictionary.put(Network.PUBLIC, NetworkType.Public);

    for (Map.Entry<Network, NetworkType> entry : stellarToInternalNetworkDictionary.entrySet()) {
      internalToStellarNetworkDictionary.put(entry.getValue(), entry.getKey());
    }
  }

  public static NetworkParameters mapToBitcoinNetworkType(NetworkType networkType)
      throws UnknownNetworkTypeException {
    if (!internalToBitcoinNetworkDictionary.containsKey(networkType)) {
      throw new UnknownNetworkTypeException("");
    }
    return internalToBitcoinNetworkDictionary.get(networkType);
  }

  public static NetworkType mapFromBitcoinNetworkType(NetworkParameters networkParameters)
      throws UnknownNetworkTypeException {
    if (!bitcoinToInternalNetworkDictionary.containsKey(networkParameters)) {
      throw new UnknownNetworkTypeException("");
    }
    return bitcoinToInternalNetworkDictionary.get(networkParameters);
  }

  public static NetworkParameters mapToLitecoinNetworkType(NetworkType networkType)
      throws UnknownNetworkTypeException {
    if (!internalToLitecoinNetworkDictionary.containsKey(networkType)) {
      throw new UnknownNetworkTypeException("");
    }
    return internalToLitecoinNetworkDictionary.get(networkType);
  }

  public static NetworkType mapFromLitecoinNetworkType(NetworkParameters networkParameters)
      throws UnknownNetworkTypeException {
    if (!litecoinToInternalNetworkDictionary.containsKey(networkParameters)) {
      throw new UnknownNetworkTypeException("");
    }
    return litecoinToInternalNetworkDictionary.get(networkParameters);
  }

  public static Network mapToStellarNetworkType(NetworkType networkType)
      throws UnknownNetworkTypeException {
    if (!internalToStellarNetworkDictionary.containsKey(networkType)) {
      throw new UnknownNetworkTypeException("");
    }
    return internalToStellarNetworkDictionary.get(networkType);
  }

  public static NetworkType mapFromStellarNetworkType(Network network)
      throws UnknownNetworkTypeException {
    if (!stellarToInternalNetworkDictionary.containsKey(network)) {
      throw new UnknownNetworkTypeException("");
    }
    return stellarToInternalNetworkDictionary.get(network);
  }
}
