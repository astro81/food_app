package servlets.order;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/confirm-order")
public class ConfirmOrderServlet extends BaseOrderServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserModel user = getAuthenticatedUser(request);
        if (user == null) {
            redirectToLogin(response, request);
            return;
        }

        try {
            boolean success = OrderDAO.confirmOrder(user.getUserId());
            setNotification(request, success ? OrderConstant.MSG_CONFIRM_SUCCESS : OrderConstant.MSG_NO_ITEMS);
            redirectToMenu(response, request);

        } catch (SQLException e) {
            handleError(request, response, e, "/menu");
        }
    }
}