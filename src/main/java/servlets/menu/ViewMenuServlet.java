package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(
        name = "ViewMenuServlet",
        value = "/menu",
        description = "View menu items list"
)
public class ViewMenuServlet extends BaseMenuServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("isAdmin", isAdmin(request));
            List<MenuItemModel> menuItems = menuItemDAO.getAllMenuItems();
            request.setAttribute(MenuConstant.ATTR_MENU_ITEMS, menuItems);
            forwardToView(request, response, MenuConstant.MENU_LIST_PAGE);
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}