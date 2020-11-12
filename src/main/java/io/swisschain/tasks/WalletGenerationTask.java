package io.swisschain.tasks;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.mappers.NetworkTypeMapper;
import io.swisschain.services.WalletService;
import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.sirius.vaultApi.generated.wallets.WalletsOuterClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WalletGenerationTask implements Runnable {
  private final String hostProcessId;
  private final VaultApiClient vaultApiClient;
  private final WalletService walletService;
  private final Logger logger = LogManager.getLogger();

  public WalletGenerationTask(
      VaultApiClient vaultApiClient, WalletService walletService, String hostProcessId) {
    this.vaultApiClient = vaultApiClient;
    this.walletService = walletService;
    this.hostProcessId = hostProcessId;

    logger.info("WalletGenerationTask created");
  }

  @Override
  public void run() {
    try {
      var requests = this.getRequests();

      if (requests == null) return;

      for (var walletGenerationRequest : requests) {
        try {
          var networkType = NetworkTypeMapper.map(walletGenerationRequest.getNetworkType());

          var wallet =
              this.walletService.create(
                  walletGenerationRequest.getId(),
                  walletGenerationRequest.getBlockchainId(),
                  walletGenerationRequest.getProtocolCode(),
                  networkType,
                  walletGenerationRequest.getTenantId(),
                  walletGenerationRequest.getGroup());

          this.confirm(walletGenerationRequest.getId(), wallet.getPublicKey(), wallet.getAddress());
        } catch (BlockchainNotSupportedException exception) {
          logger.error("BlockchainId is not supported.", exception);

          this.reject(
              walletGenerationRequest.getId(),
              WalletsOuterClass.RejectionReason.UNKNOWN_BLOCKCHAIN,
              "BlockchainId is not supported.");

        } catch (StatusRuntimeException exception) {
          if (!(exception.getStatus() == Status.UNAVAILABLE
              && exception.getMessage().contains("NO_ERROR"))) {
            logger.error(
                "An error occurred while processing wallet generation request. "
                    + exception.getMessage());
          }
        } catch (Exception exception) {
          logger.error("An error occurred while processing wallet generation request.", exception);
          this.reject(
              walletGenerationRequest.getId(),
              WalletsOuterClass.RejectionReason.OTHER,
              exception.getMessage());
        }
      }

    } catch (Exception exception) {
      logger.error("An error occurred while processing wallet generation requests.", exception);
    }
  }

  @Nullable
  private List<WalletsOuterClass.WalletGenerationRequest> getRequests() {
    var request = WalletsOuterClass.GetWalletGenerationRequestRequest.newBuilder().build();
    var response = this.vaultApiClient.getWallets().get(request);

    if (response.getBodyCase()
        == WalletsOuterClass.GetWalletGenerationRequestResponse.BodyCase.ERROR) {
      logger.error(
          String.format(
              "An error occurred while getting wallet generation requests. %s",
              response.getError().getErrorMessage()));
      return null;
    }

    return response.getResponse().getRequestsList();
  }

  private void confirm(Long walletGenerationRequestId, String publicKey, String address) {
    var conformationRequest =
        WalletsOuterClass.ConfirmWalletGenerationRequestRequest.newBuilder()
            .setRequestId(String.format("Vault:Wallet:%d", walletGenerationRequestId))
            .setWalletGenerationRequestId(walletGenerationRequestId)
            .setPublicKey(publicKey)
            .setAddress(address)
            .setSignature("empty") // TODO: Will be implemented next time
            .setHostProcessId(hostProcessId)
            .build();

    var response = this.vaultApiClient.getWallets().confirm(conformationRequest);

    if (response.getBodyCase()
        == WalletsOuterClass.ConfirmWalletGenerationRequestResponse.BodyCase.ERROR) {
      logger.error(
          String.format(
              "An error occurred while confirming wallet generation request. %s",
              response.getError().getErrorMessage()));
    } else {
      logger.info(
          String.format("Wallet generation request confirmed. %d", walletGenerationRequestId));
    }
  }

  private void reject(
      Long walletGenerationRequestId, WalletsOuterClass.RejectionReason reason, String message) {
    var rejectRequest =
        WalletsOuterClass.RejectWalletGenerationRequestRequest.newBuilder()
            .setRequestId(String.format("Vault:Wallet:%d", walletGenerationRequestId))
            .setWalletGenerationRequestId(walletGenerationRequestId)
            .setReasonMessage(message)
            .setReason(reason)
            .setHostProcessId(hostProcessId)
            .build();

    var response = this.vaultApiClient.getWallets().reject(rejectRequest);

    if (response.getBodyCase()
        == WalletsOuterClass.RejectWalletGenerationRequestResponse.BodyCase.ERROR) {
      logger.error(
          String.format(
              "An error occurred while rejecting wallet generation request. %s",
              response.getError().getErrorMessage()));
    } else {
      logger.info(
          String.format("Wallet generation request rejected. %d", walletGenerationRequestId));
    }
  }
}
