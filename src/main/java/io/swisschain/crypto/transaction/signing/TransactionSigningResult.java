package io.swisschain.crypto.transaction.signing;

import org.apache.commons.codec.binary.Hex;

public class TransactionSigningResult {
  private final String transactionId;
  private final byte[] signedTransaction;

  public TransactionSigningResult(String transactionId, byte[] signedTransaction) {
    this.transactionId = transactionId;
    this.signedTransaction = signedTransaction;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public byte[] getSignedTransaction() {
    return signedTransaction;
  }

  @Override
  public String toString() {
    return "TransactionSigningResult{"
        + "transactionId='"
        + transactionId
        + '\''
        + ", signedTransaction="
        + Hex.encodeHexString(signedTransaction)
        + '}';
  }
}
