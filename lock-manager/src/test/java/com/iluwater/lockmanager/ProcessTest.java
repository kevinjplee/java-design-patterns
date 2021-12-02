package com.iluwater.lockmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ProcessTest {
  @Test
  void getUniqueIdTest() {
    long tempId = 1;
    Process p1 = new Process(tempId);
    assertEquals(tempId, p1.getUniqueId());
  }
}
