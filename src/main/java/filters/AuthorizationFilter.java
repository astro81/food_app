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
 * Restricts access to paths based on user roles (admin, vendor, customer).
 * Returns HTTP 403 (Forbidden) for unauthorized access attempts.
 */
public class AuthorizationFilter implements Filter {

    /**
     * List of URL paths that require admin privileges.
     * Includes all admin management operations.
     */
    private static final String[] ADMIN_PATHS = {
            "/admin/*",          // All admin paths
            "/user/promote",     // User promotion
            "/user/demote"       // User demotion
    };

    /**
     * List of URL paths that require vendor privileges.
     * Includes all menu management operations:
     * - Adding menu items
     * - Editing menu items
     * - Deleting menu items
     * - Viewing vendor-specific orders
     */
    private static final String[] VENDOR_PATHS = {
            "/menu/add",        // Menu item addition
            "/menu/edit",       // Menu item editing
            "/menu/delete",     // Menu item deletion
            "/vendor/*",        // All vendor paths
            "/orders/vendor"    // Vendor order view
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

        // Check authorization based on path requirements
        if (requiresAdmin(path)) {
            if (!hasAdminAccess(httpRequest)) {
                sendForbiddenResponse(httpResponse, "Admin privileges required");
                return;
            }
        } else if (requiresVendor(path)) {
            if (!hasVendorAccess(httpRequest)) {
                sendForbiddenResponse(httpResponse, "Vendor privileges required");
                return;
            }
        }

        // Proceed with the request if authorized
        chain.doFilter(request, response);
    }

    /**
     * Checks if the requested path requires admin privileges.
     *
     * @param path The request path to check
     * @return true if path is in ADMIN_PATHS, false otherwise
     */
    private boolean requiresAdmin(String path) {
        for (String adminPath : ADMIN_PATHS) {
            if (path.startsWith(adminPath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the requested path requires vendor privileges.
     *
     * @param path The request path to check
     * @return true if path is in VENDOR_PATHS, false otherwise
     */
    private boolean requiresVendor(String path) {
        for (String vendorPath : VENDOR_PATHS) {
            if (path.startsWith(vendorPath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies if the current user has admin access.
     *
     * @param request The HttpServletRequest object
     * @return true if user is admin, false otherwise
     */
    private boolean hasAdminAccess(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserModel user = (UserModel) session.getAttribute(UserConstant.ATTR_USER);
            return user != null && UserConstant.ROLE_ADMIN.equals(user.getUserRole());
        }
        return false;
    }

    /**
     * Verifies if the current user has vendor access.
     * Admins automatically have vendor privileges.
     *
     * @param request The HttpServletRequest object
     * @return true if user is vendor or admin, false otherwise
     */
    private boolean hasVendorAccess(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserModel user = (UserModel) session.getAttribute(UserConstant.ATTR_USER);
            if (user != null) {
                String role = user.getUserRole();
                return UserConstant.ROLE_VENDOR.equals(role) || UserConstant.ROLE_ADMIN.equals(role);
            }
        }
        return false;
    }

    /**
     * Sends a 403 Forbidden response with a custom message.
     *
     * @param response The HttpServletResponse object
     * @param message  The error message to include
     * @throws IOException If an I/O error occurs
     */
    private void sendForbiddenResponse(HttpServletResponse response, String message) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, message);
    }

    /**
     * Cleanup method for filter destruction. Currently no cleanup needed.
     */
    @Override
    public void destroy() {
        // No cleanup currently required
    }
}