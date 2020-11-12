package io.swisschain.crypto.transaction.signing.exceptions;

public class TransferDetailsValidationException extends Exception {
  private final String message;

  public TransferDetailsValidationException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
