// New ProfilePictureHandler.java
package servlets.user.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.UserModel;
import dao.UserDAO;
import servlets.user.UserConstant;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Handler for user profile picture update operations.
 * <p>
 * Responsible for processing requests to modify user profile pictures.
 */
public class ProfilePictureHandler {

    private final UserDAO userDao;

    /**
     * Constructs a new ProfilePictureHandler with required dependencies.
     *
     * @param userDao the UserDAO instance for database operations
     */
    public ProfilePictureHandler(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Processes profile picture update requests.
     * <p>
     * Handles the uploading and updating of user profile pictures
     *
     * @throws SQLException if database access fails
     */
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       UserModel currentUser, HttpSession session)
            throws ServletException, IOException, SQLException {

        // Handle file upload
        Part filePart = request.getPart("profilePicture");
        byte[] profilePicture = null;

        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream fileContent = filePart.getInputStream()) {
                profilePicture = fileContent.readAllBytes();
            }
        } else {
            request.setAttribute(UserConstant.MSG_NOTIFICATION, "No profile picture was selected.");
            request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_ERROR);
            request.getRequestDispatcher(UserConstant.PROFILE_PAGE).forward(request, response);
            return;
        }

        // Create updated user model with the new profile picture
        UserModel updatedUser = createUserWithUpdatedPicture(currentUser, profilePicture);

        // Attempt database update
        boolean updateSuccess = userDao.updateProfilePicture(currentUser.getUserMail(), profilePicture);

        if (updateSuccess) {
            // Update session with new user data
            session.setAttribute(UserConstant.ATTR_USER, updatedUser);
            request.setAttribute(UserConstant.MSG_NOTIFICATION, "Profile picture updated successfully.");
            request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_SUCCESS);
        } else {
            request.setAttribute(UserConstant.MSG_NOTIFICATION, "Failed to update profile picture.");
            request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_ERROR);
        }

        // Return to profile view
        response.sendRedirect(request.getContextPath() + "/user/profile");
    }

    /**
     * Creates a UserModel with the updated profile picture.
     *
     * @param currentUser the original user data
     * @param profilePicture the new profile picture data
     * @return updated UserModel instance
     */
    private UserModel createUserWithUpdatedPicture(UserModel currentUser, byte[] profilePicture) {
        return new UserModel(
                currentUser.getUserId(),
                currentUser.getUserName(),
                currentUser.getUserMail(),
                currentUser.getUserPasswd(),
                currentUser.getUserPhone(),
                currentUser.getUserAddress(),
                currentUser.getUserRole(),
                profilePicture
        );
    }
}