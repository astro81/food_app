package servlets.menu;

import dao.MenuItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MenuItemModel;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;
import java.io.Serial;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation for menu item operations.
 * Supports both admin and customer roles with appropriate access restrictions.
 */
@WebServlet(
        name = "MenuItemServlet",
        value = "/menu/*",
        description = "Controller for menu item operations"
)
public class MenuItemServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    private MenuItemDAO menuItemDAO;

    @Override
    public void init() {
        this.menuItemDAO = new MenuItemDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get path info to determine action
        String pathInfo = request.getPathInfo();
        String action = request.getParameter(MenuConstant.PARAM_ACTION);

        // Default to view action if none specified
        if (action == null) {
            action = MenuConstant.ACTION_VIEW;
        }

        try {
            // Process based on action parameter
            switch (action) {
                case MenuConstant.ACTION_ADD:
                    if (isAdmin(request)) {
                        showAddForm(request, response);
                    } else {
                        unauthorized(request, response);
                    }
                    break;
                case MenuConstant.ACTION_EDIT:
                    if (isAdmin(request)) {
                        showEditForm(request, response);
                    } else {
                        unauthorized(request, response);
                    }
                    break;
                case MenuConstant.ACTION_FILTER:
                    filterMenuItems(request, response);
                    break;
                case MenuConstant.ACTION_VIEW:
                default:
                    viewMenuItems(request, response);
            }
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter(MenuConstant.PARAM_ACTION);

        // Check if user is admin for all POST operations
        if (!isAdmin(request)) {
            try {
                unauthorized(request, response);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        try {
            switch (action) {
                case MenuConstant.ACTION_ADD:
                    addMenuItem(request, response);
                    break;
                case MenuConstant.ACTION_EDIT:
                    updateMenuItem(request, response);
                    break;
                case MenuConstant.ACTION_DELETE:
                    deleteMenuItem(request, response);
                    break;
                default:
                    viewMenuItems(request, response);
            }
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }

    /**
     * Display list of all menu items
     */
    private void viewMenuItems(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        List<MenuItemModel> menuItems = menuItemDAO.getAllMenuItems();
        request.setAttribute(MenuConstant.ATTR_MENU_ITEMS, menuItems);
        request.getRequestDispatcher(MenuConstant.MENU_LIST_PAGE).forward(request, response);
    }

    /**
     * Display form to add a new menu item
     */
    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(MenuConstant.MENU_EDIT_PAGE).forward(request, response);
    }

    /**
     * Display form to edit an existing menu item
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int foodId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
        MenuItemModel menuItem = menuItemDAO.getMenuItemById(foodId);

        if (menuItem != null) {
            request.setAttribute(MenuConstant.ATTR_MENU_ITEM, menuItem);
            request.getRequestDispatcher(MenuConstant.MENU_EDIT_PAGE).forward(request, response);
        } else {
            // Item not found, redirect to list
            response.sendRedirect(request.getContextPath() + MenuConstant.MENU_PATH);
        }
    }

    /**
     * Filter menu items by category or availability
     */
    private void filterMenuItems(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String category = request.getParameter(MenuConstant.ATTR_FILTER_CATEGORY);
        String availability = request.getParameter(MenuConstant.ATTR_FILTER_AVAILABILITY);
        List<MenuItemModel> menuItems;

        if (category != null && !category.isEmpty()) {
            menuItems = menuItemDAO.getMenuItemsByCategory(category);
            request.setAttribute(MenuConstant.ATTR_FILTER_CATEGORY, category);
        } else if (availability != null && !availability.isEmpty()) {
            menuItems = menuItemDAO.getMenuItemsByAvailability(availability);
            request.setAttribute(MenuConstant.ATTR_FILTER_AVAILABILITY, availability);
        } else {
            menuItems = menuItemDAO.getAllMenuItems();
        }

        request.setAttribute(MenuConstant.ATTR_MENU_ITEMS, menuItems);
        request.getRequestDispatcher(MenuConstant.MENU_LIST_PAGE).forward(request, response);
    }

    /**
     * Add a new menu item
     */
    private void addMenuItem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        String foodName = request.getParameter(MenuConstant.PARAM_FOOD_NAME);
        String foodDescription = request.getParameter(MenuConstant.PARAM_FOOD_DESC);
        BigDecimal foodPrice = new BigDecimal(request.getParameter(MenuConstant.PARAM_FOOD_PRICE));
        String foodCategory = request.getParameter(MenuConstant.PARAM_FOOD_CATEGORY);
        String foodAvailability = request.getParameter(MenuConstant.PARAM_FOOD_AVAILABILITY);

        MenuItemModel newItem = new MenuItemModel(foodName, foodDescription, foodPrice, foodCategory, foodAvailability);

        boolean success = menuItemDAO.addMenuItem(newItem);
        String notification = success ? MenuConstant.MSG_ADD_SUCCESS : MenuConstant.MSG_ADD_FAILURE;

        request.setAttribute(MenuConstant.MSG_NOTIFICATION, notification);
        viewMenuItems(request, response);
    }

    /**
     * Update an existing menu item
     */
    private void updateMenuItem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int foodId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
        String foodName = request.getParameter(MenuConstant.PARAM_FOOD_NAME);
        String foodDescription = request.getParameter(MenuConstant.PARAM_FOOD_DESC);
        BigDecimal foodPrice = new BigDecimal(request.getParameter(MenuConstant.PARAM_FOOD_PRICE));
        String foodCategory = request.getParameter(MenuConstant.PARAM_FOOD_CATEGORY);
        String foodAvailability = request.getParameter(MenuConstant.PARAM_FOOD_AVAILABILITY);

        MenuItemModel updatedItem = new MenuItemModel(foodId, foodName, foodDescription, foodPrice, foodCategory, foodAvailability);

        boolean success = menuItemDAO.updateMenuItem(updatedItem);
        String notification = success ? MenuConstant.MSG_UPDATE_SUCCESS : MenuConstant.MSG_UPDATE_FAILURE;

        request.setAttribute(MenuConstant.MSG_NOTIFICATION, notification);
        viewMenuItems(request, response);
    }

    /**
     * Delete a menu item
     */
    private void deleteMenuItem(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {

        int foodId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));

        boolean success = menuItemDAO.deleteMenuItem(foodId);
        String notification = success ? MenuConstant.MSG_DELETE_SUCCESS : MenuConstant.MSG_DELETE_FAILURE;

        request.setAttribute(MenuConstant.MSG_NOTIFICATION, notification);
        viewMenuItems(request, response);
    }

    /**
     * Handle unauthorized access attempt
     */
    private void unauthorized(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        request.setAttribute(MenuConstant.MSG_NOTIFICATION, MenuConstant.MSG_UNAUTHORIZED);
        viewMenuItems(request, response);
    }

    /**
     * Handle database errors
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws ServletException, IOException {

        e.printStackTrace();
        request.setAttribute(MenuConstant.MSG_NOTIFICATION, MenuConstant.MSG_DB_ERROR + e.getMessage());
        request.getRequestDispatcher(MenuConstant.MENU_LIST_PAGE).forward(request, response);
    }

    /**
     * Check if current user is an admin
     */
    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(UserConstant.ATTR_USER) != null) {
            UserModel user = (UserModel) session.getAttribute(UserConstant.ATTR_USER);
            return "admin".equals(user.getUserRole());
        }
        return false;
    }
}