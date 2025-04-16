package servlets.menu.helpers;

import jakarta.servlet.http.HttpServletRequest;
import model.MenuItemModel;
import servlets.menu.MenuConstant;

import java.math.BigDecimal;

public class MenuRequestHandler {
    public static MenuItemModel extractMenuItemFromRequest(HttpServletRequest request) {
        String foodName = request.getParameter(MenuConstant.PARAM_FOOD_NAME);
        String foodDescription = request.getParameter(MenuConstant.PARAM_FOOD_DESC);
        BigDecimal foodPrice = new BigDecimal(request.getParameter(MenuConstant.PARAM_FOOD_PRICE));
        String foodCategory = request.getParameter(MenuConstant.PARAM_FOOD_CATEGORY);
        String foodAvailability = request.getParameter(MenuConstant.PARAM_FOOD_AVAILABILITY);

        return new MenuItemModel(foodName, foodDescription, foodPrice, foodCategory, foodAvailability);
    }

    public static MenuItemModel extractMenuItemFromRequestWithId(HttpServletRequest request) {
        int foodId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
        MenuItemModel item = extractMenuItemFromRequest(request);
        item.setFoodId(foodId);
        return item;
    }

    public static int extractItemIdFromRequest(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
    }
}