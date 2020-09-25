package io.swisschain.sirius.vaultApi;

import io.grpc.ManagedChannel;
import io.swisschain.sirius.vaultApi.generated.transferSigningRequests.TransferSigningRequestsGrpc;
import io.swisschain.sirius.vaultApi.generated.wallets.WalletsGrpc;

import java.util.concurrent.TimeUnit;

public class VaultApiClient {
  private final ManagedChannel channel;

  private final WalletsGrpc.WalletsBlockingStub wallets;
  private final TransferSigningRequestsGrpc.TransferSigningRequestsBlockingStub
      transferSigningRequests;

  public VaultApiClient(ManagedChannel channel) {
    this.channel = channel;

    this.wallets = WalletsGrpc.newBlockingStub(channel);
    this.transferSigningRequests = TransferSigningRequestsGrpc.newBlockingStub(channel);
  }

  public WalletsGrpc.WalletsBlockingStub getWallets() {
    return this.wallets;
  }

  public TransferSigningRequestsGrpc.TransferSigningRequestsBlockingStub getTransactions() {
    return this.transferSigningRequests;
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }
}
