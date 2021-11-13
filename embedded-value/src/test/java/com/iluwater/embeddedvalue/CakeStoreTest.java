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
    CakeStore cakestore;
    DataSource dataSource;
    private static final String URL = "jdbc:h2:~/embedded-value";
    public static final String CREATE_SCHEMA_SQL =
            "CREATE TABLE IF NOT EXISTS CAKESTORE(balance int, egg int, milk int);";
    public static final String DELETE_SCHEMA_SQL =
            "DROP TABLE CAKESTORE IF EXISTS";
    @BeforeEach
    void initialize() throws SQLException
    {
        cakestore = new CakeStore();
        var dS = new JdbcDataSource();
        dS.setURL(URL);
        dataSource = dS;
        try (var connection = DriverManager.getConnection(URL);
             var statement = connection.createStatement()) {
            statement.execute(CREATE_SCHEMA_SQL);
        }
    }

    @Test
    public void updateTest()
    {
        int newBalance = 3000;
        int newEgg = 10;
        int newMilk = 15;
        cakestore.update(newBalance, newEgg, newMilk);
        assertEquals(cakestore.getBalance(), newBalance);
        assertEquals(cakestore.getInventory().getEgg(), newEgg);
        assertEquals(cakestore.getInventory().getMilk(), newMilk);
    }

    @Nested
    public class connectionSuccess{
        @Test
        public void updateDBTest()
        {
            int newBalance = 3000;
            int newEgg = 10;
            int newMilk = 15;
            cakestore.update(newBalance, newEgg, newMilk);
            assertEquals(cakestore.updateDB(dataSource), true);


            try (var connection = dataSource.getConnection();
                 var statement = connection.prepareStatement("SELECT * FROM CAKESTORE")) {
                ResultSet rs = statement.executeQuery();
                if(rs.next())
                {
                    assertEquals(rs.getInt("balance"), newBalance);
                    assertEquals(rs.getInt("egg"), newEgg);
                    assertEquals(rs.getInt("milk"), newMilk);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Test
        public void loadTest()
        {
            int newBalance = 3000;
            int newEgg = 10;
            int newMilk = 15;
            cakestore.update(5000, 5, 10);
            try (var connection = dataSource.getConnection();
                 var statement = connection.prepareStatement("SELECT * FROM CAKESTORE")) {
                ResultSet rs = statement.executeQuery();
                if(rs.next())
                {
                    cakestore.load(rs);
                    assertEquals(cakestore.getBalance(), newBalance);
                    assertEquals(cakestore.getInventory().getEgg(), newEgg);
                    assertEquals(cakestore.getInventory().getMilk(), newMilk);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
