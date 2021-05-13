package io.swisschain.crypto.transactions.validators.ethereum;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.services.JsonSerializer;

public class EthereumSmartContractInvocationTransactionValidator
    extends BaseEthereumSmartContractInvocationTransactionValidator {
  public EthereumSmartContractInvocationTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.ethereum, jsonSerializer);
  }
}
