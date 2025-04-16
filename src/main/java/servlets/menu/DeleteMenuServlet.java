package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlets.menu.helpers.MenuRequestHandler;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "DeleteMenuServlet",
        value = "/menu/delete",
        description = "Delete menu item"
)
public class DeleteMenuServlet extends BaseMenuServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            int foodId = MenuRequestHandler.extractItemIdFromRequest(request);

            boolean success = menuItemDAO.deleteMenuItem(foodId);
            String notification = success ? MenuConstant.MSG_DELETE_SUCCESS : MenuConstant.MSG_DELETE_FAILURE;

            setNotification(request, notification);
            redirectToMenu(response, request);
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}