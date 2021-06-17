package io.swisschain.http_handlers.responses.settings;

public class TaskSettings {
  private int periodInSeconds;
  private int queueSize;
  private int threadsCount;

  public TaskSettings(int periodInSeconds, int queueSize, int threadsCount) {
    this.periodInSeconds = periodInSeconds;
    this.queueSize = queueSize;
    this.threadsCount = threadsCount;
  }

  public int getPeriodInSeconds() {
    return periodInSeconds;
  }

  public int getQueueSize() {
    return queueSize;
  }

  public int getThreadsCount() {
    return threadsCount;
  }
}
