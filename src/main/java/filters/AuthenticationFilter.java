package filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servlets.user.UserConstant;

import java.io.IOException;
import java.util.List;

/**
 * Servlet filter that handles authentication checks for protected resources.
 * Redirects unauthenticated users to login page for non-public paths.
 * Allows public access to specified resources like CSS, JS, and authentication pages.
 */
public class AuthenticationFilter implements Filter {

    /**
     * List of URL paths that can be accessed without authentication.
     * Includes:
     * - Public pages (home, login, register)
     * - Menu browsing pages
     * - Static resources (CSS, JS, images)
     */
    private static final String[] PUBLIC_PATHS = {
            "/index.jsp",          // Home page
            "/user/login",         // Login page
            "/user/register",      // Registration page
            "/menu",               // Menu listing
            "/menu/filter",        // Filtered menu
            "/css/",               // CSS resources
            "/js/",                // JavaScript resources
            "/images/"             // Image assets
    };

    /**
     * Main filter method that checks authentication status for each request.
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

        // Get request path relative to context
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Allow root path without authentication
        if (path.equals("/")) {
            chain.doFilter(request, response);
            return;
        }

        // Skip authentication for public paths
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Check for existing authenticated session
        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute(UserConstant.ATTR_USER) != null);

        // Redirect unauthenticated users to login with redirect parameter
        if (!isLoggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() +
                    UserConstant.LOGIN_PATH + "?redirect=" + path);
            return;
        }

        // Proceed with request for authenticated users
        chain.doFilter(request, response);
    }

    /**
     * Checks if the requested path is in the public paths list.
     *
     * @param path The request path to check
     * @return true if path is public, false otherwise
     */
    private boolean isPublicPath(String path) {
        // Special case for root and index
        if (path.equals("/") || path.equals("/index.jsp")) {
            return true;
        }

        // Check against all public paths
        for (String publicPath : PUBLIC_PATHS) {
            if (path.startsWith(publicPath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Initializes the filter. Currently no initialization needed.
     *
     * @param filterConfig Filter configuration object
     * @throws ServletException If initialization fails
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization currently required
    }

    /**
     * Cleanup method for filter destruction. Currently no cleanup needed.
     */
    @Override
    public void destroy() {
        // No cleanup currently required
    }
}