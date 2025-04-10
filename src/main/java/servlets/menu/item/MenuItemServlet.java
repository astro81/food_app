package servlets.menu.item;

import controller.MenuItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;

import java.io.IOException;
import java.io.Serial;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet implementation for menu item management.
 * <p>
 * This servlet handles the creation of new menu items through HTTP POST requests. Key functionalities include:
 * <ul>
 *   <li>Processing menu item creation form submissions</li>
 *   <li>Validating menu item data (price, category, availability)</li>
 *   <li>Persisting new menu items to the database</li>
 *   <li>Providing user feedback through session attributes</li>
 * </ul>
 *
 * <p><strong>Security and Validation Considerations:</strong>
 * <ul>
 *   <li>Validates price format (must be a valid number)</li>
 *   <li>Restricts categories to predefined values (meals, snacks, sweets, drinks)</li>
 *   <li>Restricts availability to predefined states (available, out_of_order)</li>
 *   <li>Uses session attributes for error/success messaging</li>
 * </ul>
 */
@WebServlet(
        name = "MenuItemServlet",
        value = "/menu",
        description = "Handles creation and management of menu items in the system"
)
public class MenuItemServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Path to the menu display page where users are redirected after processing.
     */
//    private static final String MENU_PAGE = "/menu.jsp";
    private static final String MENU_PAGE = "/WEB-INF/menu.jsp";

    // Form parameter names
    private static final String ITEM_FORM_PARAM_NAME = "food_name";
    private static final String ITEM_FORM_PARAM_DESC = "food_description";
    private static final String ITEM_FORM_PARAM_PRICE = "food_price";
    private static final String ITEM_FORM_PARAM_CATEGORY = "food_category";
    private static final String ITEM_FORM_PARAM_AVAILABILITY = "food_availability";

    // Session attribute names for feedback messages
    private static final String ATTR_SUCCESS = "successMessage";
    private static final String ATTR_ERROR = "errorMessage";

    // Data access object for menu items
    private MenuItemDAO menuItemDAO;

    /**
     * Initializes the servlet and its data access components.
     * <p>
     * Creates a new MenuItemDAO instance for database operations. This method is
     * guaranteed to be called once before the servlet handles any requests.
     */
    @Override
    public void init() {
        this.menuItemDAO = new MenuItemDAO();
    }

    // Add this method to handle GET requests
    /**
     * Handles HTTP GET requests for viewing menu items.
     *
     * Retrieves all menu items from the database and forwards them to the JSP page
     * for display.
     *
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<MenuItemModel> menuItems = menuItemDAO.getAllMenuItems();
            System.out.println("menu" + menuItems);
            request.setAttribute("menuItems", menuItems);
            request.getRequestDispatcher(MENU_PAGE).forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute(ATTR_ERROR, "Error retrieving menu items: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + MENU_PAGE);
        }
    }

    /**
     * Handles HTTP POST requests for menu item creation.
     * <p>
     * Processes the menu item creation attempt through these steps:
     * <ol>
     *   <li>Extracts menu item data from request parameters</li>
     *   <li>Validates price format (must be parsable as double)</li>
     *   <li>Validates category against allowed values</li>
     *   <li>Validates availability status against allowed values</li>
     *   <li>Creates new MenuItemModel instance</li>
     *   <li>Attempts to persist menu item via MenuItemDAO</li>
     *   <li>Sets appropriate success/error message in session</li>
     *   <li>Redirects back to menu page with status</li>
     * </ol>
     *
     * @param request  the HttpServletRequest object containing form data
     * @param response the HttpServletResponse object
     * @throws ServletException if form processing fails
     * @throws IOException if redirection fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract form data
        String menuItemName = request.getParameter(ITEM_FORM_PARAM_NAME);
        String menuItemDesc = request.getParameter(ITEM_FORM_PARAM_DESC);
        String priceStr = request.getParameter(ITEM_FORM_PARAM_PRICE);
        String menuItemCategory = request.getParameter(ITEM_FORM_PARAM_CATEGORY);
        String menuItemAvailability = request.getParameter(ITEM_FORM_PARAM_AVAILABILITY);

        try {
            // Validate and parse price
            BigDecimal menuItemPrice;
            try {
                menuItemPrice = new BigDecimal(priceStr);
            } catch (NumberFormatException e) {
                request.getSession().setAttribute(ATTR_ERROR, "Invalid price format. Please enter a valid number.");
                response.sendRedirect(request.getContextPath() + MENU_PAGE);
                return;
            }

            // Ensure the price is positive
            if (menuItemPrice.compareTo(BigDecimal.ZERO) <= 0) {
                request.getSession().setAttribute(ATTR_ERROR, "Price must be greater than zero.");
                response.sendRedirect(request.getContextPath() + MENU_PAGE);
                return;
            }

            // Validate category
            if (!Arrays.asList("meals", "snacks", "sweets", "drinks").contains(menuItemCategory)) {
                request.getSession().setAttribute(ATTR_ERROR, "Invalid food category");
                response.sendRedirect(request.getContextPath() + MENU_PAGE);
                return;
            }

            // Validate availability
            if (!Arrays.asList("available", "out_of_order").contains(menuItemAvailability)) {
                request.getSession().setAttribute(ATTR_ERROR, "Invalid availability status");
                response.sendRedirect(request.getContextPath() + MENU_PAGE);
                return;
            }

            // Create menu item model
            MenuItemModel menuItem = new MenuItemModel(
                    menuItemName,
                    menuItemDesc,
                    menuItemPrice,
                    menuItemCategory,
                    menuItemAvailability
            );

            // Add item to database
            boolean isAdded = menuItemDAO.addMenuItem(menuItem);

            if (isAdded) {
                request.getSession().setAttribute(ATTR_SUCCESS, "Menu item added successfully!");
            } else {
                request.getSession().setAttribute(ATTR_ERROR, "Failed to add menu item. Please try again.");
            }

            response.sendRedirect(request.getContextPath() + MENU_PAGE);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute(ATTR_ERROR, "Invalid price format. Please enter a valid number.");
            response.sendRedirect(request.getContextPath() + MENU_PAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute(ATTR_ERROR, "Database error occurred. Please try again later.");
            response.sendRedirect(request.getContextPath() + MENU_PAGE);
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute(ATTR_ERROR, "An unexpected error occurred. Please try again.");
            response.sendRedirect(request.getContextPath() + MENU_PAGE);
        }
    }

    /**
     * Cleanup method called during servlet destruction.
     * <p>
     * Currently no resources require explicit cleanup. Maintained for
     * future compatibility and consistent servlet lifecycle management.
     */
    @Override
    public void destroy() {
        // No resources to clean up in current implementation
    }
}
