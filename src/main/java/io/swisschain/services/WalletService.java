package io.swisschain.services;

import io.swisschain.config.Config;
import io.swisschain.crypto.BlockchainProtocolCodes;
import io.swisschain.crypto.address.generation.AddressGeneratorFactory;
import io.swisschain.primitives.NetworkType;
import io.swisschain.repositories.exceptions.WalletAlreadyExistsException;
import io.swisschain.repositories.wallets.WalletRepository;

import java.time.Instant;

public class WalletService {
  private final WalletRepository walletRepository;
  private final AddressGeneratorFactory addressGeneratorFactory;

  public WalletService(WalletRepository walletRepository, Config config) {
    this.walletRepository = walletRepository;
    this.addressGeneratorFactory = new AddressGeneratorFactory(config);
  }

  public Wallet create(
      Long walletGenerationRequestId,
      String blockchainId,
      String protocolCode,
      NetworkType networkType,
      String tenantId,
      String group)
      throws Exception {

    var addressGenerator =
        addressGeneratorFactory.get(BlockchainProtocolCodes.valueOf(protocolCode));
    var generatedAddress = addressGenerator.generate(networkType);
    var createdAt = Instant.now();

    Wallet wallet =
        new Wallet(
            walletGenerationRequestId,
            blockchainId,
            protocolCode,
            networkType,
            generatedAddress.getAddress(),
            generatedAddress.getPublicKey(),
            generatedAddress.getPrivateKey(),
            tenantId,
            group,
            createdAt);

    try {
      this.walletRepository.insert(wallet);
    } catch (WalletAlreadyExistsException exception) {
      wallet = this.walletRepository.getByGenerationRequestId(walletGenerationRequestId);
    } catch (Exception exception) {
      throw new Exception("An error occurred while creating wallet", exception);
    }

    return wallet;
  }
}
