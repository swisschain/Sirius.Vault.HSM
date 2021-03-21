package io.swisschain.domain.wallet;

import io.swisschain.primitives.NetworkType;

import java.time.Instant;

public class Wallet {
  private final Long id;
  private final Long walletGenerationRequestId;
  private final String blockchainId;
  private final String protocolCode;
  private final NetworkType networkType;
  private final String address;
  private final String publicKey;
  private final String privateKey;
  private final String tenantId;
  private final String group;
  private final Instant createdAt;

  public Wallet(
      Long walletGenerationRequestId,
      String blockchainId,
      String protocolCode,
      NetworkType networkType,
      String address,
      String publicKey,
      String privateKey,
      String tenantId,
      String group,
      Instant createdAt) {
    this(
        (long) 0,
        walletGenerationRequestId,
        blockchainId,
        protocolCode,
        networkType,
        address,
        publicKey,
        privateKey,
        tenantId,
        group,
        createdAt);
  }

  public Wallet(
      Long id,
      Long walletGenerationRequestId,
      String blockchainId,
      String protocolCode,
      NetworkType networkType,
      String address,
      String publicKey,
      String privateKey,
      String tenantId,
      String group,
      Instant createdAt) {
    this.id = id;
    this.walletGenerationRequestId = walletGenerationRequestId;
    this.blockchainId = blockchainId;
    this.protocolCode = protocolCode;
    this.networkType = networkType;
    this.address = address;
    this.publicKey = publicKey;
    this.privateKey = privateKey;
    this.tenantId = tenantId;
    this.group = group;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public Long getWalletGenerationRequestId() {
    return this.walletGenerationRequestId;
  }

  public String getBlockchainId() {
    return this.blockchainId;
  }

  public String getProtocolCode() {
    return this.protocolCode;
  }

  public NetworkType getNetworkType() {
    return this.networkType;
  }

  public String getAddress() {
    return this.address;
  }

  public String getPublicKey() {
    return this.publicKey;
  }

  public String getPrivateKey() {
    return this.privateKey;
  }

  public String getTenantId() {
    return this.tenantId;
  }

  public String getGroup() {
    return this.group;
  }

  public Instant getCreatedAt() {
    return this.createdAt;
  }

  public static Wallet create(
      Long walletGenerationRequestId,
      String blockchainId,
      String protocolCode,
      NetworkType networkType,
      String address,
      String publicKey,
      String privateKey,
      String tenantId,
      String group) {
    var createdAt = Instant.now();

    return new Wallet(
        walletGenerationRequestId,
        blockchainId,
        protocolCode,
        networkType,
        address,
        publicKey,
        privateKey,
        tenantId,
        group,
        createdAt);
  }
}
