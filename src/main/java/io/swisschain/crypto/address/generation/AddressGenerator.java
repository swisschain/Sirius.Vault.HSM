package io.swisschain.crypto.address.generation;

import io.swisschain.crypto.exceptions.InvalidPublicKeyException;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.primitives.NetworkType;

import java.io.IOException;

public interface AddressGenerator {
  AddressGenerationResult generate(NetworkType networkType)
      throws InvalidPublicKeyException, UnknownNetworkTypeException, IOException,
          OperationFailedException, OperationExhaustedException;
}
