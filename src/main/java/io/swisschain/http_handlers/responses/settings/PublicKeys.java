package io.swisschain.http_handlers.responses.settings;

public class PublicKeys {

  private final String settings;
  private final String guardian;

  public PublicKeys(String settings, String guardian) {
    this.settings = settings;
    this.guardian = guardian;
  }

  public String getSettings() {
    return settings;
  }

  public String getGuardian() {
    return guardian;
  }
}
