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

@WebServlet("/make-order")
public class MakeOrderServlet extends BaseOrderServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserModel user = getAuthenticatedUser(request);
        if (user == null) {
            redirectToLogin(response, request);
            return;
        }

        try {
            OrderModel order = OrderDAO.getPendingOrder(user.getUserId());
            if (isEmptyOrder(order)) {
                setNotification(request, OrderConstant.MSG_NO_ITEMS);
                redirectToMenu(response, request);
                return;
            }

            request.setAttribute("order", order);
            request.getRequestDispatcher(OrderConstant.ORDER_PREVIEW_PAGE).forward(request, response);

        } catch (SQLException e) {
            handleError(request, response, e, "/menu");
        }
    }

    private boolean isEmptyOrder(OrderModel order) {
        return order == null || order.getItems().isEmpty();
    }
}