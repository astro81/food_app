package servlets.user.handlers;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.UserModel;
import org.mindrot.jbcrypt.BCrypt;
import servlets.user.UserConstant;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Handler for user profile update operations.
 * <p>
 * Responsible for processing requests to modify user profile information,
 * including name, contact details, and password.
 */
public class UpdateProfileHandler implements ProfileHandler {

    private final UserDAO userDao;

    /**
     * Constructs a new UpdateProfileHandler with required dependencies.
     *
     * @param userDao the UserDAO instance for database operations
     */
    public UpdateProfileHandler(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Processes profile update requests.
     * <p>
     * Handles the following updates:
     * - Basic information (name, phone, address)
     * - Password (optional, only updated if new value provided)
     *
     * @throws SQLException if database access fails
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       UserModel currentUser, HttpSession session)
            throws ServletException, IOException, SQLException {

        // Extract parameters from request
        String userName = request.getParameter(UserConstant.PARAM_NAME);
        String userPhone = request.getParameter(UserConstant.PARAM_PHONE);
        String userAddress = request.getParameter(UserConstant.PARAM_ADDRESS);
        String userPasswd = request.getParameter(UserConstant.PARAM_PASSWORD);

        // Create updated user model with validated data
        UserModel updatedUser = createUpdatedUserModel(currentUser, userName, userPhone, userAddress, userPasswd);

        // Attempt database update
        boolean updateSuccess = userDao.updateUserInfo(currentUser.getUserMail(), updatedUser);

        if (updateSuccess) {
            // Update session with new user data
            updatedUser.setProfilePicture(currentUser.getProfilePicture()); // Keep the existing profile picture
            session.setAttribute(UserConstant.ATTR_USER, updatedUser);
            request.setAttribute(UserConstant.MSG_NOTIFICATION, UserConstant.MSG_UPDATE_SUCCESS);
        } else {
            request.setAttribute(UserConstant.MSG_NOTIFICATION, UserConstant.MSG_UPDATE_FAILED);
        }

        // Return to profile view
        request.getRequestDispatcher(UserConstant.PROFILE_PAGE).forward(request, response);
    }

    /**
     * Creates an updated UserModel instance with validated data.
     *
     * @param currentUser the original user data
     * @param userName new name (null retains current)
     * @param userPhone new phone (null retains current)
     * @param userAddress new address (null retains current)
     * @param userPasswd new password (null or empty retains current)
     * @return updated UserModel instance
     */
    private UserModel createUpdatedUserModel(UserModel currentUser, String userName, String userPhone, String userAddress, String userPasswd) {
        // Handle password hashing if password is being changed else password remains unchanged
        String hashedPasswd = isPasswordChanged(userPasswd)
                ? BCrypt.hashpw(userPasswd, BCrypt.gensalt())
                : currentUser.getUserPasswd();

        UserModel updatedUser = new UserModel(
                currentUser.getUserId(),
                userName != null ? userName : currentUser.getUserName(),
                currentUser.getUserMail(),
                hashedPasswd,
                userPhone != null ? userPhone : currentUser.getUserPhone(),
                userAddress != null ? userAddress : currentUser.getUserAddress(),
                currentUser.getUserRole(),
                currentUser.getProfilePicture()
        );

        return updatedUser;
    }

    /**
     * Determines if password should be updated.
     *
     * @param newPassword the password value from request
     * @return true if password should be updated (not null and not empty)
     */
    private boolean isPasswordChanged(String newPassword) {
        return newPassword != null && !newPassword.trim().isEmpty();
    }
}