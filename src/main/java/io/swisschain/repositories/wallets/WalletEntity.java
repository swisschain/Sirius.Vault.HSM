package io.swisschain.repositories.wallets;

import io.swisschain.primitives.NetworkType;
import io.swisschain.repositories.Entity;
import io.swisschain.domain.wallet.Wallet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class WalletEntity extends Entity {
  private Long id;
  private Long walletGenerationRequestId;
  private String blockchainId;
  private String protocolCode;
  private NetworkType networkType;
  private String address;
  private String publicKey;
  private String privateKey;
  private String group;
  private String tenantId;
  private Instant createdAt;

  public WalletEntity() {}

  public WalletEntity(Wallet wallet) {
    this.id = wallet.getId();
    this.walletGenerationRequestId = wallet.getWalletGenerationRequestId();
    this.blockchainId = wallet.getBlockchainId();
    this.protocolCode = wallet.getProtocolCode();
    this.networkType = wallet.getNetworkType();
    this.address = wallet.getAddress();
    this.publicKey = wallet.getPublicKey();
    this.privateKey = wallet.getPrivateKey();
    this.tenantId = wallet.getTenantId();
    this.group = wallet.getGroup();
    this.createdAt = wallet.getCreatedAt();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getWalletGenerationRequestId() {
    return walletGenerationRequestId;
  }

  public void setWalletGenerationRequestId(Long walletGenerationRequestId) {
    this.walletGenerationRequestId = walletGenerationRequestId;
  }

  public String getBlockchainId() {
    return blockchainId;
  }

  public void setBlockchainId(String blockchainId) {
    this.blockchainId = blockchainId;
  }

  public String getProtocolCode() {
    return protocolCode;
  }

  public void setProtocolCode(String protocolCode) {
    this.protocolCode = protocolCode;
  }

  public NetworkType getNetworkType() {
    return networkType;
  }

  public void setNetworkType(NetworkType networkType) {
    this.networkType = networkType;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public void map(ResultSet resultSet) throws SQLException {
    this.id = resultSet.getLong("id");
    this.walletGenerationRequestId = resultSet.getLong("wallet_generation_request_id");
    this.blockchainId = resultSet.getString("blockchain_id");
    this.protocolCode = resultSet.getString("protocol_code");
    this.networkType = Enum.valueOf(NetworkType.class, resultSet.getString("network_type"));
    this.address = resultSet.getString("address");
    this.publicKey = resultSet.getString("public_key");
    this.privateKey = resultSet.getString("private_key");
    this.tenantId = resultSet.getString("tenant_id");
    this.group = resultSet.getString("group");
    this.createdAt = resultSet.getTimestamp("created_at").toInstant();
  }

  public Wallet toDomain() {
    return new Wallet(
        this.id,
        this.walletGenerationRequestId,
        this.blockchainId,
        this.protocolCode,
        this.networkType,
        this.address,
        this.publicKey,
        this.privateKey,
        this.tenantId,
        this.group,
        this.createdAt);
  }
}
