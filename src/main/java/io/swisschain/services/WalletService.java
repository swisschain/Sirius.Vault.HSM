package io.swisschain.services;

import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.address.generation.AddressGeneratorFactory;
import io.swisschain.crypto.exceptions.BlockchainNotSupportedException;
import io.swisschain.crypto.exceptions.InvalidPublicKeyException;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.exceptions.WalletAlreadyExistsException;
import io.swisschain.domain.wallet.RejectionReason;
import io.swisschain.domain.wallet.Wallet;
import io.swisschain.domain.wallet.WalletGenerationRequest;
import io.swisschain.repositories.wallets.WalletRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class WalletService {
  private final WalletRepository walletRepository;
  private final WalletApiService walletApiService;
  private final AddressGeneratorFactory addressGeneratorFactory;
  private final Logger logger = LogManager.getLogger();

  public WalletService(
      WalletRepository walletRepository,
      WalletApiService walletApiService,
      AddressGeneratorFactory addressGeneratorFactory) {
    this.walletRepository = walletRepository;
    this.walletApiService = walletApiService;
    this.addressGeneratorFactory = addressGeneratorFactory;
  }

  public void generate(WalletGenerationRequest walletGenerationRequest)
      throws OperationFailedException, OperationExhaustedException, SQLException {
    Wallet wallet = null;
    try {
      var addressGenerator =
          addressGeneratorFactory.get(
              BlockchainProtocolCodes.fromString(walletGenerationRequest.getProtocolCode()));
      var generatedAddress = addressGenerator.generate(walletGenerationRequest.getNetworkType());
      wallet =
          Wallet.create(
              walletGenerationRequest.getId(),
              walletGenerationRequest.getBlockchainId(),
              walletGenerationRequest.getProtocolCode(),
              walletGenerationRequest.getNetworkType(),
              generatedAddress.getAddress(),
              generatedAddress.getPublicKey(),
              generatedAddress.getPrivateKey(),
              walletGenerationRequest.getTenantId(),
              walletGenerationRequest.getGroup());
    } catch (BlockchainNotSupportedException exception) {
      walletGenerationRequest.reject(
          RejectionReason.UnknownBlockchain, "Blockchain is not supported");
      logger.error(
          String.format(
              "It's not possible to create wallet for request %d. Blockchain %s not supported.",
              walletGenerationRequest.getId(), walletGenerationRequest.getBlockchainId()));
    } catch (InvalidPublicKeyException exception) {
      walletGenerationRequest.reject(RejectionReason.Other, "Invalid public key");
      logger.error(
          String.format(
              "It's not possible to create wallet for request %d. Invalid public key.",
              walletGenerationRequest.getId()),
          exception);
    } catch (Exception exception) {
      walletGenerationRequest.reject(RejectionReason.Other, "Unexpected error");
      logger.error(
          String.format(
              "It's not possible to create wallet for request %d. An unexpected error occurred.",
              walletGenerationRequest.getId()),
          exception);
    }

    if (walletGenerationRequest.isRejected()) {
      walletApiService.reject(walletGenerationRequest);
    } else {
      try {
        walletRepository.insert(wallet);
      } catch (WalletAlreadyExistsException exception) {
        wallet = walletRepository.getByRequestId(walletGenerationRequest.getId());
      }
      if (wallet != null) {
        walletGenerationRequest.confirm(wallet.getAddress(), wallet.getPublicKey());
        walletApiService.confirm(walletGenerationRequest);
      }
    }
  }
}
