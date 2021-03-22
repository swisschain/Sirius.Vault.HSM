package io.swisschain.services;

import io.swisschain.domain.wallet.RejectionReason;
import io.swisschain.domain.wallet.WalletGenerationRequest;
import io.swisschain.mappers.NetworkTypeMapper;
import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.sirius.vaultApi.generated.wallets.WalletsOuterClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WalletApiServiceImp implements WalletApiService {
  private final VaultApiClient vaultApiClient;
  private final String hostProcessId;
  private final Logger logger = LogManager.getLogger();

  public WalletApiServiceImp(VaultApiClient vaultApiClient, String hostProcessId) {
    this.vaultApiClient = vaultApiClient;
    this.hostProcessId = hostProcessId;
  }

  public List<WalletGenerationRequest> get() {
    var request = WalletsOuterClass.GetWalletGenerationRequestRequest.newBuilder().build();
    var response = this.vaultApiClient.getWallets().get(request);

    if (response.getBodyCase()
        == WalletsOuterClass.GetWalletGenerationRequestResponse.BodyCase.ERROR) {
      logger.error(
          String.format(
              "An error occurred while getting wallet generation requests. %s %s",
              response.getError().getErrorCode().name(), response.getError().getErrorMessage()));
      return new ArrayList<>();
    }

    return response.getResponse().getRequestsList().stream()
        .map(
            item ->
                new WalletGenerationRequest(
                    item.getId(),
                    item.getBlockchainId(),
                    item.getProtocolCode(),
                    NetworkTypeMapper.map(item.getNetworkType()),
                    item.getTenantId(),
                    item.getGroup()))
        .collect(Collectors.toList());
  }

  public void confirm(WalletGenerationRequest walletGenerationRequest) {
    var request =
        WalletsOuterClass.ConfirmWalletGenerationRequestRequest.newBuilder()
            .setRequestId(String.format("Vault:Wallet:%d", walletGenerationRequest.getId()))
            .setWalletGenerationRequestId(walletGenerationRequest.getId())
            .setPublicKey(walletGenerationRequest.getPublicKey())
            .setAddress(walletGenerationRequest.getAddress())
            .setSignature("empty") // TODO: remove
            .setHostProcessId(hostProcessId)
            .build();

    var response = this.vaultApiClient.getWallets().confirm(request);

    if (response.getBodyCase()
        == WalletsOuterClass.ConfirmWalletGenerationRequestResponse.BodyCase.ERROR) {
      var message =
          String.format(
              "It is not possible to confirm wallet generation request %d. %s %s",
              walletGenerationRequest.getId(),
              response.getError().getErrorCode().name(),
              response.getError().getErrorMessage());
      if (response.getError().getErrorCode()
          == WalletsOuterClass.ConfirmWalletGenerationRequestErrorResponseBody.ErrorCode
              .INVALID_STATE) {
        logger.warn(message);
      } else {
        logger.error(message);
      }
    } else {
      logger.info(
          String.format(
              "Wallet generation request %d confirmed.", walletGenerationRequest.getId()));
    }
  }

  public void reject(WalletGenerationRequest walletGenerationRequest) {
    var request =
        WalletsOuterClass.RejectWalletGenerationRequestRequest.newBuilder()
            .setRequestId(String.format("Vault:Wallet:%d", walletGenerationRequest.getId()))
            .setWalletGenerationRequestId(walletGenerationRequest.getId())
            .setReasonMessage(walletGenerationRequest.getRejectionReasonMessage())
            .setReason(map(walletGenerationRequest.getRejectionReason()))
            .setHostProcessId(hostProcessId)
            .build();

    var response = this.vaultApiClient.getWallets().reject(request);

    if (response.getBodyCase()
        == WalletsOuterClass.RejectWalletGenerationRequestResponse.BodyCase.ERROR) {
      var message =
          String.format(
              "It is not possible to reject wallet generation request %d. %s %s",
              walletGenerationRequest.getId(),
              response.getError().getErrorCode().name(),
              response.getError().getErrorMessage());
      if (response.getError().getErrorCode()
          == WalletsOuterClass.RejectWalletGenerationRequestErrorResponseBody.ErrorCode
              .INVALID_STATE) {
        logger.warn(message);
      } else {
        logger.error(message);
      }
    } else {
      logger.info(
          String.format(
              "Wallet generation request %d rejected with reason %s.",
              walletGenerationRequest.getId(),
              walletGenerationRequest.getRejectionReasonMessage()));
    }
  }

  private WalletsOuterClass.RejectionReason map(@NotNull RejectionReason rejectionReason) {
    switch (rejectionReason) {
      case Other:
        return WalletsOuterClass.RejectionReason.OTHER;
      case UnknownBlockchain:
        return WalletsOuterClass.RejectionReason.UNKNOWN_BLOCKCHAIN;
      default:
        throw new IllegalArgumentException(
            String.format("Unknown rejection reason. %s", rejectionReason.name()));
    }
  }
}
