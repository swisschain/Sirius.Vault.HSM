package io.swisschain.services;

import io.swisschain.config.Config;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transaction.signing.TransactionSignerFactory;
import io.swisschain.primitives.DoubleSpendingProtectionType;
import io.swisschain.primitives.NetworkType;
import io.swisschain.repositories.wallets.WalletRepository;

import java.util.Date;
import java.util.List;

public class TransactionService {

  private final WalletRepository walletRepository;
  private final TransactionSignerFactory transactionSignerFactory;

  public TransactionService(WalletRepository walletRepository, Config config) {
    this.walletRepository = walletRepository;
    this.transactionSignerFactory = new TransactionSignerFactory(config);
  }

  public Transaction create(
      long id,
      String blockchainId,
      String protocolCode,
      NetworkType networkType,
      DoubleSpendingProtectionType doubleSpendingProtectionType,
      List<String> signingAddresses,
      String group,
      String tenantId,
      byte[] bytes,
      List<Coin> coins)
      throws Exception {
    List<Wallet> wallets;

    try {
      wallets =
          this.walletRepository.getByAddressesAndGroupAndTenantId(
              signingAddresses, group, tenantId);
    } catch (Exception exception) {
      throw new Exception("Can not get wallets.", exception);
    }

    if (wallets.size() != signingAddresses.size()) {
      throw new Exception("One or more wallets not found.");
    }

    var signer =
        transactionSignerFactory.getCoinsTransactionSigner(
            BlockchainProtocolCodes.valueOf(protocolCode));

    var result =
        signer.sign(
            bytes,
            coins,
            wallets.get(0).getPrivateKey(),
            wallets.get(0).getPublicKey(),
            networkType);

    var createdAt = new Date();

    return new Transaction(
        id,
        blockchainId,
        protocolCode,
        networkType,
        signingAddresses,
        result.getSignedTransaction(),
        result.getTransactionId(),
        createdAt);
  }
}
