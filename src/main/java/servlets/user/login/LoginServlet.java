package servlets.user.login;

import java.io.*;

import controller.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;

import java.sql.SQLException;

/**
 * Servlet implementation for handling user login functionality.
 * This servlet processes login requests, authenticates users against the database,
 * and manages user sessions upon successful authentication.
 */
@WebServlet(name = "LoginServlet", value = "/user/login")
public class LoginServlet extends HttpServlet {
    /** Serial version UID for serialization */
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String LOGIN_PAGE = "/WEB-INF/user/login.jsp";
    private static final String PROFILE_PAGE = "/WEB-INF/user/welcome.jsp";

    /** Data Access Object for user operations */
    private UserDAO userDao;

    /**
     * Initializes the servlet and creates a new UserDAO instance.
     * This method is called once when the servlet is first loaded.
     */
    public void init() {
        userDao = new UserDAO();
    }

    /**
     * Handles HTTP GET requests to the login endpoint.
     * Redirects users to the login page.
     *
     * @param request The HttpServletRequest object containing client request
     * @param response The HttpServletResponse object for sending response
     * @throws IOException If an input or output error occurs
     * @throws ServletException If a servlet-specific error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
    }

    /**
     * Handles HTTP POST requests containing login form data.
     * Authenticates user credentials and creates a session upon successful login.
     *
     * @param request The HttpServletRequest object containing client request
     * @param response The HttpServletResponse object for sending response
     * @throws IOException If an input or output error occurs
     * @throws ServletException If a servlet-specific error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Extract user credentials from request parameters
        String userMail = request.getParameter("user_mail");
        String userPasswd = request.getParameter("user_passwd");

        try {
            // Attempt to authenticate user with provided credentials using the instance method
            UserModel user = userDao.loginUser(userMail, userPasswd);

            if (user != null) {
                // User authenticated successfully - create session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Redirect to welcome page
                request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
            } else {
                // Authentication failed - return to login page with error message
                request.setAttribute("NOTIFICATION", "Invalid email or password!");
                request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
            }

        } catch (SQLException e) {
            // Handle database-specific exceptions
            e.printStackTrace();
            request.setAttribute("NOTIFICATION", "Database Error: " + e.getMessage());
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            request.setAttribute("NOTIFICATION", "Error: " + e.getMessage());
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        }
    }

    /**
     * Cleans up resources when the servlet is being destroyed.
     * This method is called once when the servlet is unloaded.
     */
    public void destroy() {
        // Clean up resources if needed
    }
}
