package servlets.order;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Servlet implementation for confirming a user's pending order.
 * Handles POST requests to finalize the order process.
 */
@WebServlet(
        name = "ConfirmOrderServlet",
        value = "/confirm-order",
        description = "Servlet for confirming and finalizing a user's pending order."
)
public class ConfirmOrderServlet extends BaseOrderServlet {

    /**
     * Handles POST requests to confirm the user's order.
     * Updates order status in database and redirects with appropriate notification.
     *
     * @param request  The HTTP request
     * @param response The HTTP response
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verify user is authenticated
        if (!checkAuthentication(request, response)) {
            return;
        }

        try {
            // Confirm the order for the current user
            UserModel user = getAuthenticatedUser(request);
            boolean success = OrderDAO.confirmOrder(user.getUserId());

            // Set notification based on success and redirect
            String message = success ? OrderConstant.MSG_CONFIRM_SUCCESS : OrderConstant.MSG_NO_ITEMS;
            setNotification(request, message);
            redirectToMenu(response, request);

        } catch (SQLException e) {
            handleError(request, response, e, "/menu");
        }
    }
}