package io.swisschain.crypto.transaction.signing;

import io.swisschain.contracts.TransferDetails;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.transaction.signing.exceptions.InvalidInputsException;
import io.swisschain.crypto.transaction.signing.exceptions.TransactionSignException;
import io.swisschain.crypto.transaction.signing.exceptions.TransferDetailsValidationException;
import io.swisschain.crypto.transaction.signing.exceptions.UnsupportedScriptException;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.primitives.NetworkType;
import io.swisschain.domain.transfers.Coin;

import java.io.IOException;
import java.util.List;

public interface CoinsTransactionSigner {
  TransactionSigningResult sign(
      byte[] unsignedTransaction,
      List<Coin> coins,
      String privateKey,
      String publicKey,
      NetworkType networkType,
      TransferDetails transferDetails)
      throws UnknownNetworkTypeException, InvalidInputsException, IOException,
          UnsupportedScriptException, TransactionSignException, BlockchainNotSupportedException,
          TransferDetailsValidationException, OperationFailedException, OperationExhaustedException;
}
