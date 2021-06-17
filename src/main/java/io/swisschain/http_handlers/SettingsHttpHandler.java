package io.swisschain.http_handlers;

import com.google.common.net.HttpHeaders;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.swisschain.http_handlers.responses.settings.*;
import io.swisschain.services.JsonSerializer;
import io.swisschain.services.SettingsKeysService;
import io.swisschain.services.SettingsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class SettingsHttpHandler implements HttpHandler {

  private final Logger logger = LogManager.getLogger();

  private final SettingsService settingsService;
  private final SettingsKeysService settingsKeysService;
  private final JsonSerializer jsonSerializer;

  public SettingsHttpHandler(
      SettingsService settingsService,
      SettingsKeysService settingsKeysService,
      JsonSerializer jsonSerializer) {
    this.settingsService = settingsService;
    this.settingsKeysService = settingsKeysService;
    this.jsonSerializer = jsonSerializer;
  }

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    exchange.getResponseHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
    if ("GET".equals(exchange.getRequestMethod())) {
      final var bytes = handleGet().getBytes();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, bytes.length);
      exchange.getResponseBody().write(bytes);
    } else if ("POST".equals(exchange.getRequestMethod())) {
      handlePost(exchange.getRequestBody());
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_NO_CONTENT, -1);
    }
  }

  private String handleGet() {
    var appConfig = settingsService.get();

    SettingsResponse response;

    if (appConfig == null) {
      response =
          new SettingsResponse(null, null, new PublicKeys(settingsKeysService.getPublic(), null));
    } else {
      response =
          new SettingsResponse(
              appConfig.name,
              new TasksSettings(
                  new TaskSettings(
                      appConfig.tasks.smartContractDeploymentSigningPublisher.periodInSeconds,
                      appConfig.tasks.smartContractDeploymentSigningPublisher.queueSize,
                      appConfig.tasks.smartContractDeploymentSigningConsumer.threadsCount),
                  new TaskSettings(
                      appConfig.tasks.smartContractInvocationSigningPublisher.periodInSeconds,
                      appConfig.tasks.smartContractInvocationSigningPublisher.queueSize,
                      appConfig.tasks.smartContractInvocationSigningConsumer.threadsCount),
                  new TaskSettings(
                      appConfig.tasks.transferSigningPublisher.periodInSeconds,
                      appConfig.tasks.transferSigningPublisher.queueSize,
                      appConfig.tasks.transferSigningConsumer.threadsCount),
                  new TaskSettings(
                      appConfig.tasks.walletGenerationPublisher.periodInSeconds,
                      appConfig.tasks.walletGenerationPublisher.queueSize,
                      appConfig.tasks.walletGenerationConsumer.threadsCount)),
              new PublicKeys(settingsKeysService.getPublic(), appConfig.keys.guardian.publicKey));
    }

    String result;
    try {
      result = jsonSerializer.serialize(response);
    } catch (Exception exception) {
      logger.error("An error occurred while processing get settings request.", exception);
      result = "{}";
    }
    return result;
  }

  private void handlePost(InputStream inputStream) {
    try {
      var secureSettings = jsonSerializer.deserialize(inputStream, SecureSettingsRequest.class);
      settingsService.set(
          secureSettings.getKey(), secureSettings.getNonce(), secureSettings.getSettings());
    } catch (Exception exception) {
      logger.error("An error occurred while processing set settings request.", exception);
    }
  }
}
