package servlets.user.logout;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Handles user logout functionality by invalidating the current session
 * and redirecting to the login page.
 *
 * Security Features:
 * - Proper session invalidation
 * - Safe redirect after logout
 * - Protection against session fixation attacks
 */
@WebServlet(name = "LogoutServlet", value = "/user/logout")
public class LogoutServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

//    private static final String loginPagePath = "/user/login.jsp";

    /**
     * Handles the HTTP GET request for user logout.
     *
     * Process Flow:
     * 1. Retrieves the current session (if exists) without creating a new one
     * 2. Invalidates the session to clear all user data
     * 3. Redirects to login page with a fresh session
     *
     * @param request The HttpServletRequest object containing client request
     * @param response The HttpServletResponse object for sending responses
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String LOGIN_PAGE = request.getContextPath() + "/user/login";

        // Get existing session without creating a new one (false parameter)
        HttpSession session = request.getSession(false);

        // If session exists, invalidate it to clear all attributes
        if (session != null) {
            session.invalidate();  // Critical security step
        }

        // Redirect to login page after logout
        response.sendRedirect(LOGIN_PAGE);
    }
}
