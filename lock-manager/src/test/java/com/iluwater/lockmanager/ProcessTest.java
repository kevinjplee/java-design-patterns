package com.iluwater.lockmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Simple Getter test for the process class.
 */
public class ProcessTest {

  /**
   * Check if uniqueId was assigned correctly and the getter returns the correct value.
   */
  @Test
  void getUniqueIdTest() {
    long tempId = 1;
    Process p1 = new Process(tempId);
    assertEquals(tempId, p1.getUniqueId());
  }
}
