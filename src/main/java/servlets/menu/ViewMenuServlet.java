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
}