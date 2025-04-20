package servlets.order;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;
import model.UserModel;
import servlets.menu.MenuConstant;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet implementation for adding items to a user's order.
 * Processes POST requests to add menu items to the user's current order.
 */
@WebServlet(
        name = "OrderServlet",
        value = "/order",
        description = "Servlet for adding items to a user's order."
)
public class OrderServlet extends BaseOrderServlet {

    /**
     * Handles POST requests to add a menu item to the user's order.
     * Validates user authentication and item existence before creating the order.
     *
     * @param request  The HTTP request containing the menu item ID
     * @param response The HTTP response
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify user is authenticated
        if (!checkAuthentication(request, response)) {
            return;
        }

        try {
            // Get the menu item from the request
            MenuItemModel menuItem = getMenuItemFromRequest(request);
            if (menuItem == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, OrderConstant.MSG_ITEM_NOT_FOUND);
                return;
            }

            // Add item to the user's order
            UserModel user = getAuthenticatedUser(request);
            OrderDAO.createOrder(user.getUserId(), menuItem);

            // Set success notification and redirect
            setNotification(request, OrderConstant.MSG_ORDER_CREATED);
            redirectToMenu(response, request);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid menu item ID");
        } catch (SQLException e) {
            handleError(request, response, e, "/menu");
        }
    }

    /**
     * Retrieves the menu item from the request parameters.
     *
     * @param request The HTTP request
     * @return The menu item, or null if not found
     * @throws NumberFormatException If the item ID is not a valid number
     * @throws SQLException          If a database error occurs
     */
    private MenuItemModel getMenuItemFromRequest(HttpServletRequest request)
            throws NumberFormatException, SQLException {
        int itemId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
        return menuItemDAO.getMenuItemById(itemId);
    }
}