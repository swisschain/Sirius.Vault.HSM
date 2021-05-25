package io.swisschain.repositories.wallets;

import io.swisschain.domain.wallet.Wallet;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.exceptions.WalletAlreadyExistsException;

import java.sql.SQLException;

public interface WalletRepository {
  Wallet find(String address, String group, String tenantId)
      throws SQLException, OperationFailedException, OperationExhaustedException;

  Wallet getByRequestId(Long walletGenerationRequestId)
      throws SQLException, OperationFailedException, OperationExhaustedException;

  boolean insert(Wallet wallet)
      throws SQLException, WalletAlreadyExistsException, OperationFailedException,
          OperationExhaustedException;
}
