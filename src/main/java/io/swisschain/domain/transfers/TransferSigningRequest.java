package io.swisschain.domain.transfers;

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
  private final List<SigningAddress> signingAddresses;
  private final List<Coin> coinsToSpend;
  private final String document;
  private final String signature;
  private final String tenantId;
  private RejectionReason rejectionReason;
  private String rejectionReasonMessage;
  private String transactionId;
  private byte[] signedTransaction;
  private final Instant createdAt;
  private final Instant updatedAt;

  public TransferSigningRequest(
      Long id,
      String blockchainId,
      String protocolCode,
      NetworkType networkType,
      DoubleSpendingProtectionType doubleSpendingProtectionType,
      byte[] builtTransaction,
      List<SigningAddress> signingAddresses,
      List<Coin> coinsToSpend,
      String document,
      String signature,
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
    this.document = document;
    this.signature = signature;
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

  public List<SigningAddress> getSigningAddresses() {
    return signingAddresses;
  }

  public List<Coin> getCoinsToSpend() {
    return coinsToSpend;
  }

  public String getDocument() {
    return document;
  }

  public String getSignature() {
    return signature;
  }

  public String getTenantId() {
    return tenantId;
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

  public String getTransactionId() {
    return transactionId;
  }

  public byte[] getSignedTransaction() {
    return signedTransaction;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void reject(RejectionReason rejectionReason, String rejectionReasonMessage) {
    this.rejectionReason = rejectionReason;
    this.rejectionReasonMessage = rejectionReasonMessage;
  }

  public void confirm(String transactionId, byte[] signedTransaction) {
    this.transactionId = transactionId;
    this.signedTransaction = signedTransaction;
  }
}
