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
import java.io.Serial;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet implementation class for handling CRUD operations on menu items.
 *
 * <p>This servlet provides endpoints for managing menu items including:
 * <ul>
 *   <li>Listing all menu items</li>
 *   <li>Creating new menu items</li>
 *   <li>Updating existing menu items</li>
 *   <li>Deleting menu items</li>
 *   <li>Displaying edit forms</li>
 * </ul>
 *
 * <p>The servlet follows RESTful conventions with GET requests for read operations
 * and POST requests for create, update, and delete operations.
 *
 * <p>All database operations are delegated to the MenuItemDAO class.
 */
@WebServlet(
        name = "MenuItemServlet",
        value = "/menu",
        description = "Handles CRUD operations for menu items"
)
public class MenuItemServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    // Constants for JSP view paths
    private static final String MENU_PAGE = "/WEB-INF/menu/index.jsp";
    private static final String EDIT_PAGE = "/WEB-INF/menu/editMenuItem.jsp";
    private static final String ADD_PAGE = "/WEB-INF/menu/addMenuItem.jsp";


    /**
     * Nested class containing constants for request parameter names.
     */
    private static final class Params {
        static final String ACTION = "action";         // Action parameter
        static final String ID = "food_id";           // Menu item ID
        static final String NAME = "food_name";       // Menu item name
        static final String DESCRIPTION = "food_description";  // Menu item description
        static final String PRICE = "food_price";     // Menu item price
        static final String CATEGORY = "food_category";  // Menu item category
        static final String AVAILABILITY = "food_availability";  // Menu item availability
    }

    /**
     * Nested class containing constants for action values.
     */
    private static final class Actions {
        static final String ADD = "add";
        static final String CREATE = "create";    // Create action
        static final String UPDATE = "update";    // Update action
        static final String DELETE = "delete";    // Delete action
        static final String EDIT = "edit";        // Edit action
    }

    /**
     * Nested class containing constants for session attribute names.
     */
    private static final class Attrs {
        static final String SUCCESS = "successMessage";   // Success message attribute
        static final String ERROR = "errorMessage";       // Error message attribute
        static final String MENU_ITEMS = "menuItems";     // Menu items list attribute
        static final String MENU_ITEM = "menuItem";       // Single menu item attribute
    }

    /**
     * Nested class containing valid values for certain fields.
     */
    private static final class ValidValues {
        // Valid categories for menu items
        static final List<String> CATEGORIES = Arrays.asList("meals", "snacks", "sweets", "drinks");
        // Valid availability statuses
        static final List<String> AVAILABILITY = Arrays.asList("available", "out_of_order");
    }

    // Data Access Object for menu items
    private MenuItemDAO menuItemDAO;

    /**
     * Initializes the servlet by creating an instance of MenuItemDAO.
     *
     * <p>This method is called by the servlet container when the servlet is first loaded.
     */
    @Override
    public void init() {
        this.menuItemDAO = new MenuItemDAO();
    }

    /**
     * Handles HTTP GET requests.
     *
     * <p>Routes requests based on the 'action' parameter:
     * <ul>
     *   <li>If action is 'edit', shows the edit form</li>
     *   <li>Otherwise, shows the full menu item list</li>
     * </ul>
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter(Params.ACTION);

            if (Actions.EDIT.equals(action)) {
                showEditForm(request, response);
            } else if (Actions.ADD.equals(action)) {
                showAddForm(request, response);
            } else {
                showMenuItems(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, "Error processing request: " + e.getMessage());
        }
    }

    /**
     * Handles HTTP POST requests.
     *
     * <p>Routes requests based on the 'action' parameter:
     * <ul>
     *   <li>create - Creates a new menu item</li>
     *   <li>update - Updates an existing menu item</li>
     *   <li>delete - Deletes a menu item</li>
     *   <li>default - Shows the full menu item list</li>
     * </ul>
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter(Params.ACTION);
        HttpSession session = request.getSession();

        try {
            switch (action) {
                case Actions.CREATE -> createMenuItem(request, response, session);
                case Actions.UPDATE -> updateMenuItem(request, response, session);
                case Actions.DELETE -> deleteMenuItem(request, response, session);
                default -> showMenuItems(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, "Error processing request: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays all menu items.
     *
     * <p>This method:
     * <ol>
     *   <li>Gets all menu items from the database via MenuItemDAO</li>
     *   <li>Sets them as a request attribute</li>
     *   <li>Forwards to the menu JSP page for display</li>
     * </ol>
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database access error occurs
     */
    private void showMenuItems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<MenuItemModel> menuItems = menuItemDAO.getAllMenuItems();
        request.setAttribute(Attrs.MENU_ITEMS, menuItems);
        request.getRequestDispatcher(MENU_PAGE).forward(request, response);
    }

    /**
     * Displays the edit form for a specific menu item.
     *
     * <p>This method:
     * <ol>
     *   <li>Gets the menu item ID from the request</li>
     *   <li>Retrieves the menu item from the database</li>
     *   <li>If found, forwards to the edit JSP page</li>
     *   <li>If not found, shows an error message</li>
     * </ol>
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database access error occurs
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int itemId = Integer.parseInt(request.getParameter(Params.ID));
        MenuItemModel menuItem = menuItemDAO.getMenuItemById(itemId);

        if (menuItem != null) {
            request.setAttribute(Attrs.MENU_ITEM, menuItem);
            request.getRequestDispatcher(EDIT_PAGE).forward(request, response);
        } else {
            handleError(request, response, "Menu item not found with ID: " + itemId);
        }
    }

    /**
     * Creates a new menu item in the database.
     *
     * <p>This method:
     * <ol>
     *   <li>Extracts menu item data from the request</li>
     *   <li>Validates the data</li>
     *   <li>If validation fails, redirects back with error</li>
     *   <li>If validation passes, adds the item to the database</li>
     *   <li>Sets appropriate success/error message</li>
     *   <li>Redirects to the menu page</li>
     * </ol>
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param session  the HttpSession object
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database access error occurs
     */
    private void createMenuItem(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, SQLException {
        MenuItemModel menuItem = extractMenuItemFromRequest(request);

        if (validateMenuItem(menuItem, session)) {
            response.sendRedirect(request.getContextPath() + "/menu");
            return;
        }

        boolean isAdded = menuItemDAO.addMenuItem(menuItem);
        setOperationResultMessage(session, isAdded, "added");
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(ADD_PAGE).forward(request, response);
    }

    /**
     * Updates an existing menu item in the database.
     *
     * <p>This method:
     * <ol>
     *   <li>Gets the menu item ID from the request</li>
     *   <li>Extracts updated menu item data from the request</li>
     *   <li>Validates the data</li>
     *   <li>If validation fails, redirects back to edit form with error</li>
     *   <li>If validation passes, updates the item in the database</li>
     *   <li>Sets appropriate success/error message</li>
     *   <li>Redirects to the menu page</li>
     * </ol>
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param session  the HttpSession object
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database access error occurs
     */
    private void updateMenuItem(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, SQLException {
        int itemId = Integer.parseInt(request.getParameter(Params.ID));
        MenuItemModel menuItem = extractMenuItemFromRequest(request);
        menuItem.setFoodId(itemId);

        if (validateMenuItem(menuItem, session)) {
            response.sendRedirect(request.getContextPath() + "/menu?action=" + Actions.EDIT + "&" + Params.ID + "=" + itemId);
            return;
        }

        boolean isUpdated = menuItemDAO.updateMenuItem(menuItem);
        setOperationResultMessage(session, isUpdated, "updated");
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    /**
     * Deletes a menu item from the database.
     *
     * <p>This method:
     * <ol>
     *   <li>Gets the menu item ID from the request</li>
     *   <li>Attempts to delete the item from the database</li>
     *   <li>Sets appropriate success/error message</li>
     *   <li>Redirects to the menu page</li>
     * </ol>
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param session  the HttpSession object
     * @throws IOException if an I/O error occurs
     * @throws SQLException if a database access error occurs
     */
    private void deleteMenuItem(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, SQLException {
        int itemId = Integer.parseInt(request.getParameter(Params.ID));
        boolean isDeleted = menuItemDAO.deleteMenuItem(itemId);

        setOperationResultMessage(session, isDeleted, "deleted");
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    /**
     * Extracts menu item data from request parameters and creates a MenuItemModel object.
     *
     * @param request the HttpServletRequest object containing the parameters
     * @return MenuItemModel populated with data from the request
     */
    private MenuItemModel extractMenuItemFromRequest(HttpServletRequest request) {
        String name = request.getParameter(Params.NAME);
        String description = request.getParameter(Params.DESCRIPTION);
        BigDecimal price = new BigDecimal(request.getParameter(Params.PRICE));
        String category = request.getParameter(Params.CATEGORY);
        String availability = request.getParameter(Params.AVAILABILITY);

        return new MenuItemModel(name, description, price, category, availability);
    }

    /**
     * Validates menu item data.
     *
     * <p>Checks:
     * <ul>
     *   <li>Name is not empty</li>
     *   <li>Price is greater than zero</li>
     *   <li>Category is valid</li>
     *   <li>Availability status is valid</li>
     * </ul>
     *
     * @param menuItem the MenuItemModel to validate
     * @param session the HttpSession to store error messages if validation fails
     * @return true if validation fails, false if validation passes
     */
    private boolean validateMenuItem(MenuItemModel menuItem, HttpSession session) {
        if (menuItem.getFoodName() == null || menuItem.getFoodName().trim().isEmpty()) {
            session.setAttribute(Attrs.ERROR, "Food name is required.");
            return true;
        }

        if (menuItem.getFoodPrice().compareTo(BigDecimal.ZERO) <= 0) {
            session.setAttribute(Attrs.ERROR, "Price must be greater than zero.");
            return true;
        }

        if (!ValidValues.CATEGORIES.contains(menuItem.getFoodCategory())) {
            session.setAttribute(Attrs.ERROR, "Invalid food category.");
            return true;
        }

        if (!ValidValues.AVAILABILITY.contains(menuItem.getFoodAvailability())) {
            session.setAttribute(Attrs.ERROR, "Invalid availability status.");
            return true;
        }

        return false;
    }

    /**
     * Sets a success or error message in the session based on the operation result.
     *
     * @param session the HttpSession to store the message
     * @param success whether the operation was successful
     * @param operation the name of the operation performed (added/updated/deleted)
     */
    private void setOperationResultMessage(HttpSession session, boolean success, String operation) {
        if (success) {
            session.setAttribute(Attrs.SUCCESS, "Menu item " + operation + " successfully!");
        } else {
            session.setAttribute(Attrs.ERROR, "Menu item " + operation + " unsuccessfully!.");
        }
    }

    /**
     * Handles errors by setting an error message in the session and redirecting.
     *
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param errorMessage the error message to display
     * @throws IOException if an I/O error occurs
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws IOException {
        request.getSession().setAttribute(Attrs.ERROR, errorMessage);
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    /**
     * Cleans up resources when the servlet is destroyed.
     */
    @Override
    public void destroy() {
        // Clean up resources if needed
    }
}