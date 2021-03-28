package io.swisschain.config.tasks;

public class TasksConfig {
  public PublisherConfig transferSigningPublisher;
  public ConsumerConfig transferSigningConsumer;
  public PublisherConfig walletGenerationPublisher;
  public ConsumerConfig walletGenerationConsumer;
  public int monitoringPeriodInSeconds;
}
