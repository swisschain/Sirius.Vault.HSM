package io.swisschain.crypto.transaction.signing.exceptions;

public class InvalidInputsException extends Exception {
  private final String message;

  public InvalidInputsException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
