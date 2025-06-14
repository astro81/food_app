package dao;

import dao.helpers.UserDAOHelpers;
import model.OrderModel;
import model.UserModel;
import servlets.user.UserConstant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.helpers.ConnectionHelper.prepareStatement;
import static dao.helpers.UserDAOHelpers.*;

/**
 * Data Access Object (DAO) class for handling user-related database operations.
 * Provides methods for user registration and authentication.
 */
public class UserDAO {
    private static final String REGISTER_QUERY = "INSERT INTO users(user_name, user_mail, user_passwd, user_phone, user_address, user_role) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String LOGIN_QUERY = "SELECT * FROM users WHERE user_mail = ?";
    private static final String UPDATE_QUERY = "UPDATE users SET user_name = ?, user_passwd = ?, user_phone = ?, user_address = ? WHERE user_mail = ?";
    private static final String UPDATE_PROFILE_PICTURE_QUERY = "UPDATE users SET profile_picture = ? WHERE user_mail = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE user_id = ?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String UPDATE_ROLE_QUERY = "UPDATE users SET user_role = ? WHERE user_id = ?";
    private static final String GET_ORDER_HISTORY_QUERY =
            "SELECT o.order_id, o.order_date, o.total, o.status " +
                    "FROM orders o " +
                    "WHERE o.user_id = ? AND o.status = 'confirmed' " +
                    "ORDER BY o.order_date DESC";
    private static final String GET_USER_COUNT_QUERY = "SELECT COUNT(*) AS count FROM users";
    private static final String GET_VENDOR_COUNT_QUERY = "SELECT COUNT(*) AS count FROM users WHERE user_role = ?";
    private static final String STORE_REMEMBER_TOKEN_QUERY = "INSERT INTO remember_tokens(user_id, token, expiry_date) VALUES (?, ?, ?)";
    private static final String DELETE_REMEMBER_TOKEN_QUERY = "DELETE FROM remember_tokens WHERE token = ?";
    private static final String GET_USER_BY_TOKEN_QUERY = "SELECT u.* FROM users u JOIN remember_tokens rt ON u.user_id = rt.user_id WHERE rt.token = ? AND rt.expiry_date > NOW()";

    /**
     * Registers a new user in the database.
     *
     * @param user The UserModel object containing user details
     * @return boolean indicating success (true) or failure (false) of registration
     * @throws SQLException if there's a database access error or driver not found
     */
    public boolean registerUser(UserModel user) throws SQLException {
        try (PreparedStatement pst = prepareStatement(REGISTER_QUERY)) {
            setUserParameters(pst, user);
            return pst.executeUpdate() > 0;
        }
    }

    /**
     * Authenticates a user by checking credentials against the database.
     *
     * @param userMail The user's email address
     * @return UserModel object if authentication succeeds, null otherwise
     * @throws SQLException if there's a database access error or driver not found
     */
    public UserModel loginUser(String userMail) throws SQLException {
        try (PreparedStatement pst = prepareStatement(LOGIN_QUERY)) {
            pst.setString(1, userMail);

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
    public boolean updateUserInfo(String currentEmail, UserModel updatedUser) throws SQLException {
            // Update without profile picture
        try (PreparedStatement pst = prepareStatement(UPDATE_QUERY)) {
            pst.setString(1, updatedUser.getUserName());
            pst.setString(2, updatedUser.getUserPasswd());
            pst.setString(3, updatedUser.getUserPhone());
            pst.setString(4, updatedUser.getUserAddress());
            pst.setString(5, currentEmail);
            return pst.executeUpdate() > 0;
        }
    }

    public boolean updateProfilePicture(String userMail, byte[] profilePicture) throws SQLException {
        try (PreparedStatement pst = prepareStatement(UPDATE_PROFILE_PICTURE_QUERY)) {
            pst.setBytes(1, profilePicture);
            pst.setString(2, userMail);
            return pst.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId The email of the user to delete
     * @return boolean indicating success (true) or failure (false) of the deletion
     * @throws SQLException if there's a database access error
     */
    public boolean deleteUser(int userId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(DELETE_QUERY)) {
            pst.setInt(1, userId);
            return pst.executeUpdate() > 0;
        }
    }

    public List<UserModel> getAllUsers() throws SQLException {
        List<UserModel> users = new ArrayList<>();
        try (PreparedStatement pst = prepareStatement(GET_ALL_USERS_QUERY);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                users.add(UserDAOHelpers.mapResultSetToUser(rs));
            }
        }
        return users;
    }

    public boolean updateUserRole(int userId, String newRole) throws SQLException {
        try (PreparedStatement pst = prepareStatement(UPDATE_ROLE_QUERY)) {
            pst.setString(1, newRole);
            pst.setInt(2, userId);
            return pst.executeUpdate() > 0;
        }
    }

    public static List<OrderModel> getOrderHistory(int userId) throws SQLException {
        List<OrderModel> orders = new ArrayList<>();
        try (PreparedStatement pst = prepareStatement(GET_ORDER_HISTORY_QUERY)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    OrderModel order = new OrderModel();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setTotal(rs.getBigDecimal("total"));
                    order.setStatus(rs.getString("status"));
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public int getUserCount() throws SQLException {
        int count = 0;
        try (PreparedStatement pst = prepareStatement(GET_USER_COUNT_QUERY);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt("count");
            }
        }

        return count;
    }

    public int getVendorCount() throws SQLException {
        int count = 0;
        try (PreparedStatement pst = prepareStatement(GET_VENDOR_COUNT_QUERY)) {

            pst.setString(1, UserConstant.ROLE_VENDOR);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            }
        }

        return count;
    }

    public void storeRememberToken(int userId, String token, java.sql.Timestamp expiry) throws SQLException {
        try (PreparedStatement pst = prepareStatement(STORE_REMEMBER_TOKEN_QUERY)) {
            pst.setInt(1, userId);
            pst.setString(2, token);
            pst.setTimestamp(3, expiry);
            pst.executeUpdate();
        }
    }

    public void deleteRememberToken(String token) throws SQLException {
        try (PreparedStatement pst = prepareStatement(DELETE_REMEMBER_TOKEN_QUERY)) {
            pst.setString(1, token);
            pst.executeUpdate();
        }
    }

    public UserModel getUserByRememberToken(String token) throws SQLException {
        try (PreparedStatement pst = prepareStatement(GET_USER_BY_TOKEN_QUERY)) {
            pst.setString(1, token);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? mapResultSetToUser(rs) : null;
            }
        }
    }
}
