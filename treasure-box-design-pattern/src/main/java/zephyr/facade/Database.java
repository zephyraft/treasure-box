package zephyr.facade;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Database {

    private Database() {
    }

    public static Properties getProperties(String dbname) {
        String fileName = dbname + ".txt";
        Properties properties = new Properties();
        try {
            properties.load(Database.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return properties;
    }
}
