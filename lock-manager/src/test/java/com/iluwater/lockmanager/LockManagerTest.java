package com.iluwater.lockmanager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/** Test for LockManager class. */
public class LockManagerTest {

  /** Checks if the LockManager object is returned correctly. */
  @Test
  void getLockManagerTest() {
    LockManager compManager = LockManager.getLockManager("comp");
    assertNotEquals(null, compManager);
  }

  /** Tests if the lock was set up correctly with the uniqueId and the string value. */
  @Test
  void requestLockTest() {
    long tempId = 1;
    LockManager compManager = LockManager.getLockManager("comp");
    assertFalse(compManager.requestLock(null, tempId));
    assertTrue(compManager.requestLock("comp", tempId));
    assertFalse(compManager.requestLock("wrong", tempId));
    assertTrue(compManager.requestLock("comp", tempId));
  }

  /** Tests if the lock with the uniqueId is released correctly. */
  @Test
  void releaseLockTest() {
    long tempId = 2;
    LockManager compManager = LockManager.getLockManager("comp");
    compManager.requestLock("comp", tempId);
    compManager.releaseLock(tempId);
    assertTrue(compManager.requestLock("right", tempId));
  }
}
