package filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servlets.user.UserConstant;

import java.io.IOException;

/**
 * Session timeout filter that automatically logs out inactive sessions.
 * Tracks user inactivity and invalidates sessions that exceed the maximum allowed inactive interval.
 * Redirects timed-out users to login page with timeout notification.
 */
public class SessionTimeoutFilter implements Filter {

    /**
     * Maximum allowed inactive session interval in milliseconds.
     * Set to 30 minutes (30 * 60 * 1000 ms).
     */
    private static final long MAX_INACTIVE_INTERVAL = 30 * 60 * 1000; // 30 minutes

    /**
     * Initializes the filter. Currently, no initialization needed.
     *
     * @param filterConfig Filter configuration object
     * @throws ServletException If initialization fails
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization currently required
    }

    /**
     * Main filter method that checks session activity for each request.
     *
     * @param request  The ServletRequest object
     * @param response The ServletResponse object
     * @param chain    The FilterChain for proceeding with the request
     * @throws IOException      If an I/O error occurs
     * @throws ServletException If a servlet-specific error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Get existing session without creating a new one
        HttpSession session = httpRequest.getSession(false);

        if (session != null) {
            // Calculate session inactivity period
            long lastAccessedTime = session.getLastAccessedTime();
            long currentTime = System.currentTimeMillis();
            long inactiveInterval = currentTime - lastAccessedTime;

            // Check if session has timed out
            if (inactiveInterval > MAX_INACTIVE_INTERVAL) {
                // Invalidate expired session
                session.invalidate();

                // Redirect to login page with timeout notification
                httpResponse.sendRedirect(httpRequest.getContextPath() + UserConstant.LOGIN_PATH + "?timeout=true");
                return;
            }
        }

        // Session is valid or doesn't exist - proceed with request
        chain.doFilter(request, response);
    }

    /**
     * Cleanup method for filter destruction. Currently, no cleanup needed.
     */
    @Override
    public void destroy() {
        // No cleanup currently required
    }
}