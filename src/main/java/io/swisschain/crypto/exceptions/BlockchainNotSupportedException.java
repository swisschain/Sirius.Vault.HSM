package io.swisschain.crypto.exceptions;

public class BlockchainNotSupportedException extends Exception {
  public BlockchainNotSupportedException() {
    super();
  }

  public BlockchainNotSupportedException(String message) {
    super(message);
  }
}
