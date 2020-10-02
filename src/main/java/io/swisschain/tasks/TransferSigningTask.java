package io.swisschain.tasks;

import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import io.grpc.StatusRuntimeException;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.mappers.DoubleSpendingProtectionTypeMapper;
import io.swisschain.mappers.NetworkTypeMapper;
import io.swisschain.services.*;
import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.sirius.vaultApi.generated.transferSigningRequests.TransferSigningRequestsOuterClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TransferSigningTask implements Runnable {
  private static final Logger logger = LogManager.getLogger();

  private final VaultApiClient vaultApiClient;
  private final TransactionService transactionService;
  private final String hostProcessId;

  public TransferSigningTask(
      VaultApiClient vaultApiClient, TransactionService transactionService, String hostProcessId) {
    this.vaultApiClient = vaultApiClient;
    this.transactionService = transactionService;
    this.hostProcessId = hostProcessId;

    logger.info("TransferSigningTask created");
  }

  @Override
  public void run() {
    try {
      var requests = getRequests();

      if (requests == null || requests.size() == 0) return;

      for (var transferSigningRequest : requests) {
        try {
          logger.info(
              String.format(
                  "Processing transfer signing request. Id: %d",
                  transferSigningRequest.getId()));

          var transaction = transactionService.create(transferSigningRequest);

          confirm(
              transferSigningRequest.getId(),
              transaction.getTransactionId(),
              transaction.getSignedTransaction());
        } catch (BlockchainNotSupportedException exception) {
          logger.error("BlockchainId is not supported.", exception);

          reject(
              transferSigningRequest.getId(),
              TransferSigningRequestsOuterClass.TransferSigningRequestRejectionReason
                  .UNKNOWN_BLOCKCHAIN,
              "BlockchainId is not supported.");

        } catch (Exception exception) {
          logger.error("An error occurred while processing transfer signing request.", exception);

          reject(
              transferSigningRequest.getId(),
              TransferSigningRequestsOuterClass.TransferSigningRequestRejectionReason.OTHER,
              exception.getMessage());
        }
      }
    } catch (Exception exception) {
      logger.error("An error occurred while processing transfer signing requests.", exception);
    }
  }

  @Nullable
  private List<TransferSigningRequest> getRequests() {
    try {
      var request =
          TransferSigningRequestsOuterClass.GetTransferSigningRequestsRequest.newBuilder().build();
      var response = vaultApiClient.getTransactions().get(request);

      if (response.getBodyCase()
          == TransferSigningRequestsOuterClass.GetTransferSigningRequestsResponse.BodyCase.ERROR) {
        logger.error(
            String.format(
                "An error occurred while getting transfer signing requests. %s",
                response.getError().getErrorMessage()));
        return null;
      }

      var transferSigningRequests = new ArrayList<TransferSigningRequest>();

      for (var item : response.getResponse().getRequestsList()) {
        try {
          var transferSigningRequest = map(item);
          transferSigningRequests.add(transferSigningRequest);
        } catch (Exception exception) {
          logger.error(
              String.format(
                  "An error occurred while parsing transfer signing request. Id: %d",
                  item.getId()),
              exception);
        }
      }

      return transferSigningRequests;
    } catch (StatusRuntimeException statusRuntimeException) {
      logger.warn(
          String.format(
              "Unable to get list of transfer signing requests due to %s network status",
              statusRuntimeException.getStatus().getCode().name()));
      return null;
    }
  }

  private void confirm(
      Long transferSigningRequestId, String transactionId, byte[] signedTransaction) {
    var conformationRequest =
        TransferSigningRequestsOuterClass.ConfirmTransferSigningRequestRequest.newBuilder()
            .setRequestId(
                String.format("Vault:TransferSigningRequest:%d", transferSigningRequestId))
            .setTransferSigningRequestId(transferSigningRequestId)
            .setTransactionId(transactionId)
            .setSignedTransaction(ByteString.copyFrom(signedTransaction))
            .setVaultSignature("empty") // TODO: sign signedTransaction+transactionId
            .setHostProcessId(hostProcessId)
            .build();

    var response = vaultApiClient.getTransactions().confirm(conformationRequest);

    if (response.getBodyCase()
        == TransferSigningRequestsOuterClass.ConfirmTransferSigningRequestResponse.BodyCase.ERROR) {
      logger.error(
          String.format(
              "An error occurred while confirming transfer signing request. %s",
              response.getError().getErrorMessage()));
    } else {
      logger.info(
          String.format("Transfer signing request confirmed. %d", transferSigningRequestId));
    }
  }

  private void reject(
      Long transferSigningRequestId,
      TransferSigningRequestsOuterClass.TransferSigningRequestRejectionReason reason,
      String message) {
    var rejectRequest =
        TransferSigningRequestsOuterClass.RejectTransferSigningRequestRequest.newBuilder()
            .setRequestId(
                String.format("Vault:TransferSigningRequest:%d", transferSigningRequestId))
            .setTransferSigningRequestId(transferSigningRequestId)
            .setReason(reason)
            .setReasonMessage(message)
            .setVaultSignature("empty") // TODO: sing reason+message
            .setHostProcessId(hostProcessId)
            .build();

    var response = vaultApiClient.getTransactions().reject(rejectRequest);

    if (response.getBodyCase()
        == TransferSigningRequestsOuterClass.RejectTransferSigningRequestResponse.BodyCase.ERROR) {
      logger.error(
          String.format(
              "An error occurred while rejecting transfer signing request. %s",
              response.getError().getErrorMessage()));
    } else {
      logger.info(String.format("Transfer signing request rejected. %d", transferSigningRequestId));
    }
  }

  private TransferSigningRequest map(
      TransferSigningRequestsOuterClass.TransferSigningRequest request) {
    return new TransferSigningRequest(
        request.getId(),
        request.getBlockchainId(),
        request.getProtocolCode(),
        NetworkTypeMapper.map(request.getNetworkType()),
        DoubleSpendingProtectionTypeMapper.map(request.getDoubleSpendingProtectionType()),
        request.getBuiltTransaction().toByteArray(),
        request.getSigningAddressesList(),
        map(request.getCoinsToSpendList()),
        request.getPolicyResult(),
        request.getGuardianSignature(),
        request.getGroup(),
        request.getTenantId(),
        map(request.getCreatedAt()),
        map(request.getUpdatedAt()));
  }

  private List<Coin> map(List<TransferSigningRequestsOuterClass.CoinToSpend> coinToSpends) {
    var coins = new ArrayList<Coin>();

    for (var coinToSpend : coinToSpends) {
      var coinId =
          new CoinId(coinToSpend.getId().getTransactionId(), coinToSpend.getId().getNumber());

      var address =
          coinToSpend.getAsset().getId().getAddress() != null
              ? coinToSpend.getAsset().getId().getAddress().getValue()
              : null;

      var blockchainAssetId =
          new BlockchainAssetId(coinToSpend.getAsset().getId().getSymbol(), address);

      var asset = new BlockchainAsset(blockchainAssetId, coinToSpend.getAsset().getAccuracy());

      var redeem = coinToSpend.getRedeem() != null ? coinToSpend.getRedeem().getValue() : null;

      var coin =
          new Coin(
              coinId,
              asset,
              new BigDecimal(coinToSpend.getValue().getValue()),
              coinToSpend.getAddress(),
              redeem);

      coins.add(coin);
    }

    return coins;
  }

  private Instant map(Timestamp timestamp) {
    return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
  }
}
