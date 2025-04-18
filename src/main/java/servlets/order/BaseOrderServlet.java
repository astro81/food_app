package servlets.order;

import dao.MenuItemDAO;
import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;

/**
 * Abstract base servlet class providing common functionality for order-related operations.
 */
public abstract class BaseOrderServlet extends HttpServlet {
    protected OrderDAO orderDAO;
    protected MenuItemDAO menuItemDAO;

    @Override
    public void init() {
        this.orderDAO = new OrderDAO();
        this.menuItemDAO = new MenuItemDAO();
    }

    protected boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(UserConstant.ATTR_USER) != null) {
            UserModel user = (UserModel) session.getAttribute(UserConstant.ATTR_USER);
            return "admin".equals(user.getUserRole());
        }
        return false;
    }

    protected void handleError(HttpServletRequest request, HttpServletResponse response, Exception e)
            throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute(OrderConstant.MSG_NOTIFICATION, OrderConstant.MSG_DB_ERROR + e.getMessage());
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    protected void setNotification(HttpServletRequest request, String message) {
        request.getSession().setAttribute(OrderConstant.MSG_NOTIFICATION, message);
    }
}