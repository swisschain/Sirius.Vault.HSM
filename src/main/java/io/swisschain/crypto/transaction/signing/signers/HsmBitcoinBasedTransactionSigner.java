package io.swisschain.crypto.transaction.signing.signers;

import io.swisschain.config.HsmConfig;
import io.swisschain.crypto.hsm.HsmConnector;
import io.swisschain.crypto.transaction.signing.CoinsTransactionSigner;
import io.swisschain.crypto.transaction.signing.TransactionSigningResult;
import io.swisschain.crypto.transaction.signing.exceptions.InvalidInputsException;
import io.swisschain.crypto.transaction.signing.exceptions.UnsupportedScriptException;
import io.swisschain.crypto.utils.BTCUtils;
import io.swisschain.services.Coin;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.LazyECPoint;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptPattern;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.List;

public abstract class HsmBitcoinBasedTransactionSigner extends HsmConnector
    implements CoinsTransactionSigner {
  public HsmBitcoinBasedTransactionSigner(HsmConfig hsmConfig) {
    super(hsmConfig);
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

    final var pubKey =
        ECKey.fromPublicOnly(
            ECKey.compressPoint(
                ECKey.CURVE.validatePublicPoint(
                    new LazyECPoint(ECKey.CURVE.getCurve(), Hex.decode(publicKey)).get())));

    final var segwitAddress = SegwitAddress.fromHash(network, pubKey.getPubKeyHash());

    signInputs(transaction, segwitAddress, Hex.decode(privateKey), pubKey);

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

    transaction.clearInputs();
    for (Coin coin : coins) {
      final var outPoint =
          new TransactionOutPoint(
              network, coin.getId().getNumber(), Sha256Hash.wrap(coin.getId().getTransactionId()));

      final var transactionInput =
          new TransactionInput(
              network,
              null,
              new byte[0],
              outPoint,
              org.bitcoinj.core.Coin.valueOf(
                  coin.getValue().longValue() * org.bitcoinj.core.Coin.COIN.value));
      transaction.addInput(transactionInput);
    }
  }

  private void signInputs(
      Transaction transaction, SegwitAddress segwitAddress, byte[] privateKey, ECKey pubKey)
      throws IOException, UnsupportedScriptException {
    final var stubHolder = initStub();
    try {
      for (int i = 0; i < transaction.getInputs().size(); i++) {
        final var input = transaction.getInput(i);
        final var scriptPubKey = ScriptBuilder.createOutputScript(segwitAddress);
        final var scriptCode =
            new ScriptBuilder()
                .data(
                    ScriptBuilder.createOutputScript(
                            LegacyAddress.fromKey(transaction.getParams(), pubKey))
                        .getProgram())
                .build();
        final var value = input.getValue();
        final var hash =
            transaction.hashForWitnessSignature(
                i, scriptCode, value, Transaction.SigHash.ALL, false);

        final var signedInput = signECDSA(stubHolder.stub, hash.getBytes(), privateKey);

        final var point = BTCUtils.signatureToPoint(signedInput);
        final var ecdsaSignature =
            new ECKey.ECDSASignature(
                    point.getXCoord().toBigInteger(), point.getYCoord().toBigInteger())
                .toCanonicalised();

        final var txSignature =
            new TransactionSignature(ecdsaSignature, Transaction.SigHash.ALL, false);

        if (ScriptPattern.isP2WPKH(scriptPubKey)) {
          input.setScriptSig(ScriptBuilder.createEmpty());
          input.setWitness(TransactionWitness.redeemP2WPKH(txSignature, pubKey));
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
