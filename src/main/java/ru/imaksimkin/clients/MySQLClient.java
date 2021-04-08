package ru.imaksimkin.clients;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Data
@NoArgsConstructor
public class MySQLClient extends Client {

    private static final String PROPERTY_FILE_PATH = "properties/database.properties";
    private static final String QUERIES_FILE_PATH = "properties/databaseQueries.properties";

    private static final String DATABASE_URL = getProperty(PROPERTY_FILE_PATH, "databaseUrl");
    private static final String USERNAME = getProperty(PROPERTY_FILE_PATH, "username");
    private static final String PASSWORD = getProperty(PROPERTY_FILE_PATH, "password");

    private static final Logger logger = LoggerFactory.getLogger(MySQLClient.class);

    private Connection connection;
    private Properties properties;

    private Statement statement;

    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();

            properties.setProperty("user", USERNAME);

            properties.setProperty("password", PASSWORD);
        }

        return properties;
    }

    /**
     * create Table
     *
     * @param table
     * @return table
     * @throws
     */
    public String createTable(String table) throws SQLException {
        logger.trace("Creating the table if it is not exists...");
        String tableName = table.replaceAll("\\W", "_");
        statement = connection.createStatement();
        try {
            statement.executeUpdate(String.format(getProperty(QUERIES_FILE_PATH, "createTable"), tableName));
        } catch (SQLException ex) {
            logger.error("Failure while crating the table\n" + ex.getMessage(), ex);
        }
        return tableName;
    }

    /**
     * create connection to the database
     *
     * @return connection
     * @throws SQLException
     * @throws java.io.EOFException
     * @throws RuntimeException
     */
    public Connection connect() throws RuntimeException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (SQLException ex) {
                logger.error("Failure while connecting to database\n" + ex.getMessage(), ex);
                ex.printStackTrace();
            }
        }
        if (connection != null) {
            return connection;
        } else {
            logger.error("There is no connection to Database");
            throw new RuntimeException(
                    "There is no connection to Database");
        }
    }

    /**
     * close the connection
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException ex) {
                logger.error("Failure while closing database connection\n" + ex.getMessage(), ex);
                ex.printStackTrace();
            }
        }
    }

    /**
     * custom insert values into database using query
     *
     * @param word
     * @param frequency
     * @param table
     */
    public void insertDataIntoTable(String word, String frequency, String table) throws SQLException {
        statement = connection.createStatement();
        String[] args = new String[]{
                table, word, frequency, frequency
        };
        String query = getProperty(QUERIES_FILE_PATH, "insertValuesIntoTable");
        query = String.format(query, args);
        statement.executeUpdate(query);
    }
}

