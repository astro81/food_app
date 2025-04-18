package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MenuItemModel;
import model.UserModel;
import servlets.user.UserConstant;
import dao.OrderDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation for viewing the complete menu items list.
 * Handles GET requests to display all available menu items.
 * Provides different view capabilities for admin vs regular users.
 */
@WebServlet(
        name = "ViewMenuServlet",
        value = "/menu",
        description = "Servlet for viewing the complete menu items listing. Displays all available menu items."
)
public class ViewMenuServlet extends BaseMenuServlet {

    /**
     * Handles GET requests to display the full menu items list.
     * Retrieves all menu items from database and forwards to list view.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs during processing
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Set admin status flag for view customization
            request.setAttribute("isAdmin", isAdmin(request));

            // Retrieve all menu items from database
            List<MenuItemModel> menuItems = menuItemDAO.getAllMenuItems();

            // Prepare request attributes and forward to list view
            request.setAttribute(MenuConstant.ATTR_MENU_ITEMS, menuItems);
            forwardToView(request, response, MenuConstant.MENU_LIST_PAGE);

        } catch (SQLException e) {
            // Handle database access errors
            handleError(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Fetch the menu id
            int itemId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
            // get menu on the basis of id
            MenuItemModel menuItem = menuItemDAO.getMenuItemById(itemId);

            if (menuItem == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Menu item not found");
                return;
            }

            // Get the current user from session
            HttpSession session = request.getSession(false);
            UserModel user = (session != null) ? (UserModel) session.getAttribute(UserConstant.ATTR_USER) : null;

            if (user != null) {
                // Add item to order (using a placeholder user ID - you should replace with actual user ID)
                OrderDAO.createOrder(user.getUserId(), menuItem); // Replace 1 with actual user ID from UserModel
            } else {
                System.out.println("\nCannot add to order - no user logged in");
            }


        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid menu item ID");
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}