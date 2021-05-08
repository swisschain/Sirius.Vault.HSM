package io.swisschain.crypto.utils.tezos.forging.operations;

public class OriginationContent extends ManagerOperationContent {
  public OriginationContent() {
    Kind = "origination";
  }

  public long Balance;
  public String Delegate;

  //TODO handle Code/Script
}
