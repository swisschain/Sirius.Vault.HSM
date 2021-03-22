package io.swisschain.services;

import io.swisschain.domain.wallet.WalletGenerationRequest;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;

import java.util.List;

public interface WalletApiService {
  List<WalletGenerationRequest> get() throws OperationFailedException, OperationExhaustedException;

  void confirm(WalletGenerationRequest walletGenerationRequest)
      throws OperationFailedException, OperationExhaustedException;

  void reject(WalletGenerationRequest walletGenerationRequest)
      throws OperationFailedException, OperationExhaustedException;
}
