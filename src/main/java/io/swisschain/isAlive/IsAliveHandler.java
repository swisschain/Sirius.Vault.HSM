package io.swisschain.isAlive;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import io.swisschain.utils.AppVersion;

import static fi.iki.elonen.NanoHTTPD.MIME_PLAINTEXT;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;

public class IsAliveHandler extends RouterNanoHTTPD.DefaultHandler {

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public String getText() {
    IsAliveResponse response = new IsAliveResponse();
    response.setName(AppVersion.NAME);
    response.setVersion(AppVersion.VERSION);
    response.setHostname(AppVersion.HOSTNAME);
    response.setStartedAt(AppVersion.STARTED_AT);
    String result;
    try {
      result = mapper.writeValueAsString(response);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      result = "";
    }
    return result;
  }

  @Override
  public String getMimeType() {
    return MIME_PLAINTEXT;
  }

  @Override
  public NanoHTTPD.Response.IStatus getStatus() {
    return OK;
  }
}
