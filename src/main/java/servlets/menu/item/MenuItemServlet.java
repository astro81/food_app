package servlets.menu.item;

import controller.MenuItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MenuItemModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet(
        name = "MenuItemServlet",
        value = "/menu",
        description = "Handles CRUD operations for menu items"
)
public class MenuItemServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JSP page paths
    private static final String MENU_PAGE = "/WEB-INF/menu.jsp";
    private static final String EDIT_PAGE = "/WEB-INF/editMenuItem.jsp";

    // Form parameter names
    private static final String ITEM_FORM_PARAM_ID = "food_id";
    private static final String ITEM_FORM_PARAM_NAME = "food_name";
    private static final String ITEM_FORM_PARAM_DESC = "food_description";
    private static final String ITEM_FORM_PARAM_PRICE = "food_price";
    private static final String ITEM_FORM_PARAM_CATEGORY = "food_category";
    private static final String ITEM_FORM_PARAM_AVAILABILITY = "food_availability";

    // Action parameters
    private static final String ACTION_PARAM = "action";
    private static final String CREATE_ACTION = "create";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String EDIT_ACTION = "edit";

    // Session attribute names
    private static final String ATTR_SUCCESS = "successMessage";
    private static final String ATTR_ERROR = "errorMessage";
    private static final String ATTR_MENU_ITEMS = "menuItems";
    private static final String ATTR_MENU_ITEM = "menuItem";

    private MenuItemDAO menuItemDAO;

    @Override
    public void init() {
        this.menuItemDAO = new MenuItemDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter(ACTION_PARAM);

        try {
            if (EDIT_ACTION.equals(action)) {
                handleEdit(request, response);
            } else {
                handleView(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, "Error processing request: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter(ACTION_PARAM);
        HttpSession session = request.getSession();

        try {
            switch (action) {
                case CREATE_ACTION -> handleCreate(request, response, session);
                case UPDATE_ACTION -> handleUpdate(request, response, session);
                case DELETE_ACTION -> handleDelete(request, response, session);
                case null, default -> handleView(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, "Error processing request: " + e.getMessage());
        }
    }

    private void handleView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<MenuItemModel> menuItems = menuItemDAO.getAllMenuItems();
        request.setAttribute(ATTR_MENU_ITEMS, menuItems);
        request.getRequestDispatcher(MENU_PAGE).forward(request, response);
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int itemId = Integer.parseInt(request.getParameter(ITEM_FORM_PARAM_ID));
        MenuItemModel menuItem = menuItemDAO.getMenuItemById(itemId);

        if (menuItem != null) {
            request.setAttribute(ATTR_MENU_ITEM, menuItem);
            request.getRequestDispatcher(EDIT_PAGE).forward(request, response);
        } else {
            handleError(request, response, "Menu item not found with ID: " + itemId);
        }
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, SQLException {
        // Extract form data
        String menuItemName = request.getParameter(ITEM_FORM_PARAM_NAME);
        String menuItemDesc = request.getParameter(ITEM_FORM_PARAM_DESC);
        String priceStr = request.getParameter(ITEM_FORM_PARAM_PRICE);
        String menuItemCategory = request.getParameter(ITEM_FORM_PARAM_CATEGORY);
        String menuItemAvailability = request.getParameter(ITEM_FORM_PARAM_AVAILABILITY);

        // Validate data
        if (!validateMenuItemData(menuItemName, priceStr, menuItemCategory, menuItemAvailability, session)) {
            response.sendRedirect(request.getContextPath() + MENU_PAGE);
            return;
        }

        BigDecimal menuItemPrice = new BigDecimal(priceStr);

        // Create menu item model
        MenuItemModel menuItem = new MenuItemModel(
                menuItemName,
                menuItemDesc,
                menuItemPrice,
                menuItemCategory,
                menuItemAvailability
        );

        // Add to database
        boolean isAdded = menuItemDAO.addMenuItem(menuItem);

        if (isAdded) {
            session.setAttribute(ATTR_SUCCESS, "Menu item added successfully!");
        } else {
            session.setAttribute(ATTR_ERROR, "Failed to add menu item.");
        }

        response.sendRedirect(request.getContextPath() + "/menu");
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, SQLException {
        // Get item ID
        int itemId = Integer.parseInt(request.getParameter(ITEM_FORM_PARAM_ID));

        // Extract form data
        String menuItemName = request.getParameter(ITEM_FORM_PARAM_NAME);
        String menuItemDesc = request.getParameter(ITEM_FORM_PARAM_DESC);
        String priceStr = request.getParameter(ITEM_FORM_PARAM_PRICE);
        String menuItemCategory = request.getParameter(ITEM_FORM_PARAM_CATEGORY);
        String menuItemAvailability = request.getParameter(ITEM_FORM_PARAM_AVAILABILITY);

        // Validate data
        if (!validateMenuItemData(menuItemName, priceStr, menuItemCategory, menuItemAvailability, session)) {
            response.sendRedirect(request.getContextPath() + "/menu?action=edit&food_id=" + itemId);
            return;
        }

        BigDecimal menuItemPrice = new BigDecimal(priceStr);

        // Create menu item model with ID
        MenuItemModel menuItem = new MenuItemModel(
                itemId,
                menuItemName,
                menuItemDesc,
                menuItemPrice,
                menuItemCategory,
                menuItemAvailability
        );

        // Update in database
        boolean isUpdated = menuItemDAO.updateMenuItem(menuItem);

        if (isUpdated) {
            session.setAttribute(ATTR_SUCCESS, "Menu item updated successfully!");
        } else {
            session.setAttribute(ATTR_ERROR, "Failed to update menu item.");
        }

        response.sendRedirect(request.getContextPath() + "/menu");
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, SQLException {
        // Get item ID
        int itemId = Integer.parseInt(request.getParameter(ITEM_FORM_PARAM_ID));

        // Delete from database
        boolean isDeleted = menuItemDAO.deleteMenuItem(itemId);

        if (isDeleted) {
            session.setAttribute(ATTR_SUCCESS, "Menu item deleted successfully!");
        } else {
            session.setAttribute(ATTR_ERROR, "Failed to delete menu item.");
        }

        response.sendRedirect(request.getContextPath() + "/menu");
    }

    private boolean validateMenuItemData(String name, String priceStr, String category,
                                         String availability, HttpSession session) {
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            session.setAttribute(ATTR_ERROR, "Food name is required.");
            return false;
        }

        // Validate price
        try {
            BigDecimal price = new BigDecimal(priceStr);
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                session.setAttribute(ATTR_ERROR, "Price must be greater than zero.");
                return false;
            }
        } catch (NumberFormatException e) {
            session.setAttribute(ATTR_ERROR, "Invalid price format. Please enter a valid number.");
            return false;
        }

        // Validate category
        if (!Arrays.asList("meals", "snacks", "sweets", "drinks").contains(category)) {
            session.setAttribute(ATTR_ERROR, "Invalid food category.");
            return false;
        }

        // Validate availability
        if (!Arrays.asList("available", "out_of_order").contains(availability)) {
            session.setAttribute(ATTR_ERROR, "Invalid availability status.");
            return false;
        }

        return true;
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws IOException {
        request.getSession().setAttribute(ATTR_ERROR, errorMessage);
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
    }
}