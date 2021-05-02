package io.swisschain.crypto.transactions;

import io.swisschain.domain.transactions.TransactionRejectionReason;

public class TransactionValidationResult {
  private final boolean isValid;
  private final TransactionRejectionReason rejectionReason;
  private final String error;

  private TransactionValidationResult(
      boolean isValid, TransactionRejectionReason rejectionReason, String error) {
    this.isValid = isValid;
    this.rejectionReason = rejectionReason;
    this.error = error;
  }

  public boolean isValid() {
    return isValid;
  }

  public TransactionRejectionReason getRejectionReason() {
    return rejectionReason;
  }

  public String getError() {
    return error;
  }

  public static TransactionValidationResult CreateValid() {
    return new TransactionValidationResult(true, null, null);
  }

  public static TransactionValidationResult CreateInvalid(
      TransactionRejectionReason rejectionReason, String error) {
    return new TransactionValidationResult(false, rejectionReason, error);
  }
}
