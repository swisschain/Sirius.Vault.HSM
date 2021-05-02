package io.swisschain.signers;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.transactions.TransactionSignerFactory;
import io.swisschain.crypto.transactions.TransactionValidatorFactory;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.transactions.TransactionRejectionReason;
import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.repositories.wallets.WalletRepository;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public abstract class TransactionSigner {
  private final Logger logger;

  private final DocumentValidator documentValidator;
  private final TransactionSignerFactory transactionSignerFactory;
  private final TransactionValidatorFactory transactionValidatorFactory;
  private final WalletRepository walletRepository;

  public TransactionSigner(
      DocumentValidator documentValidator,
      TransactionSignerFactory transactionSignerFactory,
      TransactionValidatorFactory transactionValidatorFactory,
      WalletRepository walletRepository,
      Logger logger) {
    this.documentValidator = documentValidator;
    this.transactionSignerFactory = transactionSignerFactory;
    this.transactionValidatorFactory = transactionValidatorFactory;
    this.walletRepository = walletRepository;
    this.logger = logger;
  }

  public void sign(TransactionSigningRequest transactionSigningRequest)
      throws OperationFailedException, OperationExhaustedException, SQLException {

    var signatureValidationResult =
        documentValidator.Validate(
            transactionSigningRequest.getDocument(), transactionSigningRequest.getSignature());

    if (!signatureValidationResult.isValid()) {
      transactionSigningRequest.reject(
          TransactionRejectionReason.InvalidSignature, signatureValidationResult.getReason());
      return;
    }

    var wallet =
        walletRepository.find(
            transactionSigningRequest.getSigningAddress().getAddress(),
            transactionSigningRequest.getSigningAddress().getGroup(),
            transactionSigningRequest.getTenantId());

    if (wallet == null) {
      transactionSigningRequest.reject(TransactionRejectionReason.Other, "Wallet not found");
      return;
    }

    try {
      var blockchainCode =
          BlockchainProtocolCodes.fromString(
              transactionSigningRequest.getBlockchain().getProtocolId());

      var validator =
          transactionValidatorFactory.get(blockchainCode, transactionSigningRequest.getType());

      if (validator != null) {
        var validationResult =
            validator.validate(
                transactionSigningRequest.getBuiltTransaction(),
                transactionSigningRequest.getBlockchain().getNetworkType(),
                wallet.getPublicKey(),
                transactionSigningRequest.getDocument());

        if (!validationResult.isValid()) {
          transactionSigningRequest.reject(
              validationResult.getRejectionReason(), validationResult.getError());
          logger.error(
              String.format(
                  "It's not possible to sign transaction request %d. %s.",
                  transactionSigningRequest.getId(), validationResult.getError()));
        }
      }

      var signer = transactionSignerFactory.get(blockchainCode);

      var signingResult =
          signer.sign(
              transactionSigningRequest.getBuiltTransaction(),
              transactionSigningRequest.getCoinsToSpend(),
              wallet.getPrivateKey(),
              wallet.getPublicKey(),
              transactionSigningRequest.getBlockchain().getNetworkType());

      transactionSigningRequest.confirm(
          signingResult.getTransactionId(), signingResult.getSignedTransaction());
    } catch (BlockchainNotSupportedException exception) {
      transactionSigningRequest.reject(
          TransactionRejectionReason.UnknownBlockchain, "Blockchain is not supported");
      logger.error(
          String.format(
              "It's not possible to sign transaction request %d. Blockchain %s not supported.",
              transactionSigningRequest.getId(),
              transactionSigningRequest.getBlockchain().getId()));
    } catch (UnknownNetworkTypeException exception) {
      transactionSigningRequest.reject(
          TransactionRejectionReason.Other, "Network type is not supported");
      logger.error(
          String.format(
              "It's not possible to sign transaction request %d. Network type %s not supported.",
              transactionSigningRequest.getId(),
              transactionSigningRequest.getBlockchain().getId()));
    } catch (Exception exception) {
      transactionSigningRequest.reject(TransactionRejectionReason.Other, "Unexpected error");
      logger.error(
          String.format(
              "It's not possible to sign transaction request %d. An unexpected error occurred.",
              transactionSigningRequest.getId()),
          exception);
    }
  }
}
