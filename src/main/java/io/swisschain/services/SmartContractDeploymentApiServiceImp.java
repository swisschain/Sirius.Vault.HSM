package io.swisschain.services;

import com.google.protobuf.ByteString;
import io.swisschain.domain.transactions.TransactionRejectionReason;
import io.swisschain.domain.transactions.TransactionSigningRequest;
import io.swisschain.domain.transactions.TransactionType;
import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.sirius.vaultApi.generated.smart_contract_deployment_signing_requests.SmartContractDeploymentSigningRequestsOuterClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SmartContractDeploymentApiServiceImp implements TransactionSigningApiService {
  private final VaultApiClient vaultApiClient;
  private final String hostProcessId;
  private final Logger logger = LogManager.getLogger();

  public SmartContractDeploymentApiServiceImp(VaultApiClient vaultApiClient, String hostProcessId) {
    this.vaultApiClient = vaultApiClient;
    this.hostProcessId = hostProcessId;
  }

  public List<TransactionSigningRequest> get() {
    var request =
        SmartContractDeploymentSigningRequestsOuterClass
            .GetSmartContractDeploymentSigningRequestsRequest.newBuilder()
            .build();
    var response = vaultApiClient.getSmartContractDeploymentSigningRequests().get(request);

    if (response.getBodyCase()
        == SmartContractDeploymentSigningRequestsOuterClass
            .GetSmartContractDeploymentSigningRequestsResponse.BodyCase.ERROR) {
      logger.error(
          String.format(
              "An error occurred while getting smart contract deployment signing requests. %s %s",
              response.getError().getErrorCode().name(), response.getError().getErrorMessage()));
      return new ArrayList<>();
    }

    return response.getResponse().getRequestsList().stream()
        .map(this::map)
        .collect(Collectors.toList());
  }

  public void confirm(TransactionSigningRequest signingRequest) {
    var conformationRequest =
        SmartContractDeploymentSigningRequestsOuterClass
            .ConfirmSmartContractDeploymentSigningRequestRequest.newBuilder()
            .setRequestId(
                String.format(
                    "Vault:SmartContractDeploymentSigningRequest:%d", signingRequest.getId()))
            .setSigningRequestId(signingRequest.getId())
            .setTransactionId(signingRequest.getTransactionId())
            .setSignedTransaction(ByteString.copyFrom(signingRequest.getSignedTransaction()))
            .setHostProcessId(hostProcessId)
            .build();

    var response =
        vaultApiClient.getSmartContractDeploymentSigningRequests().confirm(conformationRequest);

    if (response.getBodyCase()
        == SmartContractDeploymentSigningRequestsOuterClass
            .ConfirmSmartContractDeploymentSigningRequestResponse.BodyCase.ERROR) {
      var message =
          String.format(
              "It is not possible to confirm smart contract deployment signing request %d. %s %s",
              signingRequest.getId(),
              response.getError().getErrorCode().name(),
              response.getError().getErrorMessage());
      if (response.getError().getErrorCode()
          == SmartContractDeploymentSigningRequestsOuterClass
              .ConfirmSmartContractDeploymentSigningRequestErrorResponseBody.ErrorCode
              .INVALID_STATE) {
        logger.warn(message);
      } else {
        logger.error(message);
      }
    } else {
      logger.info(
          String.format(
              "Smart contract deployment signing request %d confirmed.", signingRequest.getId()));
    }
  }

  public void reject(TransactionSigningRequest signingRequest) {
    var rejectRequest =
        SmartContractDeploymentSigningRequestsOuterClass
            .RejectSmartContractDeploymentSigningRequestRequest.newBuilder()
            .setRequestId(
                String.format(
                    "Vault:SmartContractDeploymentSigningRequest:%d", signingRequest.getId()))
            .setSigningRequestId(signingRequest.getId())
            .setRejectionReason(map(signingRequest.getRejectionReason()))
            .setRejectionReasonMessage(signingRequest.getRejectionReasonMessage())
            .setHostProcessId(hostProcessId)
            .build();

    var response = vaultApiClient.getSmartContractDeploymentSigningRequests().reject(rejectRequest);

    if (response.getBodyCase()
        == SmartContractDeploymentSigningRequestsOuterClass
            .RejectSmartContractDeploymentSigningRequestResponse.BodyCase.ERROR) {
      var message =
          String.format(
              "It is not possible to reject smart contract deployment signing request %d. %s %s",
              signingRequest.getId(),
              response.getError().getErrorCode().name(),
              response.getError().getErrorMessage());
      if (response.getError().getErrorCode()
          == SmartContractDeploymentSigningRequestsOuterClass
              .RejectSmartContractDeploymentSigningRequestErrorResponseBody.ErrorCode
              .INVALID_STATE) {
        logger.warn(message);
      } else {
        logger.error(message);
      }
    } else {
      logger.info(
          String.format(
              "Smart contract deployment signing request %d rejected with reason %s.",
              signingRequest.getId(), signingRequest.getRejectionReasonMessage()));
    }
  }

  private TransactionSigningRequest map(
      SmartContractDeploymentSigningRequestsOuterClass.SmartContractDeploymentSigningRequest
          request) {
    return new TransactionSigningRequest(
        request.getId(),
        TransactionType.SmartContractDeployment,
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

  private SmartContractDeploymentSigningRequestsOuterClass
          .RejectSmartContractDeploymentSigningRequestRequest.RejectionReason
      map(@NotNull TransactionRejectionReason transactionRejectionReason) {
    switch (transactionRejectionReason) {
      case Other:
        return SmartContractDeploymentSigningRequestsOuterClass
            .RejectSmartContractDeploymentSigningRequestRequest.RejectionReason.OTHER;
      case UnknownBlockchain:
        return SmartContractDeploymentSigningRequestsOuterClass
            .RejectSmartContractDeploymentSigningRequestRequest.RejectionReason.UNKNOWN_BLOCKCHAIN;
      case UnwantedTransaction:
        return SmartContractDeploymentSigningRequestsOuterClass
            .RejectSmartContractDeploymentSigningRequestRequest.RejectionReason
            .UNWANTED_TRANSACTION;
      default:
        throw new IllegalArgumentException(
            String.format("Unknown rejection reason. %s", transactionRejectionReason.name()));
    }
  }
}
