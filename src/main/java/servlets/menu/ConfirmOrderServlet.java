package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;
import servlets.user.UserConstant;
import dao.OrderDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/confirm-order")
public class ConfirmOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(UserConstant.ATTR_USER) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        UserModel user = (UserModel) session.getAttribute(UserConstant.ATTR_USER);

        try {
            boolean success = OrderDAO.confirmOrder(user.getUserId());
            if (success) {
                request.getSession().setAttribute("NOTIFICATION", "Order confirmed successfully!");
            } else {
                request.getSession().setAttribute("NOTIFICATION", "No items to confirm in your order!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.getSession().setAttribute("NOTIFICATION", "Error confirming order: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/menu");
    }
}