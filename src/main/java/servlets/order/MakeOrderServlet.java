package servlets.order;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderModel;
import model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "MakeOrderServlet",
        value = "/make-order",
        description = "Servlet for displaying a user's pending order before confirmation."
)
public class MakeOrderServlet extends BaseOrderServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify user is authenticated
        if (!checkAuthentication(request, response)) {
            return;
        }

        try {
            // Get the user's pending order
            UserModel user = getAuthenticatedUser(request);
            OrderModel order = OrderDAO.getPendingOrder(user.getUserId());

            // Check if order has items
            if (isEmptyOrder(order)) {
                setNotification(request, OrderConstant.MSG_NO_ITEMS);
                redirectToMenu(response, request);
                return;
            }

            // Prepare view attributes and forward to preview page
            request.setAttribute("order", order);
            forwardToView(request, response, OrderConstant.ORDER_PREVIEW_PAGE);

        } catch (SQLException e) {
            handleError(request, response, e, "/menu");
        }
    }

    private boolean isEmptyOrder(OrderModel order) {
        return order == null || order.getItemsWithQuantities().isEmpty();
    }
}