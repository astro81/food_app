package servlets.user.logout;

import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

/**
 * Servlet implementation for secure user session termination.
 * <p>
 * This servlet handles the logout process by:
 * <ul>
 *   <li>Invalidating the current HTTP session and all associated data</li>
 *   <li>Clearing authentication tokens</li>
 *   <li>Preventing session fixation attacks</li>
 *   <li>Redirecting to login page with clean session state</li>
 * </ul>
 *
 * <p><strong>Security Features:</strong>
 * <ul>
 *   <li>No new session creation during logout process</li>
 *   <li>Complete session data destruction</li>
 *   <li>Context-aware redirect path construction</li>
 *   <li>Protection against session fixation attacks</li>
 * </ul>
 */
@WebServlet(
        name = "LogoutServlet",
        value = "/user/logout",
        description = "Handles secure session termination and user logout"
)
public class LogoutServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    // Configuration constants
    private static final String LOGIN_PATH = "/user/login";

    /**
     * Handles HTTP GET requests for user logout.
     * <p>
     * Implements secure logout procedure:
     * <ol>
     *   <li>Retrieves existing session without creating new one (false parameter)</li>
     *   <li>Invalidates session if one exists (destroys all session data)</li>
     *   <li>Constructs context-aware redirect path</li>
     *   <li>Performs client-side redirect to login page</li>
     * </ol>
     *
     * <p><strong>Security Note:</strong>
     * The session.invalidate() call is critical for preventing session fixation
     * attacks and ensuring complete cleanup of authentication artifacts.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if redirect operation fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Construct redirect path using context to ensure correct application root
        String loginPage = request.getContextPath() + LOGIN_PATH;

        // Retrieve existing session without creating new one
        HttpSession session = request.getSession(false);

        // Invalidate session if exists
        if (session != null) {
            session.invalidate();  // Security-critical operation
        }

        // Redirect to login page (client-side redirect)
        response.sendRedirect(loginPage);
    }

    /**
     * Ensures consistent logout behavior for HTTP POST requests.
     * <p>
     * Simply delegates to doGet() to maintain single code path for logout logic.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if redirect operation fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
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