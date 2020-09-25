package io.swisschain.crypto.hsm.ep11;

public enum ObjectClass {
  CKO_DATA(0L),
  CKO_CERTIFICATE(1L),
  CKO_PUBLIC_KEY(2L),
  CKO_PRIVATE_KEY(3L),
  CKO_SECRET_KEY(4L),
  CKO_HW_FEATURE(5L),
  CKO_DOMAIN_PARAMETERS(6L),
  CKO_MECHANISM(7L),
  CKO_OTP_KEY(8L),
  CKO_VENDOR_DEFINED(2147483648L);

  public Long value;

  ObjectClass(Long value) {
    this.value = value;
  }
}
