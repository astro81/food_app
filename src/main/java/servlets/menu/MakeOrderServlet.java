package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrderModel;
import model.UserModel;
import servlets.user.UserConstant;
import dao.OrderDAO;

import java.io.IOException;

@WebServlet("/make-order")
public class MakeOrderServlet extends HttpServlet {
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
            session.setAttribute("NOTIFICATION", "No items in your order!");
            response.sendRedirect(request.getContextPath() + "/menu");
            return;
        }

        request.setAttribute("order", order);
        request.getRequestDispatcher("/WEB-INF/menu/order-preview.jsp").forward(request, response);
    }
}