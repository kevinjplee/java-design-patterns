package com.iluwater.embeddedvalue;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import java.sql.SQLException;

public class App{
    private static final String URL = "jdbc:h2:~/embedded-value";
    public static final String CREATE_SCHEMA_SQL =
            "CREATE TABLE IF NOT EXISTS CAKESTORE(balance int, egg int, milk int);";
    public static final String DELETE_SCHEMA_SQL =
            "DROP TABLE CAKESTORE IF EXISTS";
    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) throws Exception {
        final var dataSource = createDataSource();
        createSchema(dataSource);

        final var cs = new CakeStore();
        cs.update(2000, 1, 2);
        cs.updateDB(dataSource);
        deleteSchema(dataSource);
    }

    /**
     * Creates a Schema for the SQL database for initialization.
     *
     * @param dataSource
     * @throws SQLException
     */
    private static void createSchema(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(CREATE_SCHEMA_SQL);
        }
    }

    /**
     * Deletes a created Schema for the SQL database for destruction.
     *
     * @param dataSource
     * @throws SQLException
     */
    private static void deleteSchema(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(DELETE_SCHEMA_SQL);
        }
    }

    /**
     * Creates a DataSource for the connection.
     *
     * @return DataSource
     */
    private static DataSource createDataSource() {
        var dS = new JdbcDataSource();
        dS.setURL(URL);
        return dS;
    }

}