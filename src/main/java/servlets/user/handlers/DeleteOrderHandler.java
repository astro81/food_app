package servlets.user.handlers;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteOrderHandler implements ProfileHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       UserModel currentUser, HttpSession session)
            throws ServletException, IOException, SQLException {

        String orderIdParam = request.getParameter("orderId");

        if (orderIdParam == null || orderIdParam.trim().isEmpty()) {
            request.setAttribute("NOTIFICATION", "Order ID is required");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);
            boolean deleted = OrderDAO.deleteOrder(orderId, currentUser.getUserId());

            if (deleted) {
                response.sendRedirect(request.getContextPath() +
                        "/user/profile?notification=Order deleted successfully");
            } else {
                request.setAttribute("NOTIFICATION", "Failed to delete order. It may not exist or you don't have permission.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("NOTIFICATION", "Invalid order ID format");
        }
    }
}