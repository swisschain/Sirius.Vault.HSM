package io.swisschain.crypto.hsm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
  public String access_token;
  public String refresh_token;
  public String ims_user_id;
  public String token_type;
  public Long expires_in;
  public Long expiration;
  public String scope;
}
