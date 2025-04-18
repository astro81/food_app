package servlets.order;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;
import model.UserModel;
import servlets.menu.MenuConstant;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/order")
public class OrderServlet extends BaseOrderServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserModel user = getAuthenticatedUser(request);
        if (user == null) {
            redirectToLogin(response, request);
            return;
        }

        try {
            MenuItemModel menuItem = getMenuItemFromRequest(request);
            if (menuItem == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Menu item not found");
                return;
            }

            OrderDAO.createOrder(user.getUserId(), menuItem);
            setNotification(request, OrderConstant.MSG_ORDER_CREATED);
            redirectToMenu(response, request);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid menu item ID");
        } catch (SQLException e) {
            handleError(request, response, e, "/menu");
        }
    }

    private MenuItemModel getMenuItemFromRequest(HttpServletRequest request)
            throws NumberFormatException, SQLException {
        int itemId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
        return menuItemDAO.getMenuItemById(itemId);
    }
}