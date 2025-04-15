package servlets.menu.validator;

import jakarta.servlet.http.HttpSession;
import model.MenuItemModel;
import servlets.menu.util.MenuItemConstants;

public class MenuItemValidator {
    public static boolean validateMenuItem(MenuItemModel menuItem, HttpSession session) {
        if (menuItem.getFoodName() == null || menuItem.getFoodName().trim().isEmpty()) {
            session.setAttribute(MenuItemConstants.Attrs.ERROR, "Food name is required.");
            return true;
        }

        if (menuItem.getFoodPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            session.setAttribute(MenuItemConstants.Attrs.ERROR, "Price must be greater than zero.");
            return true;
        }

        if (!MenuItemConstants.ValidValues.CATEGORIES.contains(menuItem.getFoodCategory())) {
            session.setAttribute(MenuItemConstants.Attrs.ERROR, "Invalid food category.");
            return true;
        }

        if (!MenuItemConstants.ValidValues.AVAILABILITY.contains(menuItem.getFoodAvailability())) {
            session.setAttribute(MenuItemConstants.Attrs.ERROR, "Invalid availability status.");
            return true;
        }

        return false;
    }
}