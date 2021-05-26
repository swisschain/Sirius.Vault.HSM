package io.swisschain.repositories.wallets;

import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.exceptions.WalletAlreadyExistsException;
import io.swisschain.domain.primitives.NetworkType;
import io.swisschain.domain.wallet.Wallet;
import io.swisschain.repositories.DbConnectionFactory;
import org.junit.Assert;

import java.sql.SQLException;
import java.time.Instant;

public class WalletRepositoryTests {

  private WalletRepository walletRepository;

  // @Before
  public void initialize() {
    walletRepository =
        new WalletRepositoryImp(
            new DbConnectionFactory(
                "jdbc:postgresql://localhost:5432/sirius", "admin", "admin", "vault"));
  }

  // @Test
  public void insert_on_conflict()
      throws SQLException, WalletAlreadyExistsException, OperationExhaustedException,
          OperationFailedException {
    // arrange

    var wallet =
        new Wallet(
            (long) 100005,
            "blockchainId",
            "protocolCode",
            NetworkType.Private,
            "address5",
            "publicKey",
            "privateKey",
            "tenantId",
            "group",
            Instant.now());

    // act

    var result1 = walletRepository.insert(wallet);

    var result2 = walletRepository.insert(wallet);

    // assert

    Assert.assertNotEquals(result1, result2);
  }
}
