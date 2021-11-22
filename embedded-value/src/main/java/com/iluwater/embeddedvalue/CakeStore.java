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
*/

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
 */

package com.iluwater.embeddedvalue;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

/** Implementation of embedded-value. */
public class CakeStore {
  private int balance;
  private final Inventory inventory;

  /** Initailizer for the CakeStore class. */
  public CakeStore() {
    balance = 0;
    inventory = new Inventory();
  }

  /**
   * Loads the private variables with the value from the ResultSet. Calls setter for the inventory
   * variable instead.
   *
   * @param rs created by querying.
   */
  public void load(ResultSet rs) {
    System.out.println("load start");
    try {
      System.out.print("load try");
      balance = rs.getInt("Balance");
      inventory.setEgg(rs.getInt("Egg"));
      inventory.setMilk(rs.getInt("Milk"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * setter for the private variables.
   *
   * @param newBalance new balance value.
   * @param newEgg new egg value.
   * @param newMilk new milk value.
   */
  public void update(int newBalance, int newEgg, int newMilk) {
    setBalance(newBalance);
    inventory.setEgg(newEgg);
    inventory.setMilk(newMilk);
  }

  /**
   * setter for the private variable balance.
   *
   * @param newBalance new Balance value.
   */
  public void setBalance(int newBalance) {
    balance = newBalance;
  }

  /**
   * Getter for private variable balance.
   *
   * @return the current balance.
   */
  public int getBalance() {
    return balance;
  }

  /**
   * Getter for the egg in inventory.
   *
   * @return current egg value.
   */
  public int getEgg() {
    return inventory.getEgg();
  }

  /**
   * Getter for the milk in inventory.
   *
   * @return current milk value.
   */
  public int getMilk() {
    return inventory.getMilk();
  }

  /**
   * Updates the database for Cakestore with the private variable values.
   *
   * @param ds is created beforehand.
   * @return true if the db is updated successfully, false otherwise.
   */
  public boolean updateDB(DataSource ds) {
    try (var connection = ds.getConnection();
        var statement =
            connection.prepareStatement("UPDATE CAKESTORE SET balance = ?, egg = ?, milk = ?")) {
      statement.setInt(1, balance);
      statement.setInt(2, inventory.getEgg());
      statement.setInt(3, inventory.getMilk());
      statement.execute();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
