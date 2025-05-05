package servlets.user;

import java.io.*;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import org.mindrot.jbcrypt.BCrypt;

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
        request.getRequestDispatcher(UserConstant.LOGIN_PAGE).forward(request, response);
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
        String userMail = request.getParameter(UserConstant.PARAM_EMAIL);
        String userPasswd = request.getParameter(UserConstant.PARAM_PASSWORD);

        try {
            // Attempt authentication
//            UserModel user = userDao.loginUser(userMail, userPasswd);
            UserModel user = userDao.loginUser(userMail);

            if (user != null && BCrypt.checkpw(userPasswd, user.getUserPasswd())) {
                // Authentication success - create session
                HttpSession session = request.getSession();
                session.setAttribute(UserConstant.ATTR_USER, user);

                // Forward to profile page (consider redirect for POST-REDIRECT-GET)
//                request.getRequestDispatcher(UserConstant.PROFILE_PAGE).forward(request, response);
                // Redirect based on role
                if (user.getUserRole().equals("admin")) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else if (user.getUserRole().equals("vendor")) {
                    response.sendRedirect(request.getContextPath() + "/menu");
                } else {
                    response.sendRedirect(request.getContextPath() + "/menu");
                }
            } else {
                // Authentication failure
                request.setAttribute(UserConstant.MSG_NOTIFICATION, UserConstant.MSG_LOGIN_FAILED);
                request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_ERROR);

                request.getRequestDispatcher(UserConstant.LOGIN_PAGE).forward(request, response);
            }
        } catch (SQLException e) {
            // Database error handling
            request.setAttribute(UserConstant.MSG_NOTIFICATION, UserConstant.MSG_DB_ERROR + e.getMessage());
            request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_ERROR);

            request.getRequestDispatcher(UserConstant.LOGIN_PAGE).forward(request, response);
        } catch (Exception e) {
            // Generic error handling
            request.setAttribute(UserConstant.MSG_NOTIFICATION, UserConstant.NOTIFICATION_ERROR + " " + e.getMessage());
            request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_ERROR);

            request.getRequestDispatcher(UserConstant.LOGIN_PAGE).forward(request, response);
        }
    }
}