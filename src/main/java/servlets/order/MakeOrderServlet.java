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

/**
 * Servlet implementation for previewing a user's pending order.
 * Handles GET requests to show the current order items before confirmation.
 */
@WebServlet(
        name = "MakeOrderServlet",
        value = "/make-order",
        description = "Servlet for displaying a user's pending order before confirmation."
)
public class MakeOrderServlet extends BaseOrderServlet {

    /**
     * Handles GET requests to display the user's pending order.
     * Redirects to menu if order is empty or user is not authenticated.
     *
     * @param request  The HTTP request
     * @param response The HTTP response
     * @throws ServletException If a servlet-specific error occurs
     * @throws IOException      If an I/O error occurs
     */
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

    /**
     * Checks if an order is empty or null.
     *
     * @param order The order to check
     * @return true if the order is empty or null, false otherwise
     */
    private boolean isEmptyOrder(OrderModel order) {
        return order == null || order.getItems().isEmpty();
    }
}