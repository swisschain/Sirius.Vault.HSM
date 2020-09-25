package io.swisschain.crypto.transaction.signing;

import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.transaction.signing.exceptions.InvalidInputsException;
import io.swisschain.crypto.transaction.signing.exceptions.TransactionSignException;
import io.swisschain.crypto.transaction.signing.exceptions.UnsupportedScriptException;
import io.swisschain.primitives.NetworkType;
import io.swisschain.services.Coin;

import java.io.IOException;
import java.util.List;

public interface CoinsTransactionSigner {
  TransactionSigningResult sign(
      byte[] unsignedTransaction,
      List<Coin> coins,
      String privateKeys,
      String publicKey,
      NetworkType networkType)
      throws UnknownNetworkTypeException, InvalidInputsException, IOException,
          UnsupportedScriptException, TransactionSignException;
}
