package io.swisschain.tasks;

import io.swisschain.sirius.vaultApi.VaultApiClient;
import io.swisschain.sirius.vaultApi.generated.vaultMonitoring.VaultMonitoringOuterClass;
import io.swisschain.common.AppVersion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;

public class MonitoringTask implements Runnable {
  private static final Logger logger = LogManager.getLogger();

  private final VaultApiClient vaultApiClient;

  public MonitoringTask(VaultApiClient vaultApiClient) {
    this.vaultApiClient = vaultApiClient;
  }

  @Override
  public void run() {
    try {
      var request =
          VaultMonitoringOuterClass.UpdateVaultStatusRequest.newBuilder()
              .setRequestId(
                  String.format("VaultMonitoring:Status:%d", Instant.now().getEpochSecond()))
              .setStatus(VaultMonitoringOuterClass.UpdateVaultStatusRequest.VaultStatus.RUNNING)
              .setVersion(AppVersion.VERSION)
              .build();

      var response = vaultApiClient.getVaultMonitoring().update(request);

      if (response.getBodyCase()
          == VaultMonitoringOuterClass.UpdateVaultStatusResponse.BodyCase.ERROR) {
        logger.warn(
            String.format("Can not update status. %s", response.getError().getErrorMessage()));
      } else {
        logger.info("Vault status updated.");
      }
    } catch (Exception exception) {
      logger.error("An error occurred while sending monitoring information.", exception);
    }
  }
}