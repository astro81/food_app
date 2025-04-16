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
        name = "AddMenuServlet",
        value = "/menu/add",
        description = "Add menu item"
)
public class AddMenuServlet extends BaseMenuServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }
        forwardToView(request, response, MenuConstant.MENU_EDIT_PAGE);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            MenuItemModel newItem = MenuRequestHandler.extractMenuItemFromRequest(request);

            boolean success = menuItemDAO.addMenuItem(newItem);
            String notification = success ? MenuConstant.MSG_ADD_SUCCESS : MenuConstant.MSG_ADD_FAILURE;

            setNotification(request, notification);
            redirectToMenu(response, request);
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}