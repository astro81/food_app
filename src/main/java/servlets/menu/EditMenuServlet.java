package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;
import servlets.menu.helpers.MenuRequestHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Servlet implementation for editing existing menu items.
 * Handles both displaying the edit form (GET) and processing updates (POST).
 * All operations require admin privileges.
 */
@WebServlet(
        name = "EditMenuServlet",
        value = "/menu/edit",
        description = "Servlet for editing menu items. Handles form display and update submissions."
)
public class EditMenuServlet extends BaseMenuServlet {

    /**
     * Handles GET requests to display the menu item edit form.
     * Retrieves the specified menu item and forwards to the edit view.
     *
     * @param request  The HttpServletRequest containing the item ID parameter
     * @param response The HttpServletResponse for rendering the view
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs during processing
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify admin privileges
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            // Extract item ID from request
            int foodId = MenuRequestHandler.extractItemIdFromRequest(request);

            // Retrieve menu item from database
            MenuItemModel menuItem = menuItemDAO.getMenuItemById(foodId);

            if (menuItem != null) {
                // Add item to request attributes and show edit form
                request.setAttribute(MenuConstant.ATTR_MENU_ITEM, menuItem);
                forwardToView(request, response, MenuConstant.MENU_EDIT_PAGE);
            } else {
                // Item not found - redirect to menu list
                redirectToMenu(response, request);
            }
        } catch (SQLException e) {
            // Handle database errors
            handleError(request, response, e);
        }
    }

    /**
     * Handles POST requests to update a menu item.
     * Processes the submitted form data and updates the database record.
     *
     * @param request  The HttpServletRequest containing updated item data
     * @param response The HttpServletResponse for redirecting after update
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify admin privileges
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            // Extract updated menu item data from request
            MenuItemModel updatedItem = MenuRequestHandler.extractMenuItemFromRequestWithId(request);

            // Attempt database update
            boolean success = menuItemDAO.updateMenuItem(updatedItem);

            // Set appropriate user notification
            String notification = success ?
                    MenuConstant.MSG_UPDATE_SUCCESS :
                    MenuConstant.MSG_UPDATE_FAILURE;

            // Store notification and redirect
            setNotification(request, notification);
            redirectToMenu(response, request);
        } catch (SQLException e) {
            // Handle database errors
            handleError(request, response, e);
        }
    }
}