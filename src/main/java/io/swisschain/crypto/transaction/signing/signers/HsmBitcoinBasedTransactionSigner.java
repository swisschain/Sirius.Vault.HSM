package io.swisschain.crypto.transaction.signing.signers;

import io.swisschain.config.HsmConfig;
import io.swisschain.contracts.TransferDetails;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.hsm.HsmConnector;
import io.swisschain.crypto.transaction.signing.CoinsTransactionSigner;
import io.swisschain.crypto.transaction.signing.TransactionSigningResult;
import io.swisschain.crypto.transaction.signing.exceptions.InvalidInputsException;
import io.swisschain.crypto.transaction.signing.exceptions.TransferDetailsValidationException;
import io.swisschain.crypto.transaction.signing.exceptions.UnsupportedScriptException;
import io.swisschain.crypto.transaction.signing.validators.BitcoinTransactionValidator;
import io.swisschain.crypto.utils.BTCUtils;
import io.swisschain.services.Coin;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.LazyECPoint;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptPattern;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
      NetworkParameters network,
      TransferDetails transferDetails,
      String blockchain,
      String networkType,
      String asset)
      throws InvalidInputsException, IOException, UnsupportedScriptException,
          BlockchainNotSupportedException, TransferDetailsValidationException {
    final var transaction = new Transaction(network, unsignedTransaction);

    BitcoinTransactionValidator.validate(
        transaction, transferDetails, blockchain, networkType, asset);

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
              org.bitcoinj.core.Coin.valueOf(
                  (coin.getValue()
                      .multiply(BigDecimal.valueOf(org.bitcoinj.core.Coin.COIN.value))
                      .longValue())));
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
