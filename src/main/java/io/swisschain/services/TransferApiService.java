package io.swisschain.services;

import io.swisschain.domain.transfers.TransferSigningRequest;
import io.swisschain.domain.exceptions.OperationExhaustedException;
import io.swisschain.domain.exceptions.OperationFailedException;

import java.util.List;

public interface TransferApiService {
  List<TransferSigningRequest> get() throws OperationFailedException, OperationExhaustedException;

  void confirm(TransferSigningRequest transferSigningRequest)
      throws OperationFailedException, OperationExhaustedException;

  void reject(TransferSigningRequest transferSigningRequest)
      throws OperationFailedException, OperationExhaustedException;
}
