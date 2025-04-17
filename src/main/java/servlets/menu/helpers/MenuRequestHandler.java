package servlets.menu.helpers;

import jakarta.servlet.http.HttpServletRequest;
import model.MenuItemModel;
import servlets.menu.MenuConstant;

import java.math.BigDecimal;

/**
 * Utility class for handling menu-related request parameter extraction.
 * Provides static methods to construct MenuItemModel objects from HTTP requests.
 */
public class MenuRequestHandler {

    /**
     * Extracts menu item data from request parameters and creates a new MenuItemModel.
     * Does not include the item ID (for new item creation).
     *
     * @param request The HttpServletRequest containing menu item parameters
     * @return New MenuItemModel populated with request data
     * @throws NumberFormatException If price parameter cannot be parsed to BigDecimal
     */
    public static MenuItemModel extractMenuItemFromRequest(HttpServletRequest request) {
        // Extract all basic menu item properties from request parameters
        String foodName = request.getParameter(MenuConstant.PARAM_FOOD_NAME);
        String foodDescription = request.getParameter(MenuConstant.PARAM_FOOD_DESC);
        BigDecimal foodPrice = new BigDecimal(request.getParameter(MenuConstant.PARAM_FOOD_PRICE));
        String foodCategory = request.getParameter(MenuConstant.PARAM_FOOD_CATEGORY);
        String foodAvailability = request.getParameter(MenuConstant.PARAM_FOOD_AVAILABILITY);

        // Create and return new menu item model
        return new MenuItemModel(foodName, foodDescription, foodPrice, foodCategory, foodAvailability);
    }

    /**
     * Extracts menu item data including ID from request parameters.
     * Creates a MenuItemModel with all properties including ID (for existing items).
     *
     * @param request The HttpServletRequest containing menu item parameters
     * @return MenuItemModel populated with all properties including ID
     * @throws NumberFormatException If ID or price parameters cannot be parsed
     */
    public static MenuItemModel extractMenuItemFromRequestWithId(HttpServletRequest request) {
        // Extract item ID from request
        int foodId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));

        // Create base menu item model
        MenuItemModel item = extractMenuItemFromRequest(request);

        // Set the extracted ID
        item.setFoodId(foodId);
        return item;
    }

    /**
     * Extracts the menu item ID from request parameters.
     *
     * @param request The HttpServletRequest containing the item ID parameter
     * @return The extracted menu item ID as integer
     * @throws NumberFormatException If ID parameter cannot be parsed to integer
     */
    public static int extractItemIdFromRequest(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
    }
}