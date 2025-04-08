package servlets.user.register;

import java.io.*;
import controller.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;

/**
 * Servlet handling user registration requests.
 *
 * Jakarta EE Annotations Explanation:
 * @WebServlet - Marks this class as a servlet and maps it to a URL pattern
 * HttpServlet - Base class for HTTP servlets in Jakarta EE
 */
@WebServlet(name = "RegistrationServlet", value = "/user/register")
public class RegistrationServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;  // Version control for serialization

    private static final String REGISTRATION_PAGE = "/WEB-INF/user/register.jsp";

    private UserDAO userDAO;  // DAO for database operations

    /**
     * Servlet initialization method.
     * Called by the servlet container when the servlet is first loaded.
     */
    public void init() {
        userDAO = new UserDAO();  // Initialize UserDAO instance
    }

    /**
     * Handles HTTP GET requests (showing the registration form).
     *
     * Jakarta EE Explanation:
     * HttpServletRequest - Wraps all incoming request data
     * HttpServletResponse - Provides tools to send response back to client
     *
     * @param request The HTTP request object
     * @param response The HTTP response object
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to registration JSP page
        request.getRequestDispatcher(REGISTRATION_PAGE).forward(request, response);

    }

    /**
     * Handles HTTP POST requests (processing registration form submission).
     *
     * @param request Contains form data submitted by user
     * @param response Used to send response back to client
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form parameters from request
        String userName = request.getParameter("user_name");
        String userMail = request.getParameter("user_mail");
        String userPasswd = request.getParameter("user_passwd");
        String userPhone = request.getParameter("user_phone");
        String userAddress = request.getParameter("user_address");

        // Create new UserModel with form data
        UserModel user = new UserModel(userName, userMail, userPasswd, userPhone, userAddress);

        try {
            // Attempt to register user
            if (userDAO.registerUser(user)) {
                // Set success notification
                request.setAttribute("NOTIFICATION", "User Registered Successfully!");
            } else {
                // Set failure notification
                request.setAttribute("NOTIFICATION", "Registration Failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Set error notification
            request.setAttribute("NOTIFICATION", "Error: " + e.getMessage());
        }

        // Forward back to registration page with notification
        request.getRequestDispatcher(REGISTRATION_PAGE).forward(request, response);

    }

    /**
     * Servlet cleanup method.
     * Called by the servlet container when servlet is being destroyed.
     */
    public void destroy() {
        // Cleanup resources if needed
    }
}
