package com.iluwater.lockmanager;

/** Process is a dummy class used to show that different objects are acquiring the lock. */
public class Process {
  private final Long uniqueId;

  public Process(long id) {
    uniqueId = id;
  }

  public Long getUniqueId() {
    return uniqueId;
  }
}
