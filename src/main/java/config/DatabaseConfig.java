package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final String PROPERTIES_FILE = "config.properties";
    private static final Properties properties = loadProperties();

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
            }
            props.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Error loading configuration", ex);
        }
        return props;
    }

    public static String getDriver() {
        String driver = properties.getProperty("db.driver");
        if (driver == null || driver.isBlank()) {
            throw new RuntimeException("Database Driver is not configured");
        }
        return driver;
    }

    public static String getDbUrl() {
        String url = properties.getProperty("db.url");
        if (url == null || url.isBlank()) {
            throw new RuntimeException("Database URL not configured");
        }
        return url;
    }

    public static String getDbUsername() {
        String username = properties.getProperty("db.username");
        if (username == null || username.isBlank()) {
            throw new RuntimeException("Username is not Provided");
        }
        return username;
    }

    public static String getDbPassword() {
        String password = properties.getProperty("db.password");
        if (password == null || password.isBlank()) {
            throw new RuntimeException("Password is not Provided");
        }
        return password;
    }
}