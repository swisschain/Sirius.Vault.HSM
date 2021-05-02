package io.swisschain.services;

import com.google.protobuf.ByteString;
import io.swisschain.domain.transactions.TransactionRejectionReason;
import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.domain.transactions.TransactionType;
import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.sirius.vaultApi.generated.transfer_signing_requests.TransferSigningRequestsOuterClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

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

  public List<TransactionSigningRequest> get() {
    var request =
        TransferSigningRequestsOuterClass.GetTransferSigningRequestsRequest.newBuilder().build();
    var response = vaultApiClient.getTransferSigningRequests().get(request);

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

  public void confirm(TransactionSigningRequest signingRequest) {
    var conformationRequest =
        TransferSigningRequestsOuterClass.ConfirmTransferSigningRequestRequest.newBuilder()
            .setRequestId(String.format("Vault:TransferSigningRequest:%d", signingRequest.getId()))
            .setSigningRequestId(signingRequest.getId())
            .setTransactionId(signingRequest.getTransactionId())
            .setSignedTransaction(ByteString.copyFrom(signingRequest.getSignedTransaction()))
            .setHostProcessId(hostProcessId)
            .build();

    var response = vaultApiClient.getTransferSigningRequests().confirm(conformationRequest);

    if (response.getBodyCase()
        == TransferSigningRequestsOuterClass.ConfirmTransferSigningRequestResponse.BodyCase.ERROR) {
      var message =
          String.format(
              "It is not possible to confirm transfer signing request %d. %s %s",
              signingRequest.getId(),
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
      logger.info(String.format("Transfer signing request %d confirmed.", signingRequest.getId()));
    }
  }

  public void reject(TransactionSigningRequest signingRequest) {
    var rejectRequest =
        TransferSigningRequestsOuterClass.RejectTransferSigningRequestRequest.newBuilder()
            .setRequestId(String.format("Vault:TransferSigningRequest:%d", signingRequest.getId()))
            .setSigningRequestId(signingRequest.getId())
            .setRejectionReason(map(signingRequest.getRejectionReason()))
            .setRejectionReasonMessage(signingRequest.getRejectionReasonMessage())
            .setHostProcessId(hostProcessId)
            .build();

    var response = vaultApiClient.getTransferSigningRequests().reject(rejectRequest);

    if (response.getBodyCase()
        == TransferSigningRequestsOuterClass.RejectTransferSigningRequestResponse.BodyCase.ERROR) {
      var message =
          String.format(
              "It is not possible to reject transfer signing request %d. %s %s",
              signingRequest.getId(),
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
              signingRequest.getId(), signingRequest.getRejectionReasonMessage()));
    }
  }

  private TransactionSigningRequest map(
      TransferSigningRequestsOuterClass.TransferSigningRequest request) {
    return new TransactionSigningRequest(
        request.getId(),
        TransactionType.Transfer,
        Mapper.map(request.getBlockchain()),
        Mapper.map(request.getDoubleSpendingProtectionType()),
        request.getBuiltTransaction().toByteArray(),
        Mapper.map(request.getSigningAddress()),
        Mapper.map(request.getCoinsToSpendList()),
        request.getDocument(),
        request.getSignature(),
        request.getTenantId(),
        Mapper.map(request.getCreatedAt()),
        Mapper.map(request.getUpdatedAt()));
  }

  private TransferSigningRequestsOuterClass.RejectTransferSigningRequestRequest.RejectionReason map(
      @NotNull TransactionRejectionReason transactionRejectionReason) {
    switch (transactionRejectionReason) {
      case Other:
        return TransferSigningRequestsOuterClass.RejectTransferSigningRequestRequest.RejectionReason
            .OTHER;
      case UnknownBlockchain:
        return TransferSigningRequestsOuterClass.RejectTransferSigningRequestRequest.RejectionReason
            .UNKNOWN_BLOCKCHAIN;
      case InvalidSignature:
        return TransferSigningRequestsOuterClass.RejectTransferSigningRequestRequest.RejectionReason
            .INVALID_SIGNATURE;
      default:
        throw new IllegalArgumentException(
            String.format("Unknown rejection reason. %s", transactionRejectionReason.name()));
    }
  }
}
