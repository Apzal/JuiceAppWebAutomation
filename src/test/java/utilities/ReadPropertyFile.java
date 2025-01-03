package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {
    Properties properties;
    Logger logger = LogManager.getLogger(ReadPropertyFile.class);

    public ReadPropertyFile() {
        properties = new Properties();
        String path = System.getProperty("user.dir") + "\\src\\test\\resources\\";
        try (FileInputStream inputStream = new FileInputStream(path + "config.properties")) {
            properties.load(inputStream);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String readProperty(String propertyName) {
        if (properties == null)
            new ReadPropertyFile();
        return properties.getProperty(propertyName);
    }
}
