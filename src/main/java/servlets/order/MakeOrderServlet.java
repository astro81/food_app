package servlets.order;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrderModel;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;

@WebServlet("/make-order")
public class MakeOrderServlet extends BaseOrderServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(UserConstant.ATTR_USER) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserModel user = (UserModel) session.getAttribute(UserConstant.ATTR_USER);
        OrderModel order = OrderDAO.getPendingOrder(user.getUserId());

        if (order == null || order.getItems().isEmpty()) {
            setNotification(request, OrderConstant.MSG_NO_ITEMS);
            response.sendRedirect(request.getContextPath() + "/menu");
            return;
        }

        request.setAttribute("order", order);
        request.getRequestDispatcher(OrderConstant.ORDER_PREVIEW_PAGE).forward(request, response);
    }
}