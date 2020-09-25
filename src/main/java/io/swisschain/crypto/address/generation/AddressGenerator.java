package io.swisschain.crypto.address.generation;

import io.swisschain.crypto.exceptions.InvalidPublicKeyException;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.primitives.NetworkType;

import java.io.IOException;

public interface AddressGenerator {
  AddressGenerationResult generate(NetworkType networkType)
      throws InvalidPublicKeyException, UnknownNetworkTypeException, IOException;
}
