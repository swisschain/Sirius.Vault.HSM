package io.swisschain.services;

import io.swisschain.primitives.NetworkType;

import java.util.Date;
import java.util.List;

public class Transaction {
  private final Long transferSigningRequestId;
  private final String blockchainId;
  private final String protocolCode;
  private final NetworkType networkType;
  private final List<String> signingAddresses;
  private final byte[] signedTransaction;
  private final String transactionId;
  private final Date createdAt;

  public Transaction(
      Long transferSigningRequestId,
      String blockchainId,
      String protocolCode,
      NetworkType networkType,
      List<String> signingAddresses,
      byte[] signedTransaction,
      String transactionId,
      Date createdAt) {
    this.transferSigningRequestId = transferSigningRequestId;
    this.blockchainId = blockchainId;
    this.protocolCode = protocolCode;
    this.networkType = networkType;
    this.signingAddresses = signingAddresses;
    this.signedTransaction = signedTransaction;
    this.transactionId = transactionId;
    this.createdAt = createdAt;
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

  public Date getCreatedAt() {
    return createdAt;
  }
}
