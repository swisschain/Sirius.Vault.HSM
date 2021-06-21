package io.swisschain.http_handlers.responses.settings;

public class SettingsResponse {
  private String name;
  private TasksSettings tasks;
  private PublicKeys keys;

  public SettingsResponse(
      String name, TasksSettings tasks, PublicKeys keys) {
    this.name = name;
    this.tasks = tasks;
    this.keys = keys;
  }

  public String getName() {
    return name;
  }

  public TasksSettings getTasks() {
    return tasks;
  }

  public PublicKeys getKeys() {
    return keys;
  }
}
