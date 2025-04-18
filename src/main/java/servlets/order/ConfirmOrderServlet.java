package servlets.order;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/confirm-order")
public class ConfirmOrderServlet extends BaseOrderServlet {
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
                setNotification(request, OrderConstant.MSG_CONFIRM_SUCCESS);
            } else {
                setNotification(request, OrderConstant.MSG_NO_ITEMS);
            }
        } catch (SQLException e) {
            handleError(request, response, e);
            return;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/menu");
    }
}