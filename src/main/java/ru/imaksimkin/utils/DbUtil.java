package ru.imaksimkin.utils;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imaksimkin.clients.MySQLClient;

import java.sql.SQLException;
import java.util.Map;

public class DbUtil {

    private static final Logger logger = LoggerFactory.getLogger(DbUtil.class);

    @SneakyThrows
    public void saveStatistic(Map<String, String> parsingResult, String table, MySQLClient mySQLClient) {
        logger.trace("Creating the table...");
        String tableName = mySQLClient.createTable(table.replaceAll("^(http[s]?://)", ""));
        logger.trace("saving into " + tableName);
        parsingResult.forEach((word, frequency) -> {
                    mySQLClient.connect();
                    logger.trace(String.format("saving %s with frequecy %s", word, frequency));
                    try {
                        System.out.println(word + " - " + frequency);
                        mySQLClient.insertDataIntoTable(word, frequency, tableName);
                    } catch (SQLException ex) {
                        logger.error("Failure while saving " + word + "&" + frequency + " into database\n" + ex.getMessage(),
                                ex);
                        ex.printStackTrace();
                    }
                    mySQLClient.disconnect();
                }
        );
    }
}

