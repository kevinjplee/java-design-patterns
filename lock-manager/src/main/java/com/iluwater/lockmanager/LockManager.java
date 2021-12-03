package com.iluwater.lockmanager;

import java.lang.Long;
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

      return (username.equals(locks.get(id)));
    }
  }

  public String releaseLock(Long id) {
    return locks.remove(id);
  }
}
