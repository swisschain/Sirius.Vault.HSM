package io.swisschain.services;

import io.swisschain.config.Config;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transaction.signing.TransactionSignerFactory;
import io.swisschain.repositories.wallets.WalletRepository;

import java.sql.SQLException;

public class TransactionService {

  private final WalletRepository walletRepository;
  private final TransactionSignerFactory transactionSignerFactory;

  public TransactionService(WalletRepository walletRepository, Config config) {
    this.walletRepository = walletRepository;
    this.transactionSignerFactory = new TransactionSignerFactory(config);
  }

  public Transaction create(TransferSigningRequest transferSigningRequest) throws Exception {

    if (transferSigningRequest.getSigningAddresses().size() != 1) {
      throw new Exception("Currently only one signing address supported.");
    }

    Wallet wallet;
    try {
      wallet =
          this.walletRepository.getFind(
              transferSigningRequest.getSigningAddresses().get(0),
              transferSigningRequest.getGroup(),
              transferSigningRequest.getTenantId());
    } catch (SQLException exception) {
      throw new Exception("An error occurred while getting wallet.", exception);
    }

    if (wallet == null) {
      throw new Exception(
          String.format(
              "Wallet not found. Address: %s; Group: %s; TenantId: %s",
              transferSigningRequest.getSigningAddresses().get(0),
              transferSigningRequest.getGroup(),
              transferSigningRequest.getTenantId()));
    }

    var signer =
        transactionSignerFactory.getCoinsTransactionSigner(
            BlockchainProtocolCodes.fromString(transferSigningRequest.getProtocolCode()));

    var result =
        signer.sign(
            transferSigningRequest.getBuiltTransaction(),
            transferSigningRequest.getCoinsToSpend(),
            wallet.getPrivateKey(),
            wallet.getPublicKey(),
            transferSigningRequest.getNetworkType());

    // TODO: Save transaction
    // TODO: Save transferSigningRequest

    return Transaction.create(
        transferSigningRequest.getId(),
        transferSigningRequest.getBlockchainId(),
        transferSigningRequest.getProtocolCode(),
        transferSigningRequest.getNetworkType(),
        transferSigningRequest.getSigningAddresses(),
        result.getSignedTransaction(),
        result.getTransactionId());
  }
}
