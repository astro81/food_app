package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MenuItemModel;
import model.UserModel;
import servlets.user.UserConstant;
import dao.OrderDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(
        name = "ViewMenuServlet",
        value = "/menu",
        description = "Servlet for viewing menu items."
)
public class ViewMenuServlet extends BaseMenuServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UserModel user = getCurrentUser(request);
            List<MenuItemModel> menuItems;

            // Set role flags for view customization
            request.setAttribute("isAdmin", isAdmin(request));
            request.setAttribute("isVendor", isVendor(request));

            if (isVendor(request)) {
                // Vendors only see their own items
                menuItems = menuItemDAO.getMenuItemsByVendor(user.getUserId());
            } else {
                // Admins and customers see all items
                menuItems = menuItemDAO.getAllMenuItems();
            }

            request.setAttribute(MenuConstant.ATTR_MENU_ITEMS, menuItems);
            forwardToView(request, response, MenuConstant.MENU_LIST_PAGE);
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int itemId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
            MenuItemModel menuItem = menuItemDAO.getMenuItemById(itemId);

            if (menuItem == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Menu item not found");
                return;
            }

            HttpSession session = request.getSession(false);
            UserModel user = (session != null) ? (UserModel) session.getAttribute(UserConstant.ATTR_USER) : null;

            if (user != null) {
                OrderDAO.createOrder(user.getUserId(), menuItem);
            } else {
                request.setAttribute(MenuConstant.MSG_NOTIFICATION, "Please login to place orders");
                forwardToView(request, response, MenuConstant.MENU_LIST_PAGE);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid menu item ID");
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}