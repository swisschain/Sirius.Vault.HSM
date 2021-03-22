package io.swisschain.services;

import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import io.swisschain.domain.transfers.*;
import io.swisschain.mappers.DoubleSpendingProtectionTypeMapper;
import io.swisschain.mappers.NetworkTypeMapper;
import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.sirius.vaultApi.generated.transferSigningRequests.TransferSigningRequestsOuterClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransferApiServiceImp implements TransferApiService {
  private final VaultApiClient vaultApiClient;
  private final String hostProcessId;
  private final Logger logger = LogManager.getLogger();

  public TransferApiServiceImp(VaultApiClient vaultApiClient, String hostProcessId) {
    this.vaultApiClient = vaultApiClient;
    this.hostProcessId = hostProcessId;
  }

  public List<TransferSigningRequest> get() {
    var request =
        TransferSigningRequestsOuterClass.GetTransferSigningRequestsRequest.newBuilder().build();
    var response = vaultApiClient.getTransactions().get(request);

    if (response.getBodyCase()
        == TransferSigningRequestsOuterClass.GetTransferSigningRequestsResponse.BodyCase.ERROR) {
      logger.error(
          String.format(
              "An error occurred while getting transfer signing requests. %s %s",
              response.getError().getErrorCode().name(), response.getError().getErrorMessage()));
      return new ArrayList<>();
    }

    return response.getResponse().getRequestsList().stream()
        .map(this::map)
        .collect(Collectors.toList());
  }

  public void confirm(TransferSigningRequest transferSigningRequest) {
    var conformationRequest =
        TransferSigningRequestsOuterClass.ConfirmTransferSigningRequestRequest.newBuilder()
            .setRequestId(
                String.format("Vault:TransferSigningRequest:%d", transferSigningRequest.getId()))
            .setTransferSigningRequestId(transferSigningRequest.getId())
            .setTransactionId(transferSigningRequest.getTransactionId())
            .setSignedTransaction(
                ByteString.copyFrom(transferSigningRequest.getSignedTransaction()))
            .setSignature("empty") // TODO: remove
            .setHostProcessId(hostProcessId)
            .build();

    var response = vaultApiClient.getTransactions().confirm(conformationRequest);

    if (response.getBodyCase()
        == TransferSigningRequestsOuterClass.ConfirmTransferSigningRequestResponse.BodyCase.ERROR) {
      var message =
          String.format(
              "It is not possible to confirm transfer signing request %d. %s %s",
              transferSigningRequest.getId(),
              response.getError().getErrorCode().name(),
              response.getError().getErrorMessage());
      if (response.getError().getErrorCode()
          == TransferSigningRequestsOuterClass.ConfirmTransferSigningRequestErrorResponseBody
              .ErrorCode.INVALID_STATE) {
        logger.warn(message);
      } else {
        logger.error(message);
      }
    } else {
      logger.info(
          String.format("Transfer signing request %d confirmed.", transferSigningRequest.getId()));
    }
  }

  public void reject(TransferSigningRequest transferSigningRequest) {
    var rejectRequest =
        TransferSigningRequestsOuterClass.RejectTransferSigningRequestRequest.newBuilder()
            .setRequestId(
                String.format("Vault:TransferSigningRequest:%d", transferSigningRequest.getId()))
            .setTransferSigningRequestId(transferSigningRequest.getId())
            .setRejectionReason(map(transferSigningRequest.getRejectionReason()))
            .setRejectionReasonMessage(transferSigningRequest.getRejectionReasonMessage())
            .setSignature("empty") // TODO: remove
            .setHostProcessId(hostProcessId)
            .build();

    var response = vaultApiClient.getTransactions().reject(rejectRequest);

    if (response.getBodyCase()
        == TransferSigningRequestsOuterClass.RejectTransferSigningRequestResponse.BodyCase.ERROR) {
      var message =
          String.format(
              "It is not possible to reject transfer signing request %d. %s %s",
              transferSigningRequest.getId(),
              response.getError().getErrorCode().name(),
              response.getError().getErrorMessage());
      if (response.getError().getErrorCode()
          == TransferSigningRequestsOuterClass.RejectTransferSigningRequestErrorResponseBody
              .ErrorCode.INVALID_STATE) {
        logger.warn(message);
      } else {
        logger.error(message);
      }
    } else {
      logger.info(
          String.format(
              "Transfer signing request %d rejected with reason %s.",
              transferSigningRequest.getId(), transferSigningRequest.getRejectionReasonMessage()));
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
        mapSigningAddress(request.getSigningAddressesList()),
        mapCoinToSpend(request.getCoinsToSpendList()),
        request.getDocument(),
        request.getSignature(),
        request.getTenantId(),
        map(request.getCreatedAt()),
        map(request.getUpdatedAt()));
  }

  private List<SigningAddress> mapSigningAddress(
      List<TransferSigningRequestsOuterClass.SigningAddress> signingAddresses) {
    var items = new ArrayList<SigningAddress>();

    for (var signingAddress : signingAddresses) {
      items.add(new SigningAddress(signingAddress.getAddress(), signingAddress.getGroup()));
    }

    return items;
  }

  private List<Coin> mapCoinToSpend(
      List<TransferSigningRequestsOuterClass.CoinToSpend> coinToSpends) {
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

  private TransferSigningRequestsOuterClass.TransferSigningRequestRejectionReason map(
      @NotNull RejectionReason rejectionReason) {
    switch (rejectionReason) {
      case Other:
        return TransferSigningRequestsOuterClass.TransferSigningRequestRejectionReason.OTHER;
      case UnknownBlockchain:
        return TransferSigningRequestsOuterClass.TransferSigningRequestRejectionReason
            .UNKNOWN_BLOCKCHAIN;
      case InvalidSignature:
        return TransferSigningRequestsOuterClass.TransferSigningRequestRejectionReason
            .INVALID_SIGNATURE;
      default:
        throw new IllegalArgumentException(
            String.format("Unknown rejection reason. %s", rejectionReason.name()));
    }
  }

  private Instant map(Timestamp timestamp) {
    return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
  }
}
