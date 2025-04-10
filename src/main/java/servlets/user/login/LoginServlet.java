package servlets.user.login;

import java.io.*;
import controller.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import java.sql.SQLException;

/**
 * Servlet implementation for user authentication and session management.
 * <p>
 * This servlet handles both the display of the login form (GET) and processing
 * of login credentials (POST). Key functionalities include:
 * <ul>
 *   <li>Rendering the login interface</li>
 *   <li>Validating user credentials against the database</li>
 *   <li>Establishing secure user sessions</li>
 *   <li>Handling authentication failures and database errors</li>
 * </ul>
 *
 * <p><strong>Security Note:</strong>
 * Implements basic authentication flow but does not include brute-force protection
 * or password complexity requirements.
 */
@WebServlet(
        name = "LoginServlet",
        value = "/user/login",
        description = "Handles user authentication and session creation"
)
public class LoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    // View configuration
    private static final String LOGIN_PAGE = "/WEB-INF/user/login.jsp";
    private static final String PROFILE_PAGE = "/WEB-INF/user/profile.jsp";

    // Request parameter names
    private static final String PARAM_EMAIL = "user_mail";
    private static final String PARAM_PASSWORD = "user_passwd";

    // Session attribute names
    private static final String ATTR_USER = "user";
    private static final String ATTR_NOTIFICATION = "NOTIFICATION";

    // Notification messages
    private static final String MSG_AUTH_FAILED = "Invalid email or password!";
    private static final String MSG_DB_ERROR = "Database Error: ";
    private static final String MSG_GENERIC_ERROR = "Error: ";

    // Dependencies
    private UserDAO userDao;

    /**
     * Initializes the servlet and its data access components.
     * <p>
     * Creates a new UserDAO instance for database operations. This method is
     * guaranteed to be called once before the servlet handles any requests.
     */
    @Override
    public void init() {
        this.userDao = new UserDAO();
    }

    /**
     * Handles HTTP GET requests for login page display.
     * <p>
     * Simply forwards to the login view without any processing. The JSP should
     * handle any notification messages present in the request attributes.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs during view rendering
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
    }

    /**
     * Handles HTTP POST requests containing authentication credentials.
     * <p>
     * Processes the login attempt through these steps:
     * <ol>
     *   <li>Extracts email and password from request parameters</li>
     *   <li>Validates credentials against user database</li>
     *   <li>On success: Creates new session with user attributes</li>
     *   <li>On failure: Returns to login page with error message</li>
     * </ol>
     *
     * @param request  the HttpServletRequest object containing credentials
     * @param response the HttpServletResponse object for redirection
     * @throws ServletException if credential processing fails
     * @throws IOException if redirection or forwarding fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extract credentials from request
        String userMail = request.getParameter(PARAM_EMAIL);
        String userPasswd = request.getParameter(PARAM_PASSWORD);

        try {
            // Attempt authentication
            UserModel user = userDao.loginUser(userMail, userPasswd);

            if (user != null) {
                // Authentication success - create session
                HttpSession session = request.getSession();
                session.setAttribute(ATTR_USER, user);

                // Forward to profile page (consider redirect for POST-REDIRECT-GET)
                request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
            } else {
                // Authentication failure
                request.setAttribute(ATTR_NOTIFICATION, MSG_AUTH_FAILED);
                request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
            }
        } catch (SQLException e) {
            // Database error handling
            request.setAttribute(ATTR_NOTIFICATION, MSG_DB_ERROR + e.getMessage());
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        } catch (Exception e) {
            // Generic error handling
            request.setAttribute(ATTR_NOTIFICATION, MSG_GENERIC_ERROR + e.getMessage());
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        }
    }

    /**
     * Cleans up resources during servlet destruction.
     * <p>
     * Currently no resources require explicit cleanup. Method maintained for
     * future compatibility and to match servlet lifecycle documentation.
     */
    @Override
    public void destroy() {
        // No resources to clean up in current implementation
    }
}