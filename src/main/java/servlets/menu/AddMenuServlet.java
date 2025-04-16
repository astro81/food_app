package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/menu/add")
public class AddMenuServlet extends BaseMenuServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }
        request.getRequestDispatcher(MenuConstant.MENU_EDIT_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            String foodName = request.getParameter(MenuConstant.PARAM_FOOD_NAME);
            String foodDescription = request.getParameter(MenuConstant.PARAM_FOOD_DESC);
            BigDecimal foodPrice = new BigDecimal(request.getParameter(MenuConstant.PARAM_FOOD_PRICE));
            String foodCategory = request.getParameter(MenuConstant.PARAM_FOOD_CATEGORY);
            String foodAvailability = request.getParameter(MenuConstant.PARAM_FOOD_AVAILABILITY);

            MenuItemModel newItem = new MenuItemModel(foodName, foodDescription, foodPrice, foodCategory, foodAvailability);

            boolean success = menuItemDAO.addMenuItem(newItem);
            String notification = success ? MenuConstant.MSG_ADD_SUCCESS : MenuConstant.MSG_ADD_FAILURE;

            request.getSession().setAttribute(MenuConstant.MSG_NOTIFICATION, notification);
            response.sendRedirect(request.getContextPath() + "/menu");
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}