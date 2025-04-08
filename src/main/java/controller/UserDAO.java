package controller;

import app.config.DatabaseConnection;
import model.UserModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) class for handling user-related database operations.
 * Provides methods for user registration and authentication.
 */
public class UserDAO {

    /**
     * Registers a new user in the database.
     *
     * @param user The UserModel object containing user details
     * @return boolean indicating success (true) or failure (false) of registration
     * @throws SQLException if there's a database access error or driver not found
     */
    public boolean registerUser(UserModel user) throws SQLException {
        // SQL query with parameterized values to prevent SQL injection
        String sqlQuery = "INSERT INTO users(user_name, user_mail, user_passwd, user_phone, user_address) VALUES (?, ?, ?, ?, ?)";

        try {
            // Get database connection from connection pool
            Connection connection = DatabaseConnection.getConnection();

            // Create prepared statement to safely insert user data
            PreparedStatement pst = connection.prepareStatement(sqlQuery);

            // Set parameters for the prepared statement
            pst.setString(1, user.getUserName());      // Set username
            pst.setString(2, user.getUserMail());      // Set email
            pst.setString(3, user.getUserPasswd());    // Set password (should be hashed)
            pst.setString(4, user.getUserPhone());     // Set phone number
            pst.setString(5, user.getUserAddress());   // Set address

            // Execute update and return true if rows were affected
            return pst.executeUpdate() > 0;

        } catch (ClassNotFoundException e) {
            // Wrap ClassNotFoundException in SQLException
            throw new SQLException("Database driver not found", e);
        }
    }

    /**
     * Authenticates a user by checking credentials against the database.
     *
     * @param userMail The user's email address
     * @param userPasswd The user's password (should be hashed before comparison)
     * @return UserModel object if authentication succeeds, null otherwise
     * @throws SQLException if there's a database access error or driver not found
     */
    public UserModel loginUser(String userMail, String userPasswd) throws SQLException {
        // SQL query to find user by email and password
        String sqlQuery = "SELECT * FROM users WHERE user_mail = ? AND user_passwd = ?";

        try {
            // Get database connection
            Connection connection = DatabaseConnection.getConnection();

            // Create prepared statement for secure query
            PreparedStatement pst = connection.prepareStatement(sqlQuery);

            // Set parameters
            pst.setString(1, userMail);      // Set email parameter
            pst.setString(2, userPasswd);    // Set password parameter

            // Execute query and get result set
            ResultSet rs = pst.executeQuery();

            // If user exists, create and return UserModel object
            if (rs.next()) {
                return new UserModel(
                        rs.getString("user_name"),     // Get username from result
                        rs.getString("user_mail"),     // Get email from result
                        rs.getString("user_passwd"),   // Get password from result
                        rs.getString("user_phone"),    // Get phone from result
                        rs.getString("user_address")   // Get address from result
                );
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database Driver not found", e);
        }

        // Return null if no matching user found
        return null;
    }
}
