package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation for filtering menu items by category and availability.
 * Handles GET requests to display filtered menu listings.
 * Shows different views for admin vs regular users.
 */
@WebServlet(
        name = "FilterMenuServlet",
        value = "/menu/filter",
        description = "Servlet for filtering menu items by category and availability status. Returns filtered listings."
)
public class FilterMenuServlet extends BaseMenuServlet {

    /**
     * Handles GET requests to display filtered menu items.
     * Processes filter parameters and retrieves matching items from database.
     *
     * @param request  The HttpServletRequest containing filter parameters
     * @param response The HttpServletResponse for rendering the view
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs during processing
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Set admin flag for view rendering
            request.setAttribute("isAdmin", isAdmin(request));

            // Retrieve filtered menu items based on request parameters
            List<MenuItemModel> menuItems = getFilteredMenuItems(request);

            // Prepare attributes and forward to list view
            setRequestAttributes(request, menuItems);
            forwardToView(request, response, MenuConstant.MENU_LIST_PAGE);
        } catch (SQLException e) {
            // Handle database errors
            handleError(request, response, e);
        }
    }

    /**
     * Retrieves menu items filtered by request parameters.
     * Applies category filter, availability filter, or returns all items if no filter specified.
     *
     * @param request The HttpServletRequest containing filter parameters
     * @return List of filtered MenuItemModel objects
     * @throws SQLException If a database access error occurs
     */
    private List<MenuItemModel> getFilteredMenuItems(HttpServletRequest request) throws SQLException {
        // Extract filter parameters from request
        String category = request.getParameter(MenuConstant.ATTR_FILTER_CATEGORY);
        String availability = request.getParameter(MenuConstant.ATTR_FILTER_AVAILABILITY);

        if (category != null && !category.isEmpty()) {
            // Apply category filter
            request.setAttribute(MenuConstant.ATTR_FILTER_CATEGORY, category);
            return menuItemDAO.getMenuItemsByCategory(category);

        } else if (availability != null && !availability.isEmpty()) {
            // Apply availability filter
            request.setAttribute(MenuConstant.ATTR_FILTER_AVAILABILITY, availability);
            return menuItemDAO.getMenuItemsByAvailability(availability);
        }

        // No filters specified - return all menu items
        return menuItemDAO.getAllMenuItems();
    }

    /**
     * Sets request attributes for the menu items list view.
     *
     * @param request    The HttpServletRequest to store attributes
     * @param menuItems  The list of menu items to display
     */
    private void setRequestAttributes(HttpServletRequest request, List<MenuItemModel> menuItems) {
        request.setAttribute(MenuConstant.ATTR_MENU_ITEMS, menuItems);
    }
}