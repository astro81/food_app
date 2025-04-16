package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/menu/filter")
public class FilterMenuServlet extends BaseMenuServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("isAdmin", isAdmin(request));
            String category = request.getParameter(MenuConstant.ATTR_FILTER_CATEGORY);
            String availability = request.getParameter(MenuConstant.ATTR_FILTER_AVAILABILITY);
            List<MenuItemModel> menuItems;

            if (category != null && !category.isEmpty()) {
                menuItems = menuItemDAO.getMenuItemsByCategory(category);
                request.setAttribute(MenuConstant.ATTR_FILTER_CATEGORY, category);
            } else if (availability != null && !availability.isEmpty()) {
                menuItems = menuItemDAO.getMenuItemsByAvailability(availability);
                request.setAttribute(MenuConstant.ATTR_FILTER_AVAILABILITY, availability);
            } else {
                menuItems = menuItemDAO.getAllMenuItems();
            }

            request.setAttribute(MenuConstant.ATTR_MENU_ITEMS, menuItems);
            request.getRequestDispatcher(MenuConstant.MENU_LIST_PAGE).forward(request, response);
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}