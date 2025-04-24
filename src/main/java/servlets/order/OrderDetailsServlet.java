package servlets.order;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrderModel;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "OrderDetailsServlet",
        value = "/order/details",
        description = "Servlet for displaying order details"
)
public class OrderDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(UserConstant.ATTR_USER) == null) {
            response.sendRedirect(request.getContextPath() + UserConstant.LOGIN_PATH);
            return;
        }

        UserModel currentUser = (UserModel) session.getAttribute(UserConstant.ATTR_USER);
        String orderIdStr = request.getParameter("orderId");

        if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user/profile");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            OrderModel order = OrderDAO.getOrderById(orderId);

            // Security check - only allow users to view their own orders
            if (order == null || order.getUserId() != currentUser.getUserId()) {
                request.setAttribute("NOTIFICATION", "You do not have permission to view this order");
                request.getRequestDispatcher(UserConstant.PROFILE_PAGE).forward(request, response);
                return;
            }

            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/order/order-details.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("NOTIFICATION", "Invalid order ID format");
            request.getRequestDispatcher(UserConstant.PROFILE_PAGE).forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("NOTIFICATION", "Database error: " + e.getMessage());
            request.getRequestDispatcher(UserConstant.PROFILE_PAGE).forward(request, response);
        }
    }
}