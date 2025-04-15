// servlets/menu/item/handler/MenuItemFormHandler.java
package servlets.menu.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;
import servlets.menu.util.MenuItemConstants;

import java.io.IOException;
import java.math.BigDecimal;

public class MenuItemFormHandler {
    public static MenuItemModel extractMenuItemFromRequest(HttpServletRequest request) {
        String name = request.getParameter(MenuItemConstants.Params.NAME);
        String description = request.getParameter(MenuItemConstants.Params.DESCRIPTION);
        BigDecimal price = new BigDecimal(request.getParameter(MenuItemConstants.Params.PRICE));
        String category = request.getParameter(MenuItemConstants.Params.CATEGORY);
        String availability = request.getParameter(MenuItemConstants.Params.AVAILABILITY);

        return new MenuItemModel(name, description, price, category, availability);
    }

    public static void setOperationResultMessage(HttpServletRequest request, boolean success, String operation) {
        if (success) {
            request.getSession().setAttribute(MenuItemConstants.Attrs.SUCCESS, "Menu item " + operation + " successfully!");
        } else {
            request.getSession().setAttribute(MenuItemConstants.Attrs.ERROR, "Menu item " + operation + " unsuccessfully!.");
        }
    }

    public static void showEditForm(HttpServletRequest request, HttpServletResponse response, MenuItemModel menuItem)
            throws ServletException, IOException {
        request.setAttribute(MenuItemConstants.Attrs.MENU_ITEM, menuItem);
        request.getRequestDispatcher(MenuItemConstants.EDIT_PAGE).forward(request, response);
    }

    public static void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(MenuItemConstants.ADD_PAGE).forward(request, response);
    }
}