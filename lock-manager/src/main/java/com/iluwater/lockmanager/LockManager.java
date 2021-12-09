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

import java.util.HashMap;

/** LockManager class. */
public class LockManager {
  private final HashMap<Long, String> locks;
  private static final HashMap<String, LockManager> managers = new HashMap<>();

  /**
   * Returns the LockManager with the managerName.
   *
   * @param managerName Name of the LockManager that should be found.
   * @return new LockManager object.
   */
  public static synchronized LockManager getLockManager(String managerName) {
    LockManager manager = managers.get(managerName);
    if (manager == null) {
      manager = new LockManager();
      managers.put(managerName, manager);
    }
    return manager;
  }

  public LockManager() {
    locks = new HashMap<>();
  }

  /**
   * Request a lock with the input username and the id from the current manager.
   *
   * @param username Username for the corresponding id.
   * @param id Unique Identifier from the lockable class.
   * @return Boolean value if requesting the lock was successful.
   */
  public boolean requestLock(String username, Long id) {
    if (username == null) {
      return false;
    }
    synchronized (locks) {
      if (!locks.containsKey(id)) {
        locks.put(id, username);
        return true;
      }

      return username.equals(locks.get(id));
    }
  }

  public String releaseLock(Long id) {
    return locks.remove(id);
  }
}
