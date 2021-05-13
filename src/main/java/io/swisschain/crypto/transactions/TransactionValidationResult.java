package io.swisschain.crypto.transactions;

public class TransactionValidationResult {
  private final boolean isValid;
  private final String error;

  private TransactionValidationResult(boolean isValid, String error) {
    this.isValid = isValid;
    this.error = error;
  }

  public boolean isValid() {
    return isValid;
  }

  public String getError() {
    return error;
  }

  public static TransactionValidationResult CreateValid() {
    return new TransactionValidationResult(true, null);
  }

  public static TransactionValidationResult CreateInvalid(String error) {
    return new TransactionValidationResult(false, error);
  }
}
