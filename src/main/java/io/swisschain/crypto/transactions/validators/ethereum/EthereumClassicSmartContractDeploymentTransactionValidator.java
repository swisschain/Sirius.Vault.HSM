package io.swisschain.crypto.transactions.validators.ethereum;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.services.JsonSerializer;

public class EthereumClassicSmartContractDeploymentTransactionValidator
    extends BaseEthereumSmartContractDeploymentTransactionValidator {
  public EthereumClassicSmartContractDeploymentTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.ethereumClassic, jsonSerializer);
  }
}
