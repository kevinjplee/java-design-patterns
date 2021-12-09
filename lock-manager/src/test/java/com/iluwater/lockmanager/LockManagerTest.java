/*
*The MIT License
*Copyright © 2014-2021 Ilkka Seppälä
*
*Permission is hereby granted, free of charge, to any person obtaining a copy
*of this software and associated documentation files (the "Software"), to deal
*in the Software without restriction, including without limitation the rights
*to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*copies of the Software, and to permit persons to whom the Software is
*furnished to do so, subject to the following conditions:
*
*The above copyright notice and this permission notice shall be included in
*all copies or substantial portions of the Software.
*
*THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
*IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
*FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
*AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
*LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
*THE SOFTWARE.
*
* CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1283
*/

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
