package io.swisschain.crypto.transactions;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.validators.bitcoin.BitcoinTransferTransactionValidator;
import io.swisschain.crypto.transactions.validators.bitcoin_cash.BitcoinCashTransferTransactionValidator;
import io.swisschain.crypto.transactions.validators.ethereum.*;
import io.swisschain.crypto.transactions.validators.litecoin.LitecoinTransferTransactionValidator;
import io.swisschain.crypto.transactions.validators.stellar.StellarTransferTransactionValidator;
import io.swisschain.crypto.transactions.validators.tezos.TezosSmartContractDeploymentTransactionValidator;
import io.swisschain.crypto.transactions.validators.tezos.TezosSmartContractInvocationTransactionValidator;
import io.swisschain.crypto.transactions.validators.tezos.TezosTransferTransactionValidator;
import io.swisschain.domain.transactions.TransactionType;
import io.swisschain.services.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class TransactionValidatorFactory {
  private final JsonSerializer jsonSerializer;

  private final Map<BlockchainProtocolCodes, CoinsTransactionValidator>
      transferTransactionValidatorsMap = new HashMap<>();

  private final Map<BlockchainProtocolCodes, CoinsTransactionValidator>
      smartContractDeploymentTransactionValidatorsMap = new HashMap<>();

  private final Map<BlockchainProtocolCodes, CoinsTransactionValidator>
      smartContractInvocationTransactionValidatorsMap = new HashMap<>();

  public TransactionValidatorFactory(JsonSerializer jsonSerializer) {
    this.jsonSerializer = jsonSerializer;
    initTransferTransactionValidators();
    initSmartContractDeploymentTransactionValidators();
    initSmartContractInvocationTransactionValidators();
  }

  private void initTransferTransactionValidators() {
    transferTransactionValidatorsMap.put(
        BlockchainProtocolCodes.bitcoin, new BitcoinTransferTransactionValidator(jsonSerializer));
    transferTransactionValidatorsMap.put(
        BlockchainProtocolCodes.bitcoinCash,
        new BitcoinCashTransferTransactionValidator(jsonSerializer));
    transferTransactionValidatorsMap.put(
        BlockchainProtocolCodes.ethereum, new EthereumTransferTransactionValidator(jsonSerializer));
    transferTransactionValidatorsMap.put(
        BlockchainProtocolCodes.ethereumClassic,
        new EthereumClassicTransferTransactionValidator(jsonSerializer));
    transferTransactionValidatorsMap.put(
        BlockchainProtocolCodes.litecoin, new LitecoinTransferTransactionValidator(jsonSerializer));
    transferTransactionValidatorsMap.put(
        BlockchainProtocolCodes.stellar, new StellarTransferTransactionValidator(jsonSerializer));
    transferTransactionValidatorsMap.put(
        BlockchainProtocolCodes.tezos, new TezosTransferTransactionValidator(jsonSerializer));
  }

  private void initSmartContractDeploymentTransactionValidators() {
    smartContractDeploymentTransactionValidatorsMap.put(
        BlockchainProtocolCodes.ethereum,
        new EthereumSmartContractDeploymentTransactionValidator(jsonSerializer));
    smartContractDeploymentTransactionValidatorsMap.put(
        BlockchainProtocolCodes.ethereumClassic,
        new EthereumClassicSmartContractDeploymentTransactionValidator(jsonSerializer));
    smartContractDeploymentTransactionValidatorsMap.put(
        BlockchainProtocolCodes.tezos,
        new TezosSmartContractDeploymentTransactionValidator(jsonSerializer));
  }

  private void initSmartContractInvocationTransactionValidators() {
    smartContractInvocationTransactionValidatorsMap.put(
        BlockchainProtocolCodes.ethereum,
        new EthereumSmartContractInvocationTransactionValidator(jsonSerializer));
    smartContractInvocationTransactionValidatorsMap.put(
        BlockchainProtocolCodes.ethereumClassic,
        new EthereumClassicSmartContractInvocationTransactionValidator(jsonSerializer));
    smartContractInvocationTransactionValidatorsMap.put(
        BlockchainProtocolCodes.tezos,
        new TezosSmartContractInvocationTransactionValidator(jsonSerializer));
  }

  public CoinsTransactionValidator get(BlockchainProtocolCodes code, TransactionType type) {
    Map<BlockchainProtocolCodes, CoinsTransactionValidator> map = null;

    switch (type) {
      case Transfer:
        map = transferTransactionValidatorsMap;
        break;
      case SmartContractDeployment:
        map = smartContractDeploymentTransactionValidatorsMap;
        break;
      case SmartContractInvocation:
        map = smartContractInvocationTransactionValidatorsMap;
        break;
    }

    if (map == null || !map.containsKey(code)) return null;

    return map.get(code);
  }
}
