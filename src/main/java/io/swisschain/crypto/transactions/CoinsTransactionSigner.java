package io.swisschain.crypto.transactions;

import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.transactions.exceptions.InvalidInputsException;
import io.swisschain.crypto.transactions.exceptions.TransactionSignException;
import io.swisschain.crypto.transactions.exceptions.UnsupportedScriptException;
import io.swisschain.domain.common.Coin;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.primitives.NetworkType;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface CoinsTransactionSigner {
  TransactionSigningResult sign(
      byte[] unsignedTransaction,
      List<Coin> coins,
      String privateKey,
      String publicKey,
      NetworkType networkType)
          throws Exception;
}
