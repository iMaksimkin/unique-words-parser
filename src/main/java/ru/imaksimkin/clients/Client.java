package ru.imaksimkin.clients;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    /**
     * get property from the file
     *
     * @param filePath
     * @param key
     * @return valueFromProperty
     */
    public static String getProperty(String filePath, String key) {

        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePath);
        String valueFromProperty = "";
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            valueFromProperty = properties.getProperty(key);
        } catch (IOException ex) {
            logger.error("Error occurred at getting property \"" + key + "\" from file: " + filePath + "\n"
                         + ex.getMessage(), ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                logger.error("Failure while closing the stream\n" + ex.getMessage(), ex);
                ex.printStackTrace();
            }
        }
        if (valueFromProperty != null) {
            return valueFromProperty;
        } else {
            throw new NullPointerException(
                    "There is no such property");
        }
    }
}
