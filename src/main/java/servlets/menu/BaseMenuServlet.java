package servlets.menu;

import dao.MenuItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;
import java.sql.SQLException;

public abstract class BaseMenuServlet extends HttpServlet {
    protected MenuItemDAO menuItemDAO;

    @Override
    public void init() {
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
        request.setAttribute(MenuConstant.MSG_NOTIFICATION, MenuConstant.MSG_DB_ERROR + e.getMessage());
        request.getRequestDispatcher(MenuConstant.MENU_LIST_PAGE).forward(request, response);
    }

    protected void unauthorized(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(MenuConstant.MSG_NOTIFICATION, MenuConstant.MSG_UNAUTHORIZED);
        request.getRequestDispatcher(MenuConstant.MENU_LIST_PAGE).forward(request, response);
    }
}