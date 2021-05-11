package io.swisschain.services;

import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;
import io.swisschain.domain.transactions.TransactionSigningRequest;

import java.util.List;

public interface TransactionSigningApiService {
  List<TransactionSigningRequest> get()
      throws OperationFailedException, OperationExhaustedException;

  void confirm(TransactionSigningRequest signingRequest)
      throws OperationFailedException, OperationExhaustedException;

  void reject(TransactionSigningRequest signingRequest)
      throws OperationFailedException, OperationExhaustedException;
}
