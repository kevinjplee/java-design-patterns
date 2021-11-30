package com.iluwater.lockmanager;

import java.lang.Long;
import java.util.HashMap;

/**
 *  LockManager class.
 */
public class LockManager {
  private final HashMap<Long, String> locks;
  private static final HashMap<String, LockManager> managers = new HashMap<>();


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
