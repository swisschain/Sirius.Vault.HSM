package io.swisschain.config.tasks;

public class TasksConfig {
  public PublisherConfig smartContractDeploymentSigningPublisher;
  public ConsumerConfig smartContractDeploymentSigningConsumer;
  public PublisherConfig smartContractInvocationSigningPublisher;
  public ConsumerConfig smartContractInvocationSigningConsumer;
  public PublisherConfig transferSigningPublisher;
  public ConsumerConfig transferSigningConsumer;
  public PublisherConfig walletGenerationPublisher;
  public ConsumerConfig walletGenerationConsumer;
  public int monitoringPeriodInSeconds;
}
