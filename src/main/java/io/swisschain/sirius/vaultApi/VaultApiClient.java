package io.swisschain.sirius.vaultApi;

import io.grpc.ManagedChannel;
import io.swisschain.sirius.vaultApi.generated.smart_contract_deployment_signing_requests.SmartContractDeploymentSigningRequestsGrpc;
import io.swisschain.sirius.vaultApi.generated.smart_contract_invocation_signing_requests.SmartContractInvocationSigningRequestsGrpc;
import io.swisschain.sirius.vaultApi.generated.transfer_signing_requests.TransferSigningRequestsGrpc;
import io.swisschain.sirius.vaultApi.generated.vaultMonitoring.VaultMonitoringGrpc;
import io.swisschain.sirius.vaultApi.generated.wallets.WalletsGrpc;

import java.util.concurrent.TimeUnit;

public class VaultApiClient {
  private final ManagedChannel channel;

  private final TransferSigningRequestsGrpc.TransferSigningRequestsBlockingStub
      transferSigningRequests;
  private final SmartContractDeploymentSigningRequestsGrpc
          .SmartContractDeploymentSigningRequestsBlockingStub
      smartContractDeploymentSigningRequests;
  private final SmartContractInvocationSigningRequestsGrpc
          .SmartContractInvocationSigningRequestsBlockingStub
      smartContractInvocationSigningRequests;
  private final VaultMonitoringGrpc.VaultMonitoringBlockingStub vaultMonitoring;
  private final WalletsGrpc.WalletsBlockingStub wallets;

  public VaultApiClient(ManagedChannel channel) {
    this.channel = channel;

    this.transferSigningRequests = TransferSigningRequestsGrpc.newBlockingStub(channel);
    this.smartContractDeploymentSigningRequests =
        SmartContractDeploymentSigningRequestsGrpc.newBlockingStub(channel);
    this.smartContractInvocationSigningRequests =
        SmartContractInvocationSigningRequestsGrpc.newBlockingStub(channel);
    this.vaultMonitoring = VaultMonitoringGrpc.newBlockingStub(channel);
    this.wallets = WalletsGrpc.newBlockingStub(channel);
  }

  public TransferSigningRequestsGrpc.TransferSigningRequestsBlockingStub
      getTransferSigningRequests() {
    return this.transferSigningRequests;
  }

  public SmartContractDeploymentSigningRequestsGrpc
          .SmartContractDeploymentSigningRequestsBlockingStub
      getSmartContractDeploymentSigningRequests() {
    return this.smartContractDeploymentSigningRequests;
  }

  public SmartContractInvocationSigningRequestsGrpc
          .SmartContractInvocationSigningRequestsBlockingStub
      getSmartContractInvocationSigningRequests() {
    return this.smartContractInvocationSigningRequests;
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
