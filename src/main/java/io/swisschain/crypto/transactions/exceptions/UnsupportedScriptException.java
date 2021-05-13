package io.swisschain.crypto.transactions.exceptions;

public class UnsupportedScriptException extends Exception {
  private final String message;

  public UnsupportedScriptException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
