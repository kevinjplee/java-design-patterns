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
* CS427 Issue link: https://github.com/iluwatar/java-design-patterns/issues/1295
*/


package com.iluwater.embeddedvalue;

public class Inventory {
  private static int egg;
  private static int milk;

  /** Initializes the inventory private variables. */
  public Inventory() {
    setEgg(1);
    setMilk(1);
  }

  /**
   * Getter for Egg.
   *
   * @return egg which is the current egg value.
   */
  public int getEgg() {
    return egg;
  }

  /**
   * Setting for Egg.
   *
   * @param amount the new egg value.
   */
  public static void setEgg(int amount) {
    if (amount >= 0) {
      egg = amount;
    }
  }

  /**
   * Getter for Milk.
   *
   * @return the current milk value.
   */
  public int getMilk() {
    return milk;
  }

  /**
   * Setter for Milk.
   *
   * @param amount the new milk value.
   */
  public static void setMilk(int amount) {
    if (amount >= 0) {
      milk = amount;
    }
  }
}
