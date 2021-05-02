package io.swisschain.crypto.transactions.validators.ethereum;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.services.JsonSerializer;

public class EthereumTransferTransactionValidator extends BaseEthereumTransferTransactionValidator {
  public EthereumTransferTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.ethereum, jsonSerializer);
  }
}
