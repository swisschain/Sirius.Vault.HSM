package io.swisschain.domain.transfers;

public class SigningAddress {
  private String address;
  private String group;

  public SigningAddress(String address, String group) {
    this.address = address;
    this.group = group;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }
}
