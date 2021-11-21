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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.h2.jdbcx.JdbcDataSource;
import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CakeStoreTest {
  static CakeStore cakestore;
  DataSource dataSource;
  private static final String URL = "jdbc:h2:~/embedded-value";
  public static final String CREATE_SCHEMA_SQL =
      "CREATE TABLE IF NOT EXISTS CAKESTORE(balance int, egg int, milk int);";
  public static final String DELETE_SCHEMA_SQL = "DROP TABLE CAKESTORE IF EXISTS";
  public static final String INSERT_VALUE_SQL = "INSERT INTO CAKESTORE VALUES(0, 0, 0)";

  /**
   * Sets up variables and the database for the later tests.
   *
   * @throws SQLException
   */
  @BeforeEach
  void initialize() throws SQLException {
    cakestore = new CakeStore();
    var dS = new JdbcDataSource();
    dS.setURL(URL);
    dataSource = dS;
    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement()) {
      statement.execute(CREATE_SCHEMA_SQL);
      var statement2 = connection.prepareStatement("SELECT * FROM CAKESTORE");
      ResultSet rs = statement2.executeQuery();
      if (!rs.next()) {
        statement.execute(INSERT_VALUE_SQL);
      }
      rs = statement2.executeQuery();
      assertEquals(true, rs.next());
    }
  }

  /** Checks the update function of cakestore. */
  @Test
  public void updateTest() {
    int newBalance = 3000;
    int newEgg = 10;
    int newMilk = 15;
    cakestore.update(newBalance, newEgg, newMilk);
    assertEquals(cakestore.getBalance(), newBalance);
    assertEquals(cakestore.getInventory().getEgg(), newEgg);
    assertEquals(cakestore.getInventory().getMilk(), newMilk);
  }

  /** Tests the situation where the connection has been successful. */
  @Nested
  public class connectionSuccess {
    /** Test the update DB function by querying the updated value. */
    @BeforeEach
    void initialize() throws SQLException {
      cakestore = new CakeStore();
      var dS = new JdbcDataSource();
      dS.setURL(URL);
      dataSource = dS;
      try (var connection = dataSource.getConnection();
          var statement = connection.createStatement()) {
        statement.execute(CREATE_SCHEMA_SQL);
        var statement2 = connection.prepareStatement("SELECT * FROM CAKESTORE");
        ResultSet rs = statement2.executeQuery();
        if (!rs.next()) {
          statement.execute(INSERT_VALUE_SQL);
        }
        rs = statement2.executeQuery();
        assertEquals(true, rs.next());
      }
    }

    @Test
    public void updateDBTest() {
      int newBalance = 3000;
      int newEgg = 10;
      int newMilk = 15;
      cakestore.update(newBalance, newEgg, newMilk);
      assertEquals(cakestore.updateDB(dataSource), true);

      try (var connection = dataSource.getConnection();
          var statement = connection.prepareStatement("SELECT * FROM CAKESTORE")) {
        ResultSet rs = statement.executeQuery();
        assertEquals(true, rs.next());
        assertEquals(rs.getInt("balance"), newBalance);
        assertEquals(rs.getInt("egg"), newEgg);
        assertEquals(rs.getInt("milk"), newMilk);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    /** Check if the load function successfully stores the appropriate value. */
    @Test
    public void loadTest() {
      int newBalance = 3000;
      int newEgg = 10;
      int newMilk = 15;
      int tempBalance = 5000;
      int tempEgg = 5;
      int tempMilk = 8;
      cakestore.update(tempBalance, tempEgg, tempMilk);

      assertEquals(cakestore.getBalance(), tempBalance);
      assertEquals(cakestore.getInventory().getEgg(), tempEgg);
      assertEquals(cakestore.getInventory().getMilk(), tempMilk);
      try (var connection = dataSource.getConnection();
          var statement = connection.prepareStatement("SELECT * FROM CAKESTORE")) {
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
          cakestore.load(rs);
          assertEquals(newBalance, cakestore.getBalance());
          assertEquals(newEgg, cakestore.getInventory().getEgg());
          assertEquals(newMilk, cakestore.getInventory().getMilk());
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
