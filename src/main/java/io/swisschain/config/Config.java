package io.swisschain.config;

import io.swisschain.config.tasks.TasksConfig;

public class Config {
  public String instanceName;
  public HsmConfig hsmConfig;
  public DbConfig db;
  public VaultApiConfig vaultApi;
  public TasksConfig tasks;
}
