package io.swisschain.crypto.transactions.validators.ethereum;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.services.JsonSerializer;

public class EthereumSmartContractDeploymentTransactionValidator
    extends BaseEthereumSmartContractDeploymentTransactionValidator {
  public EthereumSmartContractDeploymentTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.ethereum, jsonSerializer);
  }
}
