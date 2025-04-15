package TestDBConnection;

import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMariaDBConnection {

    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        try {
            openDatabaseConnection();
        } finally {
            closeDatabaseConnection();
        }
    }

    private static void openDatabaseConnection() throws SQLException {
        String url = DatabaseConfig.getDbUrl();
        String username = DatabaseConfig.getDbUsername();
        String password = DatabaseConfig.getDbPassword();

        System.out.println("Connecting to database");

        connection = DriverManager.getConnection(
                url,
                username,
                password
        );
//        if( true ) throw new  RuntimeException("Simulated error!");
        System.out.println("Connection valid: " + connection.isValid(5));
    }


    private static void closeDatabaseConnection() throws SQLException {
        System.out.println("Closing database connection...");
        connection.close();
        System.out.println("Connection valid: " + connection.isValid(5));
    }
}

