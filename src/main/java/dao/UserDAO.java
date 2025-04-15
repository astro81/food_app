package dao;

import config.DatabaseConnection;
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
    private static final String registerQuery = "INSERT INTO users(user_name, user_mail, user_passwd, user_phone, user_address, user_role) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String loginQuery = "SELECT * FROM users WHERE user_mail = ? AND user_passwd = ?";
    private static final String updateQuery = "UPDATE users SET user_name = ?, user_passwd = ?, user_phone = ?, user_address = ? WHERE user_mail = ?";
    private static final String deleteQuery = "DELETE FROM users WHERE user_mail = ?";

    /**
     * Helper method to create a connection and prepared statement
     */
    private PreparedStatement prepareStatement(String sql) throws SQLException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            return connection.prepareStatement(sql);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }

    /**
     * Helper method to set user parameters on a PreparedStatement
     */
    private void setUserParameters(PreparedStatement pst, UserModel user) throws SQLException {
        pst.setString(1, user.getUserName());
        pst.setString(2, user.getUserMail());
        pst.setString(3, user.getUserPasswd());
        pst.setString(4, user.getUserPhone());
        pst.setString(5, user.getUserAddress());
        pst.setString(6, user.getUserRole());
    }

    /**
     * Helper method to create a UserModel from a ResultSet
     */
    private UserModel mapResultSetToUser(ResultSet rs) throws SQLException {
        return new UserModel(
                rs.getString("user_name"),     // Get username from result
                rs.getString("user_mail"),     // Get email from result
                rs.getString("user_passwd"),   // Get password from result
                rs.getString("user_phone"),    // Get phone from result
                rs.getString("user_address"),  // Get address from result
                rs.getString("user_role")      // Get role from result
        );
    }

    /**
     * Registers a new user in the database.
     *
     * @param user The UserModel object containing user details
     * @return boolean indicating success (true) or failure (false) of registration
     * @throws SQLException if there's a database access error or driver not found
     */
    public boolean registerUser(UserModel user) throws SQLException {
        try (PreparedStatement pst = prepareStatement(registerQuery)) {
            setUserParameters(pst, user);
            return pst.executeUpdate() > 0;
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
        try (PreparedStatement pst = prepareStatement(loginQuery)) {
            pst.setString(1, userMail);
            pst.setString(2, userPasswd);

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? mapResultSetToUser(rs) : null;
            }
        }
    }

    /**
     * Updates user information in the database.
     *
     * @param currentEmail The current email of the user (used to identify the record)
     * @param updatedUser The UserModel object containing updated information
     * @return boolean indicating success (true) or failure (false) of the update
     * @throws SQLException if there's a database access error
     */
    public boolean updateUser(String currentEmail, UserModel updatedUser) throws SQLException {
        try (PreparedStatement pst = prepareStatement(updateQuery)) {
            pst.setString(1, updatedUser.getUserName());
            pst.setString(2, updatedUser.getUserPasswd());
            pst.setString(3, updatedUser.getUserPhone());
            pst.setString(4, updatedUser.getUserAddress());
            pst.setString(5, currentEmail);

            return pst.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param userMail The email of the user to delete
     * @return boolean indicating success (true) or failure (false) of the deletion
     * @throws SQLException if there's a database access error
     */
    public boolean deleteUser(String userMail) throws SQLException {
        try (PreparedStatement pst = prepareStatement(deleteQuery)) {
            pst.setString(1, userMail);
            return pst.executeUpdate() > 0;
        }
    }
}
