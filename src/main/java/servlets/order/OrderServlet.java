package servlets.order;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MenuItemModel;
import model.UserModel;
import servlets.menu.MenuConstant;
import servlets.user.UserConstant;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "OrderServlet",
        value = "/order",
        description = "Servlet for adding menu items to an order"
)
public class OrderServlet extends BaseOrderServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Fetch the menu id
            int itemId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));

            // Get the current user from session
            HttpSession session = request.getSession(false);
            UserModel user = (session != null) ? (UserModel) session.getAttribute(UserConstant.ATTR_USER) : null;

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Get menu item and add to order
            MenuItemModel menuItem = menuItemDAO.getMenuItemById(itemId);
            if (menuItem == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Menu item not found");
                return;
            }

            OrderDAO.createOrder(user.getUserId(), menuItem);
            setNotification(request, OrderConstant.MSG_ORDER_CREATED);
            response.sendRedirect(request.getContextPath() + "/menu");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid menu item ID");
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}