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
import java.time.Duration;
import java.time.Instant;

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

      var generateAddressStart = Instant.now();
      var generatedAddress = addressGenerator.generate(walletGenerationRequest.getNetworkType());
      var generateAddressEnd = Instant.now();

      logger.info(
          String.format(
              "Address generation HSM. Request: %d. Blockchain: %s. Elapsed: %d ms.",
              walletGenerationRequest.getId(),
              walletGenerationRequest.getBlockchainId(),
              Duration.between(generateAddressStart, generateAddressEnd).toMillis()));

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
        var walletInsertStart = Instant.now();

        var result = walletRepository.insert(wallet);

        if (!result) {
          wallet = walletRepository.getByRequestId(walletGenerationRequest.getId());
        }

        var walletInsertEnd = Instant.now();

        logger.info(
            String.format(
                "Address generation insert DB. Request: %d. Blockchain: %s. Elapsed: %d ms.",
                walletGenerationRequest.getId(),
                walletGenerationRequest.getBlockchainId(),
                Duration.between(walletInsertStart, walletInsertEnd).toMillis()));
      } catch (WalletAlreadyExistsException exception) {
        wallet = walletRepository.getByRequestId(walletGenerationRequest.getId());
      }
      if (wallet != null) {
        var walletConfirmStart = Instant.now();
        walletGenerationRequest.confirm(wallet.getAddress(), wallet.getPublicKey());
        walletApiService.confirm(walletGenerationRequest);
        var walletConfirmEnd = Instant.now();

        logger.info(
            String.format(
                "Address generation confirm gRPC. Request: %d. Blockchain: %s. Elapsed: %d ms.",
                walletGenerationRequest.getId(),
                walletGenerationRequest.getBlockchainId(),
                Duration.between(walletConfirmStart, walletConfirmEnd).toMillis()));
      }
    }
  }
}
