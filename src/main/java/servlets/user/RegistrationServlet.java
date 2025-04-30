package servlets.user;

import java.io.*;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import org.mindrot.jbcrypt.BCrypt;

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
        request.getRequestDispatcher(UserConstant.REGISTRATION_PAGE).forward(request, response);
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
        String userName = request.getParameter(UserConstant.PARAM_NAME);
        String userMail = request.getParameter(UserConstant.PARAM_EMAIL);
        String userPasswd = request.getParameter(UserConstant.PARAM_PASSWORD);
        String userPhone = request.getParameter(UserConstant.PARAM_PHONE);
        String userAddress = request.getParameter(UserConstant.PARAM_ADDRESS);
        String userRole = "customer";

        // Hash the password before creating UserModel
        String hashedPassword = BCrypt.hashpw(userPasswd, BCrypt.gensalt());

        // Create user model
        UserModel user = new UserModel(userName, userMail, hashedPassword, userPhone, userAddress, userRole);

        try {
            // Attempt registration
            if (userDAO.registerUser(user)) {
                request.setAttribute(UserConstant.MSG_NOTIFICATION, UserConstant.MSG_REGISTER_SUCCESS);
                request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_SUCCESS);

            } else {
                request.setAttribute(UserConstant.MSG_NOTIFICATION, UserConstant.MSG_REGISTER_FAILED);
                request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_ERROR);
            }
        } catch (Exception e) {
            request.setAttribute(UserConstant.MSG_NOTIFICATION, UserConstant.MSG_DB_ERROR + e.getMessage());
            request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_ERROR);

            e.printStackTrace();
        }

        // Return to registration page with notification
        request.getRequestDispatcher(UserConstant.REGISTRATION_PAGE).forward(request, response);
    }
}