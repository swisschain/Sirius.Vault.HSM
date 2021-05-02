package io.swisschain.domain.common;

public class CoinId {
  private final String transactionId;
  private final int number;

  public CoinId(String transactionId, int number) {
    this.transactionId = transactionId;
    this.number = number;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public int getNumber() {
    return number;
  }

  @Override
  public String toString() {
    return "CoinId{" + "transactionId='" + transactionId + '\'' + ", number=" + number + '}';
  }
}
