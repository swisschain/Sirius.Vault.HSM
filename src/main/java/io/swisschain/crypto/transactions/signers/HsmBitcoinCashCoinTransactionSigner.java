package io.swisschain.crypto.transactions.signers;

import io.swisschain.config.clients.IbmApiConfig;
import io.swisschain.crypto.NetworkMapper;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.hsm.HsmConnector;
import io.swisschain.crypto.transactions.CoinsTransactionSigner;
import io.swisschain.crypto.transactions.TransactionSigningResult;
import io.swisschain.crypto.transactions.exceptions.InvalidInputsException;
import io.swisschain.crypto.transactions.exceptions.UnsupportedScriptException;
import io.swisschain.crypto.utils.BTCUtils;
import io.swisschain.domain.common.Coin;
import io.swisschain.domain.primitives.NetworkType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinjcash.core.*;
import org.bitcoinjcash.crypto.TransactionSignature;
import org.bitcoinjcash.script.ScriptBuilder;
import org.bitcoinjcash.script.ScriptPattern;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HsmBitcoinCashCoinTransactionSigner extends HsmConnector
    implements CoinsTransactionSigner {
  private static final Logger logger = LogManager.getLogger();

  public HsmBitcoinCashCoinTransactionSigner(IbmApiConfig ibmApiConfig) {
    super(ibmApiConfig);
  }

  @Override
  public TransactionSigningResult sign(
      byte[] unsignedTransaction,
      List<Coin> coins,
      String privateKey,
      String publicKey,
      NetworkType networkType)
      throws UnknownNetworkTypeException, InvalidInputsException, IOException,
          UnsupportedScriptException {
    var result =
        sign(
            unsignedTransaction,
            coins,
            privateKey,
            publicKey,
            NetworkMapper.mapToBitcoinCashNetworkType(networkType));
    logger.debug("TxId: {}", result.getTransactionId());
    logger.debug("Signed: {}", Hex.toHexString(result.getSignedTransaction()));
    return result;
  }

  public TransactionSigningResult sign(
      byte[] unsignedTransaction,
      List<Coin> coins,
      String privateKey,
      String publicKey,
      NetworkParameters network)
      throws InvalidInputsException, IOException, UnsupportedScriptException {
    final var transaction = new Transaction(network, unsignedTransaction);

    processCoins(transaction, coins, network);

    final var pubKey = ECKey.fromPublicOnly(Hex.decode(publicKey));

    final var cashAddress = CashAddress.fromKey(network, pubKey).toCash();

    signInputs(transaction, cashAddress, Hex.decode(privateKey), pubKey);

    final var signedTransaction = transaction.bitcoinSerialize();

    return new TransactionSigningResult(transaction.getTxId().toString(), signedTransaction);
  }

  private void processCoins(Transaction transaction, List<Coin> coins, NetworkParameters network)
      throws InvalidInputsException {
    if (transaction.getInputs().size() != coins.size()) {
      throw new InvalidInputsException(
          String.format(
              "Transaction inputs count %d doesn't match coins count %d",
              transaction.getInputs().size(), coins.size()));
    }

    final var oldInputs = new ArrayList<>(transaction.getInputs());
    transaction.clearInputs();

    for (Coin coin : coins) {
      final var oldInput =
          oldInputs.stream()
              .filter(
                  input ->
                      input
                              .getOutpoint()
                              .getHash()
                              .toString()
                              .equals(coin.getId().getTransactionId())
                          && input.getOutpoint().getIndex() == coin.getId().getNumber())
              .findFirst()
              .orElse(null);
      if (oldInput == null) {
        throw new InvalidInputsException(
            String.format(
                "Transaction inputs doesn't contain input with id %s and index %d from coin",
                coin.getId().getTransactionId(), coin.getId().getNumber()));
      }

      final var transactionInput =
          new TransactionInput(
              network,
              oldInput.getParentTransaction(),
              oldInput.getScriptBytes(),
              oldInput.getOutpoint(),
              org.bitcoinjcash.core.Coin.valueOf(
                  (coin.getValue()
                      .multiply(BigDecimal.valueOf(org.bitcoinjcash.core.Coin.COIN.value))
                      .longValue())));
      transaction.addInput(transactionInput);
    }
  }

  private void signInputs(
      Transaction transaction, CashAddress cashAddress, byte[] privateKey, ECKey pubKey)
      throws IOException, UnsupportedScriptException {
    final var stubHolder = initStub();
    try {
      for (int i = 0; i < transaction.getInputs().size(); i++) {
        final var input = transaction.getInput(i);
        final var scriptPubKey = ScriptBuilder.createOutputScript(cashAddress);
        final var value = input.getValue();
        final var hash =
            transaction.hashForSignatureWitness(
                i, scriptPubKey, value, Transaction.SigHash.ALL, false);

        final var signedInput = signECDSA(stubHolder.stub, hash.getBytes(), privateKey);

        final var point = BTCUtils.signatureToPoint(signedInput);
        final var ecdsaSignature =
            new ECKey.ECDSASignature(
                    point.getXCoord().toBigInteger(), point.getYCoord().toBigInteger())
                .toCanonicalised();

        final var txSignature =
            new TransactionSignature(ecdsaSignature, Transaction.SigHash.ALL, false, true);

        if (ScriptPattern.isP2PK(scriptPubKey)) {
          input.setScriptSig(ScriptBuilder.createInputScript(txSignature));
        } else if (ScriptPattern.isP2PKH(scriptPubKey)) {
          input.setScriptSig(ScriptBuilder.createInputScript(txSignature, pubKey));
        } else {
          throw new UnsupportedScriptException(
              String.format("Unsupported script %s, only P2WPKH is supported", scriptPubKey));
        }
      }
    } finally {
      closeStub(stubHolder);
    }
  }
}
