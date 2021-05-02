package io.swisschain.crypto.transactions.validators.ethereum;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.services.JsonSerializer;

public class EthereumClassicTransferTransactionValidator
    extends BaseEthereumTransferTransactionValidator {
  public EthereumClassicTransferTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.ethereumClassic, jsonSerializer);
  }
}
