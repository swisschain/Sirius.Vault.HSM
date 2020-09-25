package io.swisschain.isAlive;

import fi.iki.elonen.router.RouterNanoHTTPD;

import java.io.IOException;

public class IsAliveService extends RouterNanoHTTPD {
  public IsAliveService(int port) throws IOException {
    super(port);
    addMappings();
    start(SOCKET_READ_TIMEOUT, true);
  }

  @Override
  public void addMappings() {
    addRoute("/api/isalive", IsAliveHandler.class);
  }
}
