package io.swisschain.crypto.transactions.validators.ethereum;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.services.JsonSerializer;

public class EthereumClassicSmartContractDeploymentTransactionValidator
    extends BaseEthereumTransferTransactionValidator {
  public EthereumClassicSmartContractDeploymentTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.ethereumClassic, jsonSerializer);
  }
}
