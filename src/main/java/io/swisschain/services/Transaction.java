package io.swisschain.services;

import io.swisschain.primitives.NetworkType;

import java.time.Instant;
import java.util.List;

public class Transaction {
  private final Long transferSigningRequestId;
  private final String blockchainId;
  private final String protocolCode;
  private final NetworkType networkType;
  private final List<String> signingAddresses;
  private final Instant createdAt;
  private byte[] signedTransaction;
  private String transactionId;

  public Transaction(
      Long transferSigningRequestId,
      String blockchainId,
      String protocolCode,
      NetworkType networkType,
      List<String> signingAddresses,
      byte[] signedTransaction,
      String transactionId,
      Instant createdAt) {
    this.transferSigningRequestId = transferSigningRequestId;
    this.blockchainId = blockchainId;
    this.protocolCode = protocolCode;
    this.networkType = networkType;
    this.signingAddresses = signingAddresses;
    this.signedTransaction = signedTransaction;
    this.transactionId = transactionId;
    this.createdAt = createdAt;
  }

  public static Transaction create(
      Long transferSigningRequestId,
      String blockchainId,
      String protocolCode,
      NetworkType networkType,
      List<String> signingAddresses,
      byte[] signedTransaction,
      String transactionId) {
    return new Transaction(
        transferSigningRequestId,
        blockchainId,
        protocolCode,
        networkType,
        signingAddresses,
        signedTransaction,
        transactionId,
        Instant.now());
  }

  public Long getTransactionSigningRequestId() {
    return transferSigningRequestId;
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

  public List<String> getSigningAddresses() {
    return signingAddresses;
  }

  public byte[] getSignedTransaction() {
    return signedTransaction;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void sign(byte[] signedTransaction, String transactionId) {
    this.signedTransaction = signedTransaction;
    this.transactionId = transactionId;
  }
}
