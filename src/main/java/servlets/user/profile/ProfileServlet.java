package servlets.user.profile;

import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import controller.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import servlets.user.profile.handlers.DeleteProfileHandler;
import servlets.user.profile.handlers.ProfileHandler;
import servlets.user.profile.handlers.UpdateProfileHandler;

/**
 * Main servlet for handling user profile operations.
 * <p>
 * This servlet acts as a controller that routes requests to appropriate handlers
 * based on the action parameter. It supports:
 * <ul>
 *   <li>GET requests for viewing profile information</li>
 *   <li>POST requests with 'update' action for profile modifications</li>
 *   <li>POST requests with 'delete' action for account deletion</li>
 * </ul>
 *
 * <p><strong>Security Note:</strong>
 * All operations require an active session with authenticated user.
 */
@WebServlet(
        name = "ProfileServlet",
        value = "/user/profile",
        description = "Controller for user profile operations"
)
public class ProfileServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    // View configuration
    private static final String PROFILE_PAGE = "/WEB-INF/user/profile.jsp";
    private static final String LOGIN_PAGE = "/user/login";

    // Request parameter and attribute names
    private static final String PARAM_ACTION = "action";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_DELETE = "delete";
    private static final String ATTR_USER = "user";

    // Notification messages
    private static final String MSG_SESSION_EXPIRED = "Session expired. Please login again.";
    private static final String MSG_INVALID_ACTION = "Invalid action requested!";
    private static final String MSG_DB_ERROR = "Database error occurred. Please try again later.";

    // Dependencies
    private UserDAO userDao;
    private Map<String, ProfileHandler> handlers;

    /**
     * Initializes the servlet and its handler mappings.
     * <p>
     * Creates a new UserDAO instance and registers handler implementations
     * for supported actions.
     */
    @Override
    public void init() {
        this.userDao = new UserDAO();
        this.handlers = new HashMap<>();
        this.handlers.put(ACTION_UPDATE, new UpdateProfileHandler(userDao));
        this.handlers.put(ACTION_DELETE, new DeleteProfileHandler(userDao));
    }

    /**
     * Handles HTTP GET requests for profile viewing.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
    }

    /**
     * Handles HTTP POST requests for profile modifications.
     * <p>
     * Routes requests to appropriate handlers based on the 'action' parameter.
     * Valid actions are 'update' and 'delete'. All other actions result in
     * an error notification.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Verify user authentication
        if (!isUserAuthenticated(session)) {
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE +
                    "?error=" + MSG_SESSION_EXPIRED);
            return;
        }

        UserModel currentUser = (UserModel) session.getAttribute(ATTR_USER);
        String action = request.getParameter(PARAM_ACTION);

        try {
            // Route to appropriate handler
            ProfileHandler handler = handlers.get(action);
            if (handler != null) {
                handler.handle(request, response, currentUser, session);
            } else {
                request.setAttribute("NOTIFICATION", MSG_INVALID_ACTION);
                request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
            }
        } catch (SQLException e) {
            // Handle database errors
            e.printStackTrace();
            request.setAttribute("NOTIFICATION", MSG_DB_ERROR + ": " + e.getMessage());
            request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
        }
    }

    /**
     * Validates user authentication status.
     *
     * @param session the current HttpSession
     * @return true if session exists and contains a valid user object
     */
    private boolean isUserAuthenticated(HttpSession session) {
        return session != null && session.getAttribute(ATTR_USER) != null;
    }

    /**
     * Cleans up resources when servlet is destroyed.
     */
    @Override
    public void destroy() {
        // Currently no resources to clean up
    }
}