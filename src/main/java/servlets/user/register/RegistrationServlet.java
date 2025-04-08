package servlets.user.register;

import java.io.*;
import controller.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;

/**
 * Servlet implementation for user registration process.
 * <p>
 * This servlet handles both the display of the registration form (GET) and
 * processing of registration data (POST). Key functionalities include:
 * <ul>
 *   <li>Rendering the registration interface</li>
 *   <li>Validating and processing user registration data</li>
 *   <li>Persisting new user accounts to the database</li>
 *   <li>Providing appropriate user feedback</li>
 * </ul>
 *
 * <p><strong>Security Considerations:</strong>
 * <ul>
 *   <li>No password hashing visible in current implementation</li>
 *   <li>Input validation should be handled by the DAO or model layer</li>
 *   <li>No CSRF protection implemented in form submission</li>
 * </ul>
 */
@WebServlet(
        name = "RegistrationServlet",
        value = "/user/register",
        description = "Handles new user account creation and registration form processing"
)
public class RegistrationServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    // View configuration
    private static final String REGISTRATION_PAGE = "/WEB-INF/user/register.jsp";

    // Request parameter names
    private static final String PARAM_NAME = "user_name";
    private static final String PARAM_EMAIL = "user_mail";
    private static final String PARAM_PASSWORD = "user_passwd";
    private static final String PARAM_PHONE = "user_phone";
    private static final String PARAM_ADDRESS = "user_address";

    // Notification attributes
    private static final String ATTR_NOTIFICATION = "NOTIFICATION";
    private static final String MSG_SUCCESS = "User Registered Successfully!";
    private static final String MSG_FAILURE = "Registration Failed!";
    private static final String MSG_ERROR = "Error: ";

    // Dependencies
    private UserDAO userDAO;

    /**
     * Initializes the servlet and its data access components.
     * <p>
     * Creates a new UserDAO instance for database operations. This method is
     * guaranteed to be called once before the servlet handles any requests.
     */
    @Override
    public void init() {
        this.userDAO = new UserDAO();
    }

    /**
     * Handles HTTP GET requests for registration page display.
     * <p>
     * Simply forwards to the registration view without any processing. The JSP
     * should handle any notification messages present in the request attributes.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs during view rendering
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(REGISTRATION_PAGE).forward(request, response);
    }

    /**
     * Handles HTTP POST requests containing registration data.
     * <p>
     * Processes the registration attempt through these steps:
     * <ol>
     *   <li>Extracts user data from request parameters</li>
     *   <li>Creates new UserModel instance</li>
     *   <li>Attempts to persist user via UserDAO</li>
     *   <li>Sets appropriate notification message</li>
     *   <li>Forwards back to registration page with status</li>
     * </ol>
     *
     * @param request  the HttpServletRequest object containing form data
     * @param response the HttpServletResponse object
     * @throws ServletException if form processing fails
     * @throws IOException if view forwarding fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract form data
        String userName = request.getParameter(PARAM_NAME);
        String userMail = request.getParameter(PARAM_EMAIL);
        String userPasswd = request.getParameter(PARAM_PASSWORD);
        String userPhone = request.getParameter(PARAM_PHONE);
        String userAddress = request.getParameter(PARAM_ADDRESS);

        // Create user model
        UserModel user = new UserModel(userName, userMail, userPasswd, userPhone, userAddress);

        try {
            // Attempt registration
            if (userDAO.registerUser(user)) {
                request.setAttribute(ATTR_NOTIFICATION, MSG_SUCCESS);
            } else {
                request.setAttribute(ATTR_NOTIFICATION, MSG_FAILURE);
            }
        } catch (Exception e) {
            // Error handling
            request.setAttribute(ATTR_NOTIFICATION, MSG_ERROR + e.getMessage());
            e.printStackTrace();
        }

        // Return to registration page with notification
        request.getRequestDispatcher(REGISTRATION_PAGE).forward(request, response);
    }

    /**
     * Cleanup method called during servlet destruction.
     * <p>
     * Currently no resources require explicit cleanup. Maintained for
     * future compatibility and consistent servlet lifecycle management.
     */
    @Override
    public void destroy() {
        // No resources to clean up in current implementation
    }
}