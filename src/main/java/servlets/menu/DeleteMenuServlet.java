package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlets.menu.helpers.MenuRequestHandler;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet implementation for deleting menu items from the system.
 * Handles POST requests to remove a menu item identified by its ID.
 * Access is restricted to admin users only.
 */
@WebServlet(
        name = "DeleteMenuServlet",
        value = "/menu/delete",
        description = "Servlet for deleting menu items. Processes deletion requests and returns operation status."
)
public class DeleteMenuServlet extends BaseMenuServlet {

    /**
     * Handles POST requests to delete a menu item.
     * @param request  The HttpServletRequest containing the item ID to delete
     * @param response The HttpServletResponse for redirecting after operation
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs during processing
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify admin privileges before processing
        if (!isAuthorized(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            // Extract menu item ID from request parameters
            int foodId = MenuRequestHandler.extractItemIdFromRequest(request);

            // Attempt deletion through Data Access Object
            boolean success = menuItemDAO.deleteMenuItem(foodId);

            // Prepare appropriate user notification
            String notification = success ?
                    MenuConstant.MSG_DELETE_SUCCESS :
                    MenuConstant.MSG_DELETE_FAILURE;

            // Store notification in session and redirect
            setNotification(request, notification);

            // Redirect back to menu listing
            redirectToMenu(response, request);

        } catch (SQLException e) {
            // Handle database errors
            handleError(request, response, e);
        }
    }
}