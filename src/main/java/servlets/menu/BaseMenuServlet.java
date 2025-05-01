package servlets.menu;

import dao.MenuItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MenuItemModel;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;

/**
 * Abstract base servlet class providing common functionality for menu-related operations.
 * Contains shared methods for authorization, error handling, and view management
 * that are used by concrete menu servlet implementations.
 */
public abstract class BaseMenuServlet extends HttpServlet {

    // Data Access Object for menu item operations
    protected MenuItemDAO menuItemDAO;

    /**
     * Initializes the servlet by creating a new MenuItemDAO instance.
     * Called by the servlet container when the servlet is first loaded.
     */
    @Override
    public void init() {
        this.menuItemDAO = new MenuItemDAO();
    }

    /**
     * Checks if the current user has admin privileges.
     *
     * @param request The HttpServletRequest containing the user's session
     * @return true if the user is logged in and has 'admin' role, false otherwise
     */
    protected boolean isAdmin(HttpServletRequest request) {
        UserModel user = getCurrentUser(request);
        return user != null && UserConstant.ROLE_ADMIN.equals(user.getUserRole());
    }

    /**
     * Checks if the current user has vendor privileges.
     *
     * @param request The HttpServletRequest containing the user's session
     * @return true if the user is logged in and has 'vendor' role, false otherwise
     */
    protected boolean isVendor(HttpServletRequest request) {
        UserModel user = getCurrentUser(request);
        return user != null && UserConstant.ROLE_VENDOR.equals(user.getUserRole());
    }

    /**
     * Checks if the current user is authorized for menu management.
     * Both admin and vendor roles are authorized.
     *
     * @param request The HttpServletRequest containing the user's session
     * @return true if the user is admin or vendor, false otherwise
     */
    protected boolean isAuthorized(HttpServletRequest request) {
        return isAdmin(request) || isVendor(request);
    }

    /**
     * Retrieves the current user from the session.
     *
     * @param request The HttpServletRequest containing the session
     * @return UserModel if user is logged in, null otherwise
     */
    protected UserModel getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (UserModel) session.getAttribute(UserConstant.ATTR_USER) : null;
    }

    /**
     * Handles unauthorized access attempts by setting a notification and forwarding to menu list.
     *
     * @param request The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    protected void unauthorized(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(MenuConstant.MSG_NOTIFICATION, MenuConstant.MSG_UNAUTHORIZED);
        forwardToView(request, response, MenuConstant.MENU_LIST_PAGE);
    }

    /**
     * Handles exceptions by logging them and forwarding to the menu list page with an error message.
     *
     * @param request The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @param e The exception that occurred
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    protected void handleError(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute(MenuConstant.MSG_NOTIFICATION, MenuConstant.MSG_DB_ERROR + e.getMessage());
        forwardToView(request, response, MenuConstant.MENU_LIST_PAGE);
    }

    /**
     * Redirects the client to the main menu page.
     *
     * @param response The HttpServletResponse object for redirection
     * @param request The HttpServletRequest object for context path
     * @throws IOException If an I/O error occurs during redirection
     */
    protected void redirectToMenu(HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    /**
     * Forwards the request to a specified view (JSP page).
     *
     * @param request The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @param view The path to the view (JSP page) to forward to
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException If an I/O error occurs
     */
    protected void forwardToView(HttpServletRequest request, HttpServletResponse response, String view)
            throws ServletException, IOException {
        request.getRequestDispatcher(view).forward(request, response);
    }

    /**
     * Sets a notification message in the session to be displayed to the user.
     *
     * @param request The HttpServletRequest object containing the session
     * @param message The notification message to store
     */
    protected void setNotification(HttpServletRequest request, String message) {
        request.getSession().setAttribute(MenuConstant.MSG_NOTIFICATION, message);
    }

    /**
     * Checks if the current user can edit the specified menu item.
     * Admins can edit all items, vendors can only edit their own items.
     *
     * @param request The HttpServletRequest containing the user's session
     * @param item The menu item to check
     * @return true if user is authorized to edit the item, false otherwise
     */
    protected boolean canEditItem(HttpServletRequest request, MenuItemModel item) {
        if (isAdmin(request)) {
            return true;
        }
        // Add logic here if vendors have ownership of certain items
        // For now, vendors can edit all items like admins
        return isVendor(request);
    }
}