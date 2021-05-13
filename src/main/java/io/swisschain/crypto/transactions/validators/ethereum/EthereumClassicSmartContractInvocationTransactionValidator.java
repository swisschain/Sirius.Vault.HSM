package io.swisschain.crypto.transactions.validators.ethereum;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.services.JsonSerializer;

public class EthereumClassicSmartContractInvocationTransactionValidator
    extends BaseEthereumSmartContractInvocationTransactionValidator {
  public EthereumClassicSmartContractInvocationTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.ethereumClassic, jsonSerializer);
  }
}
