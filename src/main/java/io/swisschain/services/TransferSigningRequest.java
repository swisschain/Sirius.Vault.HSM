package io.swisschain.services;

import io.swisschain.primitives.DoubleSpendingProtectionType;
import io.swisschain.primitives.NetworkType;

import java.time.Instant;
import java.util.List;

public class TransferSigningRequest {
  private final Long id;
  private final String blockchainId;
  private final String protocolCode;
  private final NetworkType networkType;
  private final DoubleSpendingProtectionType doubleSpendingProtectionType;
  private final byte[] builtTransaction;
  private final List<String> signingAddresses;
  private final List<Coin> coinsToSpend;
  private final String policyResult;
  private final String guardianSignature;
  private final String group;
  private final String tenantId;
  private final Instant createdAt;
  private final Instant updatedAt;

  public TransferSigningRequest(
      Long id,
      String blockchainId,
      String protocolCode,
      NetworkType networkType,
      DoubleSpendingProtectionType doubleSpendingProtectionType,
      byte[] builtTransaction,
      List<String> signingAddresses,
      List<Coin> coinsToSpend,
      String policyResult,
      String guardianSignature,
      String group,
      String tenantId,
      Instant createdAt,
      Instant updatedAt) {
    this.id = id;
    this.blockchainId = blockchainId;
    this.protocolCode = protocolCode;
    this.networkType = networkType;
    this.doubleSpendingProtectionType = doubleSpendingProtectionType;
    this.builtTransaction = builtTransaction;
    this.signingAddresses = signingAddresses;
    this.coinsToSpend = coinsToSpend;
    this.policyResult = policyResult;
    this.guardianSignature = guardianSignature;
    this.group = group;
    this.tenantId = tenantId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public String getBlockchainId() {
    return blockchainId;
  }

  public String getProtocolCode() {
    return protocolCode;
  }

  public NetworkType getNetworkType() {
    return networkType;
  }

  public DoubleSpendingProtectionType getDoubleSpendingProtectionType() {
    return doubleSpendingProtectionType;
  }

  public byte[] getBuiltTransaction() {
    return builtTransaction;
  }

  public List<String> getSigningAddresses() {
    return signingAddresses;
  }

  public List<Coin> getCoinsToSpend() {
    return coinsToSpend;
  }

  public String getPolicyResult() {
    return policyResult;
  }

  public String getGuardianSignature() {
    return guardianSignature;
  }

  public String getGroup() {
    return group;
  }

  public String getTenantId() {
    return tenantId;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }
}
