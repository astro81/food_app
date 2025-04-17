package filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;

/**
 * Authorization filter that verifies user permissions for protected resources.
 * Restricts access to admin-only paths to users with 'admin' role.
 * Returns HTTP 403 (Forbidden) for unauthorized access attempts.
 */
public class AuthorizationFilter implements Filter {

    /**
     * List of URL paths that require admin privileges.
     * Includes all menu management operations:
     * - Adding menu items
     * - Editing menu items
     * - Deleting menu items
     */
    private static final String[] ADMIN_PATHS = {
            "/menu/add",    // Menu item addition
            "/menu/edit",    // Menu item editing
            "/menu/delete"   // Menu item deletion
    };

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
     * Main filter method that checks authorization for each request.
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

        // Check if requested path requires admin privileges
        if (requiresAdmin(path)) {
            HttpSession session = httpRequest.getSession(false);

            // Verify admin role in existing session
            if (session != null) {
                UserModel user = (UserModel) session.getAttribute(UserConstant.ATTR_USER);
                if (user != null && "admin".equals(user.getUserRole())) {
                    // User is admin - proceed with request
                    chain.doFilter(request, response);
                    return;
                }
            }

            // Not authorized - send 403 Forbidden response
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "You don't have permission to access this resource");
            return;
        }

        // Path doesn't require admin privileges - proceed normally
        chain.doFilter(request, response);
    }

    /**
     * Checks if the requested path requires admin privileges.
     *
     * @param path The request path to check
     * @return true if path is in ADMIN_PATHS, false otherwise
     */
    private boolean requiresAdmin(String path) {
        // Check against all admin-restricted paths
        for (String adminPath : ADMIN_PATHS) {
            if (path.startsWith(adminPath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Cleanup method for filter destruction. Currently no cleanup needed.
     */
    @Override
    public void destroy() {
        // No cleanup currently required
    }
}