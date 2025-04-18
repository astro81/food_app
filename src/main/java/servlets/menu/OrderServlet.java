package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(
        name = "OrderServlet",
        value = "/order",
        description = "Servlet for viewing detailed information about a specific menu item"
)
public class OrderServlet extends BaseMenuServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Fetch the menu id
            int itemId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
            // get menu on the basis of id
            MenuItemModel menuItem = menuItemDAO.getMenuItemById(itemId);

            if (menuItem == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Menu item not found");
                return;
            }

            System.out.println(menuItem.getFoodName());

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid menu item ID");
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}