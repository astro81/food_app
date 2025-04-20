package servlets.order;

import dao.MenuItemDAO;
import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Base servlet class for order-related operations.
 * Provides common functionality for order management servlets including:
 * - User authentication checking
 * - Admin authorization
 * - Error handling
 * - Navigation helpers
 * - Notification management
 */
public abstract class BaseOrderServlet extends HttpServlet {
    protected OrderDAO orderDAO;
    protected MenuItemDAO menuItemDAO;

    /**
     * Initializes data access objects during servlet initialization.
     */
    @Override
    public void init() {
        this.orderDAO = new OrderDAO();
        this.menuItemDAO = new MenuItemDAO();
    }

    /**
     * Retrieves the currently authenticated user from the session.
     *
     * @param request The HTTP request
     * @return UserModel if authenticated, null otherwise
     */
    protected UserModel getAuthenticatedUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (UserModel) session.getAttribute(UserConstant.ATTR_USER) : null;
    }

    /**
     * Checks if the current user has admin privileges.
     *
     * @param request The HTTP request
     * @return true if user is admin, false otherwise
     */
    protected boolean isAdmin(HttpServletRequest request) {
        UserModel user = getAuthenticatedUser(request);
        return user != null && "admin".equals(user.getUserRole());
    }

    /**
     * Handles exceptions with standardized error message display and redirection.
     *
     * @param request      The HTTP request
     * @param response     The HTTP response
     * @param e            The exception that occurred
     * @param redirectPath The path to redirect to after error handling
     * @throws ServletException If a servlet error occurs
     * @throws IOException      If an I/O error occurs
     */
    protected void handleError(HttpServletRequest request, HttpServletResponse response,
                               Exception e, String redirectPath) throws ServletException, IOException {
        e.printStackTrace();
        setNotification(request, OrderConstant.MSG_DB_ERROR + e.getMessage());
        response.sendRedirect(request.getContextPath() + redirectPath);
    }

    /**
     * Sets a notification message in the session.
     *
     * @param request The HTTP request
     * @param message The notification message
     */
    protected void setNotification(HttpServletRequest request, String message) {
        request.getSession().setAttribute(OrderConstant.MSG_NOTIFICATION, message);
    }

    /**
     * Redirects unauthenticated users to the login page.
     *
     * @param response The HTTP response
     * @param request  The HTTP request
     * @throws IOException If an I/O error occurs
     */
    protected void redirectToLogin(HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/login");
    }

    /**
     * Redirects to the menu page.
     *
     * @param response The HTTP response
     * @param request  The HTTP request
     * @throws IOException If an I/O error occurs
     */
    protected void redirectToMenu(HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    /**
     * Forwards the request to the specified JSP view.
     *
     * @param request    The HTTP request
     * @param response   The HTTP response
     * @param viewPath   The path to the JSP view
     * @throws ServletException If a servlet error occurs
     * @throws IOException      If an I/O error occurs
     */
    protected void forwardToView(HttpServletRequest request, HttpServletResponse response, String viewPath)
            throws ServletException, IOException {
        request.getRequestDispatcher(viewPath).forward(request, response);
    }

    /**
     * Handles authentication check with redirect to login if not authenticated.
     *
     * @param request   The HTTP request
     * @param response  The HTTP response
     * @return true if user is authenticated, false otherwise
     * @throws IOException If redirect fails
     */
    protected boolean checkAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        UserModel user = getAuthenticatedUser(request);
        if (user == null) {
            redirectToLogin(response, request);
            return false;
        }
        return true;
    }
}