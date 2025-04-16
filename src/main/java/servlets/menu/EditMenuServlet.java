package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;
import servlets.menu.helpers.MenuRequestHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(
        name = "EditMenuServlet",
        value = "/menu/edit",
        description = "Edit menu item"
)
public class EditMenuServlet extends BaseMenuServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            int foodId = MenuRequestHandler.extractItemIdFromRequest(request);
            MenuItemModel menuItem = menuItemDAO.getMenuItemById(foodId);

            if (menuItem != null) {
                request.setAttribute(MenuConstant.ATTR_MENU_ITEM, menuItem);
                forwardToView(request, response, MenuConstant.MENU_EDIT_PAGE);
            } else {
                redirectToMenu(response, request);
            }
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            MenuItemModel updatedItem = MenuRequestHandler.extractMenuItemFromRequestWithId(request);

            boolean success = menuItemDAO.updateMenuItem(updatedItem);
            String notification = success ? MenuConstant.MSG_UPDATE_SUCCESS : MenuConstant.MSG_UPDATE_FAILURE;

            setNotification(request, notification);
            redirectToMenu(response, request);
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}