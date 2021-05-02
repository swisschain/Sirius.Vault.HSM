package io.swisschain.domain.transactions;

import io.swisschain.domain.common.Blockchain;
import io.swisschain.domain.primitives.DoubleSpendingProtectionType;
import io.swisschain.domain.common.Coin;
import io.swisschain.domain.common.SigningAddress;

import java.time.Instant;
import java.util.List;

public class TransactionSigningRequest {
  private final Long id;
  private final TransactionType type;
  private final Blockchain blockchain;
  private final DoubleSpendingProtectionType doubleSpendingProtectionType;
  private final byte[] builtTransaction;
  private final SigningAddress signingAddress;
  private final List<Coin> coinsToSpend;
  private final String document;
  private final String signature;
  private final String tenantId;
  private TransactionRejectionReason transactionRejectionReason;
  private String rejectionReasonMessage;
  private String transactionId;
  private byte[] signedTransaction;
  private final Instant createdAt;
  private final Instant updatedAt;

  public TransactionSigningRequest(
      Long id,
      TransactionType type,
      Blockchain blockchain,
      DoubleSpendingProtectionType doubleSpendingProtectionType,
      byte[] builtTransaction,
      SigningAddress signingAddress,
      List<Coin> coinsToSpend,
      String document,
      String signature,
      String tenantId,
      Instant createdAt,
      Instant updatedAt) {
    this.id = id;
    this.type = type;
    this.blockchain = blockchain;
    this.doubleSpendingProtectionType = doubleSpendingProtectionType;
    this.builtTransaction = builtTransaction;
    this.signingAddress = signingAddress;
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

  public TransactionType getType() {
    return type;
  }

  public Blockchain getBlockchain() {
    return blockchain;
  }

  public DoubleSpendingProtectionType getDoubleSpendingProtectionType() {
    return doubleSpendingProtectionType;
  }

  public byte[] getBuiltTransaction() {
    return builtTransaction;
  }

  public SigningAddress getSigningAddress() {
    return signingAddress;
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
    return transactionRejectionReason != null;
  }

  public TransactionRejectionReason getRejectionReason() {
    return transactionRejectionReason;
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

  public void reject(
      TransactionRejectionReason transactionRejectionReason, String rejectionReasonMessage) {
    this.transactionRejectionReason = transactionRejectionReason;
    this.rejectionReasonMessage = rejectionReasonMessage;
  }

  public void confirm(String transactionId, byte[] signedTransaction) {
    this.transactionId = transactionId;
    this.signedTransaction = signedTransaction;
  }
}
