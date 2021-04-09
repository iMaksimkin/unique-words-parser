package ru.imaksimkin.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.imaksimkin.clients.MySQLClient;

import java.sql.SQLException;
import java.util.Map;

@Data
@NoArgsConstructor
public class DbUtil {

    private static final Logger logger = LoggerFactory.getLogger(DbUtil.class);

    @SneakyThrows
    public void saveStatistic(Map<String, Integer> parsingResult, String table, MySQLClient mySQLClient) {
        String tableName = mySQLClient.createTable(table.replaceAll("^(http[s]?://)", ""));
        logger.trace(String.format("saving statistics into %s database", tableName));
        parsingResult.forEach((word, frequency) -> {
                    mySQLClient.connect();
                    try {
                        System.out.println(word + " - " + frequency);
                        mySQLClient.insertDataIntoTable(word, String.valueOf(frequency), tableName);
                    } catch (SQLException ex) {
                        logger.error(String.format("Failure while saving %s&%s into database\n%s", word, frequency,
                                ex.getMessage(), ex));
                        ex.printStackTrace();
                    }
                    mySQLClient.disconnect();
                }
        );
    }
}

