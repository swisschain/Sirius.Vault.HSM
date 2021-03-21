package io.swisschain.tasks;

import io.swisschain.contracts.Document;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.exceptions.UnknownNetworkTypeException;
import io.swisschain.crypto.transaction.signing.TransactionSignerFactory;
import io.swisschain.domain.transfers.RejectionReason;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.repositories.wallets.WalletRepository;
import io.swisschain.services.DocumentValidator;
import io.swisschain.services.JsonSerializer;
import io.swisschain.services.TransferApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransferSigningTask implements Runnable {
  private static final Logger logger = LogManager.getLogger();

  private final TransferApiService transferApiService;
  private final DocumentValidator documentValidator;
  private final TransactionSignerFactory transactionSignerFactory;
  private final WalletRepository walletRepository;

  private final JsonSerializer jsonMapper = new JsonSerializer();

  public TransferSigningTask(
      TransferApiService transferApiService,
      DocumentValidator documentValidator,
      TransactionSignerFactory transactionSignerFactory,
      WalletRepository walletRepository) {
    this.transferApiService = transferApiService;
    this.documentValidator = documentValidator;
    this.transactionSignerFactory = transactionSignerFactory;
    this.walletRepository = walletRepository;
  }

  @Override
  public void run() {
    try {
      for (var transferSigningRequest : transferApiService.get()) {
        if (transferSigningRequest.getSigningAddresses().size() != 1) {
          transferSigningRequest.reject(
              RejectionReason.Other, "Currently only one signing address supported");
          transferApiService.reject(transferSigningRequest);
          continue;
        }

        var signatureValidationResult =
            documentValidator.Validate(
                transferSigningRequest.getDocument(), transferSigningRequest.getSignature());

        if (!signatureValidationResult.isValid()) {
          transferSigningRequest.reject(
              RejectionReason.InvalidSignature, signatureValidationResult.getReason());
          transferApiService.reject(transferSigningRequest);
          continue;
        }

        var signingAddress = transferSigningRequest.getSigningAddresses().get(0);

        var wallet =
            walletRepository.find(
                signingAddress.getAddress(),
                signingAddress.getGroup(),
                transferSigningRequest.getTenantId());

        if (wallet == null) {
          transferSigningRequest.reject(RejectionReason.Other, "Wallet not found");
          transferApiService.reject(transferSigningRequest);
          continue;
        }

        try {
          var signer =
              transactionSignerFactory.getCoinsTransactionSigner(
                  BlockchainProtocolCodes.fromString(transferSigningRequest.getProtocolCode()));

          var document =
              jsonMapper.deserialize(transferSigningRequest.getDocument(), Document.class);

          var result =
              signer.sign(
                  transferSigningRequest.getBuiltTransaction(),
                  transferSigningRequest.getCoinsToSpend(),
                  wallet.getPrivateKey(),
                  wallet.getPublicKey(),
                  transferSigningRequest.getNetworkType(),
                  document.getTransferDetails());

          transferSigningRequest.confirm(result.getTransactionId(), result.getSignedTransaction());
        } catch (BlockchainNotSupportedException exception) {
          transferSigningRequest.reject(
              RejectionReason.UnknownBlockchain, "Blockchain is not supported");
          logger.error(
              String.format(
                  "It's not possible to sign transaction request %d. Blockchain %s not supported.",
                  transferSigningRequest.getId(), transferSigningRequest.getBlockchainId()));
        } catch (UnknownNetworkTypeException exception) {
          transferSigningRequest.reject(RejectionReason.Other, "Network type is not supported");
          logger.error(
              String.format(
                  "It's not possible to sign transaction request %d. Network type %s not supported.",
                  transferSigningRequest.getId(), transferSigningRequest.getBlockchainId()));
        } catch (Exception exception) {
          transferSigningRequest.reject(RejectionReason.Other, "Unexpected error");
          logger.error(
              String.format(
                  "It's not possible to sign transaction request %d. An unexpected error occurred.",
                  transferSigningRequest.getId()),
              exception);
        }

        if (transferSigningRequest.isRejected()) {
          transferApiService.reject(transferSigningRequest);
        } else {
          transferApiService.confirm(transferSigningRequest);
        }
      }
    } catch (OperationExhaustedException exception) {
      logger.error("Operation exhausted while processing transfer signing requests.", exception);
    } catch (OperationFailedException exception) {
      logger.error("Operation failed while processing transfer signing requests.", exception);
    } catch (Exception exception) {
      logger.error(
          "An unexpected error occurred while processing transfer signing requests.", exception);
    }
  }
}
