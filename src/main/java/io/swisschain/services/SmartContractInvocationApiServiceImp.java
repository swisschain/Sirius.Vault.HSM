package io.swisschain.services;

import com.google.protobuf.ByteString;
import io.swisschain.domain.transactions.TransactionRejectionReason;
import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.domain.transactions.TransactionType;
import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.sirius.vaultApi.generated.smart_contract_invocation_signing_requests.SmartContractInvocationSigningRequestsOuterClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SmartContractInvocationApiServiceImp implements TransactionSigningApiService {
  private final VaultApiClient vaultApiClient;
  private final String hostProcessId;
  private final Logger logger = LogManager.getLogger();

  public SmartContractInvocationApiServiceImp(VaultApiClient vaultApiClient, String hostProcessId) {
    this.vaultApiClient = vaultApiClient;
    this.hostProcessId = hostProcessId;
  }

  public List<TransactionSigningRequest> get() {
    var request =
        SmartContractInvocationSigningRequestsOuterClass
            .GetSmartContractInvocationSigningRequestsRequest.newBuilder()
            .build();
    var response = vaultApiClient.getSmartContractInvocationSigningRequests().get(request);

    if (response.getBodyCase()
        == SmartContractInvocationSigningRequestsOuterClass
            .GetSmartContractInvocationSigningRequestsResponse.BodyCase.ERROR) {
      logger.error(
          String.format(
              "An error occurred while getting smart contract invocation signing requests. %s %s",
              response.getError().getErrorCode().name(), response.getError().getErrorMessage()));
      return new ArrayList<>();
    }

    return response.getResponse().getRequestsList().stream()
        .map(this::map)
        .collect(Collectors.toList());
  }

  public void confirm(TransactionSigningRequest signingRequest) {
    var conformationRequest =
        SmartContractInvocationSigningRequestsOuterClass
            .ConfirmSmartContractInvocationSigningRequestRequest.newBuilder()
            .setRequestId(
                String.format(
                    "Vault:SmartContractInvocationSigningRequest:%d", signingRequest.getId()))
            .setSigningRequestId(signingRequest.getId())
            .setTransactionId(signingRequest.getTransactionId())
            .setSignedTransaction(ByteString.copyFrom(signingRequest.getSignedTransaction()))
            .setHostProcessId(hostProcessId)
            .build();

    var response =
        vaultApiClient.getSmartContractInvocationSigningRequests().confirm(conformationRequest);

    if (response.getBodyCase()
        == SmartContractInvocationSigningRequestsOuterClass
            .ConfirmSmartContractInvocationSigningRequestResponse.BodyCase.ERROR) {
      var message =
          String.format(
              "It is not possible to confirm smart contract invocation signing request %d. %s %s",
              signingRequest.getId(),
              response.getError().getErrorCode().name(),
              response.getError().getErrorMessage());
      if (response.getError().getErrorCode()
          == SmartContractInvocationSigningRequestsOuterClass
              .ConfirmSmartContractInvocationSigningRequestErrorResponseBody.ErrorCode
              .INVALID_STATE) {
        logger.warn(message);
      } else {
        logger.error(message);
      }
    } else {
      logger.info(
          String.format(
              "Smart contract invocation signing request %d confirmed.", signingRequest.getId()));
    }
  }

  public void reject(TransactionSigningRequest signingRequest) {
    var rejectRequest =
        SmartContractInvocationSigningRequestsOuterClass
            .RejectSmartContractInvocationSigningRequestRequest.newBuilder()
            .setRequestId(
                String.format(
                    "Vault:SmartContractInvocationSigningRequest:%d", signingRequest.getId()))
            .setSigningRequestId(signingRequest.getId())
            .setRejectionReason(map(signingRequest.getRejectionReason()))
            .setRejectionReasonMessage(signingRequest.getRejectionReasonMessage())
            .setHostProcessId(hostProcessId)
            .build();

    var response = vaultApiClient.getSmartContractInvocationSigningRequests().reject(rejectRequest);

    if (response.getBodyCase()
        == SmartContractInvocationSigningRequestsOuterClass
            .RejectSmartContractInvocationSigningRequestResponse.BodyCase.ERROR) {
      var message =
          String.format(
              "It is not possible to reject smart contract invocation signing request %d. %s %s",
              signingRequest.getId(),
              response.getError().getErrorCode().name(),
              response.getError().getErrorMessage());
      if (response.getError().getErrorCode()
          == SmartContractInvocationSigningRequestsOuterClass
              .RejectSmartContractInvocationSigningRequestErrorResponseBody.ErrorCode
              .INVALID_STATE) {
        logger.warn(message);
      } else {
        logger.error(message);
      }
    } else {
      logger.info(
          String.format(
              "Smart contract invocation signing request %d rejected with reason %s.",
              signingRequest.getId(), signingRequest.getRejectionReasonMessage()));
    }
  }

  private TransactionSigningRequest map(
      SmartContractInvocationSigningRequestsOuterClass.SmartContractInvocationSigningRequest
          request) {
    return new TransactionSigningRequest(
        request.getId(),
        TransactionType.SmartContractInvocation,
        Mapper.map(request.getBlockchain()),
        Mapper.map(request.getDoubleSpendingProtectionType()),
        request.getBuiltTransaction().toByteArray(),
        Mapper.map(request.getSigningAddress()),
        new ArrayList<>(),
        request.getDocument(),
        request.getSignature(),
        request.getTenantId(),
        Mapper.map(request.getCreatedAt()),
        Mapper.map(request.getUpdatedAt()));
  }

  private SmartContractInvocationSigningRequestsOuterClass
          .RejectSmartContractInvocationSigningRequestRequest.RejectionReason
      map(@NotNull TransactionRejectionReason transactionRejectionReason) {
    switch (transactionRejectionReason) {
      case Other:
        return SmartContractInvocationSigningRequestsOuterClass
            .RejectSmartContractInvocationSigningRequestRequest.RejectionReason.OTHER;
      case UnknownBlockchain:
        return SmartContractInvocationSigningRequestsOuterClass
            .RejectSmartContractInvocationSigningRequestRequest.RejectionReason.UNKNOWN_BLOCKCHAIN;
      case UnwantedTransaction:
        return SmartContractInvocationSigningRequestsOuterClass
            .RejectSmartContractInvocationSigningRequestRequest.RejectionReason
            .UNWANTED_TRANSACTION;
      default:
        throw new IllegalArgumentException(
            String.format("Unknown rejection reason. %s", transactionRejectionReason.name()));
    }
  }
}
