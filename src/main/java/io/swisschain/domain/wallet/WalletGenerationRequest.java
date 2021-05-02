package io.swisschain.domain.wallet;

import io.swisschain.domain.primitives.NetworkType;

public class WalletGenerationRequest {
  private final Long id;
  private final String blockchainId;
  private final String protocolCode;
  private final NetworkType networkType;
  private final String tenantId;
  private final String group;
  private String address;
  private String publicKey;
  private RejectionReason rejectionReason;
  private String rejectionReasonMessage;

  public WalletGenerationRequest(
      Long id,
      String blockchainId,
      String protocolCode,
      NetworkType networkType,
      String tenantId,
      String group) {
    this.id = id;
    this.blockchainId = blockchainId;
    this.protocolCode = protocolCode;
    this.networkType = networkType;
    this.tenantId = tenantId;
    this.group = group;
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

  public String getTenantId() {
    return tenantId;
  }

  public String getGroup() {
    return group;
  }

  public String getAddress() {
    return address;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public Boolean isRejected() {
    return rejectionReason != null;
  }

  public RejectionReason getRejectionReason() {
    return rejectionReason;
  }

  public String getRejectionReasonMessage() {
    return rejectionReasonMessage;
  }

  public void confirm(String address, String publicKey) {
    this.address = address;
    this.publicKey = publicKey;
  }

  public void reject(RejectionReason rejectionReason, String rejectionReasonMessage) {
    this.rejectionReason = rejectionReason;
    this.rejectionReasonMessage = rejectionReasonMessage;
  }
}
