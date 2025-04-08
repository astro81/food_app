package servlets.user.profile.handlers;

import controller.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Handler for user account deletion operations.
 * <p>
 * Manages the complete removal of user accounts from the system,
 * including session invalidation.
 */
public class DeleteProfileHandler implements ProfileHandler {
    // Notification attributes and messages
    private static final String ATTR_NOTIFICATION = "NOTIFICATION";
    private static final String MSG_DELETE_SUCCESS = "Account deleted successfully";
    private static final String MSG_DELETE_FAILED = "Failed to delete account!";

    // View configuration
    private static final String LOGIN_PAGE = "/user/login";
    private static final String PROFILE_PAGE = "/WEB-INF/user/profile.jsp";

    private final UserDAO userDao;

    /**
     * Constructs a new DeleteProfileHandler with required dependencies.
     *
     * @param userDao the UserDAO instance for database operations
     */
    public DeleteProfileHandler(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Processes account deletion requests.
     * <p>
     * Performs the following operations:
     * 1. Attempts to delete user from database
     * 2. On success: Invalidates session and redirects to login
     * 3. On failure: Returns to profile page with error message
     *
     * @throws SQLException if database access fails
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       UserModel currentUser, HttpSession session)
            throws ServletException, IOException, SQLException {

        // Attempt account deletion
        boolean deleteSuccess = userDao.deleteUser(currentUser.getUserMail());

        if (deleteSuccess) {
            // Successful deletion - terminate session
            session.invalidate();
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE +
                    "?notification=" + MSG_DELETE_SUCCESS);
        } else {
            // Deletion failed - show error
            request.setAttribute(ATTR_NOTIFICATION, MSG_DELETE_FAILED);
            request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
        }
    }
}