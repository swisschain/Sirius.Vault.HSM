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
import io.swisschain.services.TransactionSigningApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class TransactionSigner {
  private final Logger logger = LogManager.getLogger();

  private final TransactionSigningApiService transactionSigningApiService;
  private final DocumentValidator documentValidator;
  private final TransactionSignerFactory transactionSignerFactory;
  private final TransactionValidatorFactory transactionValidatorFactory;
  private final WalletRepository walletRepository;

  public TransactionSigner(
      TransactionSigningApiService transactionSigningApiService,
      DocumentValidator documentValidator,
      TransactionSignerFactory transactionSignerFactory,
      TransactionValidatorFactory transactionValidatorFactory,
      WalletRepository walletRepository) {
    this.transactionSigningApiService = transactionSigningApiService;
    this.documentValidator = documentValidator;
    this.transactionSignerFactory = transactionSignerFactory;
    this.transactionValidatorFactory = transactionValidatorFactory;
    this.walletRepository = walletRepository;
  }

  public void sign(TransactionSigningRequest transactionSigningRequest)
      throws OperationFailedException, OperationExhaustedException, SQLException {

    var signatureValidationResult =
        documentValidator.Validate(
            transactionSigningRequest.getDocument(), transactionSigningRequest.getSignature());

    if (!signatureValidationResult.isValid()) {
      transactionSigningRequest.reject(
          TransactionRejectionReason.UnwantedTransaction, signatureValidationResult.getReason());
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
              TransactionRejectionReason.UnwantedTransaction, validationResult.getError());
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

    if (transactionSigningRequest.isRejected()) {
      transactionSigningApiService.reject(transactionSigningRequest);
    } else {
      transactionSigningApiService.confirm(transactionSigningRequest);
    }
  }
}
