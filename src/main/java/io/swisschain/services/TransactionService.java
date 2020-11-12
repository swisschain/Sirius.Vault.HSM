package io.swisschain.services;

import io.swisschain.config.Config;
import io.swisschain.contracts.Document;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.transaction.signing.TransactionSignerFactory;
import io.swisschain.repositories.wallets.WalletRepository;

import java.sql.SQLException;

public class TransactionService {

  private final WalletRepository walletRepository;
  private final TransactionSignerFactory transactionSignerFactory;
  private final JsonSerializer jsonMapper = new JsonSerializer();

  public TransactionService(WalletRepository walletRepository, Config config) {
    this.walletRepository = walletRepository;
    this.transactionSignerFactory = new TransactionSignerFactory(config);
  }

  public Transaction create(TransferSigningRequest transferSigningRequest) throws Exception {

    if (transferSigningRequest.getSigningAddresses().size() != 1) {
      throw new Exception("Currently only one signing address supported.");
    }

    var signingAddress = transferSigningRequest.getSigningAddresses().get(0);

    Wallet wallet;
    try {
      wallet =
          this.walletRepository.getFind(
              signingAddress.getAddress(),
              signingAddress.getGroup(),
              transferSigningRequest.getTenantId());
    } catch (SQLException exception) {
      throw new Exception("An error occurred while getting wallet.", exception);
    }

    if (wallet == null) {
      throw new Exception(
          String.format(
              "Wallet not found. Address: %s; Group: %s; TenantId: %s",
              signingAddress.getAddress(),
              signingAddress.getGroup(),
              transferSigningRequest.getTenantId()));
    }

    var signer =
        transactionSignerFactory.getCoinsTransactionSigner(
            BlockchainProtocolCodes.fromString(transferSigningRequest.getProtocolCode()));

    var document = jsonMapper.deserialize(transferSigningRequest.getDocument(), Document.class);

    var result =
        signer.sign(
            transferSigningRequest.getBuiltTransaction(),
            transferSigningRequest.getCoinsToSpend(),
            wallet.getPrivateKey(),
            wallet.getPublicKey(),
            transferSigningRequest.getNetworkType(),
            document.getTransferDetails());

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
