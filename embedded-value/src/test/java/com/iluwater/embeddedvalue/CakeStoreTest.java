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
    public static final String DELETE_SCHEMA_SQL =
            "DROP TABLE CAKESTORE IF EXISTS";
    public static final String INSERT_VALUE_SQL =
            "INSERT INTO CAKESTORE VALUES(0, 0, 0)";
    @BeforeEach
    void initialize() throws SQLException
    {
        cakestore = new CakeStore();
        var dS = new JdbcDataSource();
        dS.setURL(URL);
        dataSource = dS;
        try (var connection =dataSource.getConnection();
             var statement = connection.createStatement();
             var statement2 = connection.prepareStatement("SELECT * FROM CAKESTORE")) {
            statement.execute(CREATE_SCHEMA_SQL);
            ResultSet rs = statement2.executeQuery();
            if(!rs.next())
            {
                statement.execute(INSERT_VALUE_SQL);
            }
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
                assertEquals(true, rs.next());
                assertEquals(rs.getInt("balance"), newBalance);
                assertEquals(rs.getInt("egg"), newEgg);
                assertEquals(rs.getInt("milk"), newMilk);
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
