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
        name = "FilterMenuServlet",
        value = "/menu/filter",
        description = "Filter menu items on the basis of category and availability"
)
public class FilterMenuServlet extends BaseMenuServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("isAdmin", isAdmin(request));

            List<MenuItemModel> menuItems = getFilteredMenuItems(request);

            setRequestAttributes(request, menuItems);
            forwardToView(request, response, MenuConstant.MENU_LIST_PAGE);
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }

    private List<MenuItemModel> getFilteredMenuItems(HttpServletRequest request) throws SQLException {

        String category = request.getParameter(MenuConstant.ATTR_FILTER_CATEGORY);
        String availability = request.getParameter(MenuConstant.ATTR_FILTER_AVAILABILITY);

        if (category != null && !category.isEmpty()) {

            request.setAttribute(MenuConstant.ATTR_FILTER_CATEGORY, category);
            return menuItemDAO.getMenuItemsByCategory(category);

        } else if (availability != null && !availability.isEmpty()) {

            request.setAttribute(MenuConstant.ATTR_FILTER_AVAILABILITY, availability);
            return menuItemDAO.getMenuItemsByAvailability(availability);

        }
        return menuItemDAO.getAllMenuItems();
    }

    private void setRequestAttributes(HttpServletRequest request, List<MenuItemModel> menuItems) {
        request.setAttribute(MenuConstant.ATTR_MENU_ITEMS, menuItems);
    }
}