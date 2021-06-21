package io.swisschain.http_handlers.responses.settings;

public class TasksSettings {
  private TaskSettings smartContractDeploymentSigning;
  private TaskSettings smartContractInvocationSigning;
  private TaskSettings transferSigning;
  private TaskSettings walletGeneration;

  public TasksSettings(
      TaskSettings smartContractDeploymentSigning,
      TaskSettings smartContractInvocationSigning,
      TaskSettings transferSigning,
      TaskSettings walletGeneration) {
    this.smartContractDeploymentSigning = smartContractDeploymentSigning;
    this.smartContractInvocationSigning = smartContractInvocationSigning;
    this.transferSigning = transferSigning;
    this.walletGeneration = walletGeneration;
  }

  public TaskSettings getSmartContractDeploymentSigning() {
    return smartContractDeploymentSigning;
  }

  public TaskSettings getSmartContractInvocationSigning() {
    return smartContractInvocationSigning;
  }

  public TaskSettings getTransferSigning() {
    return transferSigning;
  }

  public TaskSettings getWalletGeneration() {
    return walletGeneration;
  }
}
