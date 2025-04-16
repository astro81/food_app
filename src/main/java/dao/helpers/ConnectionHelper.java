package dao.helpers;

import config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionHelper {
    /**
     * Helper method to create a connection and prepared statement
     */
    public static PreparedStatement prepareStatement(String sql) throws SQLException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            return connection.prepareStatement(sql);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }
}
