package io.swisschain.sirius.vaultApi;

import io.grpc.ManagedChannel;
import io.swisschain.sirius.vaultApi.generated.transferSigningRequests.TransferSigningRequestsGrpc;
import io.swisschain.sirius.vaultApi.generated.vaultMonitoring.VaultMonitoringGrpc;
import io.swisschain.sirius.vaultApi.generated.wallets.WalletsGrpc;

import java.util.concurrent.TimeUnit;

public class VaultApiClient {
  private final ManagedChannel channel;

  private final TransferSigningRequestsGrpc.TransferSigningRequestsBlockingStub
      transferSigningRequests;
  private final VaultMonitoringGrpc.VaultMonitoringBlockingStub vaultMonitoring;
  private final WalletsGrpc.WalletsBlockingStub wallets;

  public VaultApiClient(ManagedChannel channel) {
    this.channel = channel;

    this.transferSigningRequests = TransferSigningRequestsGrpc.newBlockingStub(channel);
    this.vaultMonitoring = VaultMonitoringGrpc.newBlockingStub(channel);
    this.wallets = WalletsGrpc.newBlockingStub(channel);
  }

  public TransferSigningRequestsGrpc.TransferSigningRequestsBlockingStub getTransactions() {
    return this.transferSigningRequests;
  }

  public WalletsGrpc.WalletsBlockingStub getWallets() {
    return this.wallets;
  }

  public VaultMonitoringGrpc.VaultMonitoringBlockingStub getVaultMonitoring() {
    return this.vaultMonitoring;
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }
}
