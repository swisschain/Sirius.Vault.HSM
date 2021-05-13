package io.swisschain.crypto.transactions.validators.ethereum;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.CoinsTransactionValidator;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.crypto.transactions.validators.SmartContractInvocationTransactionValidator;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.TransactionDecoder;

import static org.apache.commons.codec.binary.Hex.encodeHexString;

public abstract class BaseEthereumSmartContractInvocationTransactionValidator
    extends SmartContractInvocationTransactionValidator implements CoinsTransactionValidator {

  protected BaseEthereumSmartContractInvocationTransactionValidator(
      BlockchainProtocolCodes blockchainProtocol, JsonSerializer jsonSerializer) {
    super(blockchainProtocol, jsonSerializer);
  }

  public TransactionValidationResult validate(
      byte[] unsignedTransaction, NetworkType networkType, String publicKey, String document)
      throws InvalidDocumentException {
    var smartContractInvocation = getSmartContractInvocation(document);

    var validationResult = validate(smartContractInvocation, networkType);

    if (!validationResult.isValid()) return validationResult;

    final var transaction = TransactionDecoder.decode(encodeHexString(unsignedTransaction));

    final var lowerCaseData = transaction.getData().toLowerCase();

    final var paddedAddress =
        Hex.toHexString(
                padZeros(
                    Hex.decode(
                        smartContractInvocation
                            .getInvoker()
                            .getAddress()
                            .toLowerCase()
                            .substring(2)),
                    32))
            .toLowerCase();

    if (!lowerCaseData.contains(paddedAddress)) {
      return TransactionValidationResult.CreateInvalid(
          "No destination address detected in contract params");
    }

    return TransactionValidationResult.CreateValid();
  }

  private static byte[] padZeros(byte[] src, int padSize) {
    byte[] result = new byte[padSize];
    System.arraycopy(src, 0, result, padSize - src.length, src.length);
    return result;
  }
}
