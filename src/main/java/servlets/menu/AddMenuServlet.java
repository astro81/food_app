package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;
import servlets.menu.helpers.MenuRequestHandler;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet implementation for adding new menu items to the system.
 * This servlet handles both displaying the add menu item form (GET requests)
 * and processing the form submission to add a new menu item (POST requests).
 * Access is restricted to admin users only.
 */
@WebServlet(
        name = "AddMenuServlet",
        value = "/menu/add",
        description = "Servlet for adding new menu items to the system. Handles both form display and submission."
)
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 50,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100    // 100 MB
)
public class AddMenuServlet extends BaseMenuServlet {

    /**
     * Handles GET requests to display the menu item addition form.
     *
     * @param request  The HttpServletRequest object containing client request data
     * @param response The HttpServletResponse object for sending responses
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is admin, return unauthorized response if not
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        // Forward to the menu edit page (which will be used for adding new items)
        forwardToView(request, response, MenuConstant.MENU_EDIT_PAGE);
    }

    /**
     * Handles POST requests to process the addition of a new menu item.
     * Extracts menu item data from the request, validates it, and attempts to add it to the database.
     *
     * @param request  The HttpServletRequest object containing the new menu item data
     * @param response The HttpServletResponse object for sending responses
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify admin privileges before processing
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            // Extract menu item data from the request parameters
            MenuItemModel newItem = MenuRequestHandler.extractMenuItemFromRequest(request);

            // Attempt to add the new menu item to the database
            boolean success = menuItemDAO.addMenuItem(newItem);

            // Set appropriate success/failure notification message
            String notification = success ? MenuConstant.MSG_ADD_SUCCESS : MenuConstant.MSG_ADD_FAILURE;

            // Store notification in request and redirect back to menu page
            setNotification(request, notification);
            redirectToMenu(response, request);
        } catch (SQLException e) {
            // Handle any database errors that occur during the operation
            handleError(request, response, e);
        }
    }
}