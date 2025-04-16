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
        forwardToView(request, response, MenuConstant.MENU_LIST_PAGE);
    }

    protected void unauthorized(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(MenuConstant.MSG_NOTIFICATION, MenuConstant.MSG_UNAUTHORIZED);
        forwardToView(request, response, MenuConstant.MENU_LIST_PAGE);
    }

    protected void redirectToMenu(HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    protected void forwardToView(HttpServletRequest request, HttpServletResponse response, String view)
            throws ServletException, IOException {
        request.getRequestDispatcher(view).forward(request, response);
    }

    protected void setNotification(HttpServletRequest request, String message) {
        request.getSession().setAttribute(MenuConstant.MSG_NOTIFICATION, message);
    }
}