package com.iluwater.lockmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class LockManagerTest {

  @Test
  void getLockManagerTest() {
    LockManager compManager = LockManager.getLockManager("comp");
    assertNotEquals(null, compManager);
  }

  @Test
  void requestLockTest() {
    long tempId = 1;
    LockManager compManager = LockManager.getLockManager("comp");
    assertEquals(false, compManager.requestLock(null, tempId));
    assertEquals(true, compManager.requestLock("comp", tempId));
    assertEquals(false, compManager.requestLock("wrong", tempId));
    assertEquals(true, compManager.requestLock("comp", tempId));
  }

  @Test
  void releaseLockTest() {
    long tempId = 2;
    LockManager compManager = LockManager.getLockManager("comp");
    compManager.requestLock("comp", tempId);
    compManager.releaseLock(tempId);
    assertEquals(true, compManager.requestLock("right", tempId));
  }
}
