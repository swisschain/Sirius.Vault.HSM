package io.swisschain.repositories.wallets;

import io.swisschain.repositories.DbConnectionFactory;
import io.swisschain.repositories.Repository;
import io.swisschain.repositories.exceptions.WalletAlreadyExistsException;
import io.swisschain.services.Wallet;

import java.sql.*;

public class WalletRepository extends Repository {
  private final DbConnectionFactory connectionFactory;

  public WalletRepository(DbConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  public Wallet getFind(String address, String group, String tenantId) throws SQLException {

    String sql =
        "SELECT *\n"
            + String.format("FROM %s.wallets\n", this.connectionFactory.getSchema())
                + "WHERE address = ? AND \"group\" = ? AND tenant_id = ?;";

    try (Connection connection = this.connectionFactory.create();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, address);
      statement.setString(2, group);
      statement.setString(3, tenantId);

      var resultSet = statement.executeQuery();

      if (resultSet.next()) {
        WalletEntity entity = new WalletEntity();
        entity.map(resultSet);
        return entity.toDomain();
      }

      return null;
    }
  }

  public Wallet getByGenerationRequestId(Long walletGenerationRequestId) throws SQLException {
    String sql =
        "SELECT *\n"
            + String.format("FROM %s.wallets\n", this.connectionFactory.getSchema())
            + "WHERE wallet_generation_request_id = ?;";

    try (Connection connection = this.connectionFactory.create();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, walletGenerationRequestId);
      ResultSet resultSet = statement.executeQuery();

      WalletEntity entity = null;

      if (resultSet.next()) {
        entity = new WalletEntity();
        entity.map(resultSet);
      }

      return entity != null ? entity.toDomain() : null;
    }
  }

  public void insert(Wallet wallet) throws Exception, WalletAlreadyExistsException {
    String sql =
        String.format("INSERT INTO %s.wallets(\n", this.connectionFactory.getSchema())
            + "wallet_generation_request_id, blockchain_id, protocol_code, network_type, address, public_key, private_key, tenant_id, \"group\", created_at)\n"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";

    try (Connection connection = this.connectionFactory.create();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, wallet.getWalletGenerationRequestId());
      statement.setString(2, wallet.getBlockchainId());
      statement.setString(3, wallet.getProtocolCode());
      statement.setString(4, wallet.getNetworkType().name());
      statement.setString(5, wallet.getAddress());
      statement.setString(6, wallet.getPublicKey());
      statement.setString(7, wallet.getPrivateKey());
      statement.setString(8, wallet.getTenantId());
      statement.setString(9, wallet.getGroup());
      statement.setTimestamp(10, Timestamp.from(wallet.getCreatedAt()));

      statement.execute();
    } catch (SQLException exception) {
      if (exception.getSQLState().equals(UniqueViolationErrorCode)) {
        throw new WalletAlreadyExistsException();
      }
      throw new Exception("An unexpected error occurred while inserting wallet.", exception);
    }
  }
}
