package servlets.user.handlers;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Handler for user account deletion operations.
 * <p>
 * Manages the complete removal of user accounts from the system,
 * including session invalidation.
 */
public class DeleteProfileHandler implements ProfileHandler {

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
            response.sendRedirect(request.getContextPath() + UserConstant.LOGIN_PATH +
                    "?notification=" + UserConstant.MSG_DELETE_SUCCESS);
        } else {
            // Deletion failed - show error
            request.setAttribute(UserConstant.MSG_NOTIFICATION, UserConstant.MSG_DELETE_FAILED);
            request.getRequestDispatcher(UserConstant.PROFILE_PAGE).forward(request, response);
        }
    }
}