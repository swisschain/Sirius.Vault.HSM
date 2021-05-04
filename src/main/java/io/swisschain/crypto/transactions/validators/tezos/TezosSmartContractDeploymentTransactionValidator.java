package io.swisschain.crypto.transactions.validators.tezos;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transactions.CoinsTransactionValidator;
import io.swisschain.crypto.transactions.TransactionValidationResult;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.crypto.transactions.exceptions.InvalidInputsException;
import io.swisschain.crypto.transactions.validators.SmartContractDeploymentTransactionValidator;
import io.swisschain.crypto.utils.tezos.forging.LocalForge;
import io.swisschain.crypto.utils.tezos.forging.operations.RevealContent;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.services.JsonSerializer;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class TezosSmartContractDeploymentTransactionValidator
    extends SmartContractDeploymentTransactionValidator implements CoinsTransactionValidator {

  public TezosSmartContractDeploymentTransactionValidator(JsonSerializer jsonSerializer) {
    super(BlockchainProtocolCodes.tezos, jsonSerializer);
  }

  public TransactionValidationResult validate(
      byte[] unsignedTransaction, NetworkType networkType, String publicKey, String document)
      throws IOException, InvalidInputsException, NoSuchAlgorithmException,
          InvalidDocumentException {
    var smartContractDeployment = getSmartContractDeployment(document);

    var validationResult = validate(smartContractDeployment, networkType);

    if (!validationResult.isValid()) return validationResult;

    var unforged = new LocalForge().UnforgeOperations(unsignedTransaction);

    if (unforged.size() > 2) {
      return TransactionValidationResult.CreateInvalid(
          "Unexpected operation count. "
              + "Expected 2 (reveal + transaction) at max, "
              + "but got "
              + unforged.size());
    }

    if (unforged.size() == 2) {
      var reveal =
          (RevealContent)
              unforged.stream()
                  .filter((op) -> op instanceof RevealContent)
                  .findFirst()
                  .orElseThrow();

      if (!reveal.PublicKey.equals(publicKey)) {
        return TransactionValidationResult.CreateInvalid(
            "Unexpected public key revelation."
                + " Expected "
                + publicKey
                + ", "
                + "but got "
                + reveal.PublicKey);
      }
    }

    //    var transaction =
    //        (TransactionContent)
    //            unforged.stream()
    //                .filter((op) -> op instanceof TransactionContent)
    //                .findFirst()
    //                .orElseThrow();
    //
    //    if (!transaction.Destination.equals(transferDetails.getAsset().getAddress())) {
    //      return TransactionValidationResult.CreateInvalid(
    //          "Unexpected smart contract address. "
    //              + "Expected "
    //              + transferDetails.getDestinationAddress().getAddress()
    //              + ", "
    //              + "but got "
    //              + transaction.Destination);
    //    }

    // TODO unforge source/destination/amount parameters from FA12/FA2 transfers and validate them

    return TransactionValidationResult.CreateValid();
  }
}
