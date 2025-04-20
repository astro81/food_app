package dao.helpers;

import model.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOHelpers {
    /**
     * Helper method to set user parameters on a PreparedStatement
     */
    public static void setUserParameters(PreparedStatement pst, UserModel user) throws SQLException {
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
    public static UserModel mapResultSetToUser(ResultSet rs) throws SQLException {
        return new UserModel(
                rs.getInt("user_id"),
                rs.getString("user_name"),     // Get username from result
                rs.getString("user_mail"),     // Get email from result
                rs.getString("user_passwd"),   // Get password from result
                rs.getString("user_phone"),    // Get phone from result
                rs.getString("user_address"),  // Get address from result
                rs.getString("user_role")      // Get role from result
        );
    }
    /**
     * Hashes a plain text password using BCrypt.
     *
     * @param plainPassword The plain password to hash
     * @return The hashed password string
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Verifies a plain text password against a hashed password.
     *
     * @param plainPassword The plain password entered by user
     * @param hashedPassword The hashed password stored in DB
     * @return true if the passwords match, false otherwise
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
