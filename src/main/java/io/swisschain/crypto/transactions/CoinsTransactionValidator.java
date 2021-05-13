package io.swisschain.crypto.transactions;

import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.transactions.exceptions.InvalidDocumentException;
import io.swisschain.crypto.transactions.exceptions.InvalidInputsException;
import io.swisschain.domain.primitives.NetworkType;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface CoinsTransactionValidator {
  TransactionValidationResult validate(
      byte[] unsignedTransaction, NetworkType networkType, String publicKey, String document)
      throws BlockchainNotSupportedException, UnknownNetworkTypeException, InvalidInputsException,
          IOException, NoSuchAlgorithmException, InvalidDocumentException;
}
