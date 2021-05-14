package io.swisschain.crypto.transactions.validators.ethereum;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.CoinsTransactionValidator;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.crypto.transactions.validators.SmartContractDeploymentTransactionValidator;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.TransactionDecoder;

import static org.apache.commons.codec.binary.Hex.encodeHexString;

public abstract class BaseEthereumSmartContractDeploymentTransactionValidator
    extends SmartContractDeploymentTransactionValidator implements CoinsTransactionValidator {

  protected BaseEthereumSmartContractDeploymentTransactionValidator(
      BlockchainProtocolCodes blockchainProtocol, JsonSerializer jsonSerializer) {
    super(blockchainProtocol, jsonSerializer);
  }

  public TransactionValidationResult validate(
      byte[] unsignedTransaction, NetworkType networkType, String publicKey, String document)
      throws InvalidDocumentException {
    var smartContractDeployment = getSmartContractDeployment(document);

    var validationResult = validate(smartContractDeployment, networkType);

    if (!validationResult.isValid()) 
        return validationResult;

    return TransactionValidationResult.CreateValid();
  }

  private static byte[] padZeros(byte[] src, int padSize) {
    byte[] result = new byte[padSize];
    System.arraycopy(src, 0, result, padSize - src.length, src.length);
    return result;
  }
}
