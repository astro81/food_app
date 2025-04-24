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
import java.sql.SQLException;

public abstract class BaseOrderServlet extends HttpServlet {
    protected OrderDAO orderDAO;
    protected MenuItemDAO menuItemDAO;

    @Override
    public void init() {
        this.orderDAO = new OrderDAO();
        this.menuItemDAO = new MenuItemDAO();
    }

    protected UserModel getAuthenticatedUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (UserModel) session.getAttribute(UserConstant.ATTR_USER) : null;
    }

    protected boolean isAdmin(HttpServletRequest request) {
        UserModel user = getAuthenticatedUser(request);
        return user != null && "admin".equals(user.getUserRole());
    }

    protected void handleError(HttpServletRequest request, HttpServletResponse response,
                               Exception e, String redirectPath) throws ServletException, IOException {
        e.printStackTrace();
        setNotification(request, OrderConstant.MSG_DB_ERROR + e.getMessage());
        response.sendRedirect(request.getContextPath() + redirectPath);
    }

    protected void setNotification(HttpServletRequest request, String message) {
        request.getSession().setAttribute(OrderConstant.MSG_NOTIFICATION, message);
    }

    protected void redirectToLogin(HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/login");
    }

    protected void redirectToMenu(HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    protected void forwardToView(HttpServletRequest request, HttpServletResponse response, String viewPath)
            throws ServletException, IOException {
        request.getRequestDispatcher(viewPath).forward(request, response);
    }

    protected boolean checkAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        UserModel user = getAuthenticatedUser(request);
        if (user == null) {
            redirectToLogin(response, request);
            return false;
        }
        return true;
    }
}