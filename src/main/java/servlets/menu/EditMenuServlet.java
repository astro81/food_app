package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/menu/edit")
public class EditMenuServlet extends BaseMenuServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            int foodId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
            MenuItemModel menuItem = menuItemDAO.getMenuItemById(foodId);

            if (menuItem != null) {
                request.setAttribute(MenuConstant.ATTR_MENU_ITEM, menuItem);
                request.getRequestDispatcher(MenuConstant.MENU_EDIT_PAGE).forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/menu");
            }
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            int foodId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));
            String foodName = request.getParameter(MenuConstant.PARAM_FOOD_NAME);
            String foodDescription = request.getParameter(MenuConstant.PARAM_FOOD_DESC);
            BigDecimal foodPrice = new BigDecimal(request.getParameter(MenuConstant.PARAM_FOOD_PRICE));
            String foodCategory = request.getParameter(MenuConstant.PARAM_FOOD_CATEGORY);
            String foodAvailability = request.getParameter(MenuConstant.PARAM_FOOD_AVAILABILITY);

            MenuItemModel updatedItem = new MenuItemModel(foodId, foodName, foodDescription,
                    foodPrice, foodCategory, foodAvailability);

            boolean success = menuItemDAO.updateMenuItem(updatedItem);
            String notification = success ? MenuConstant.MSG_UPDATE_SUCCESS : MenuConstant.MSG_UPDATE_FAILURE;

            request.getSession().setAttribute(MenuConstant.MSG_NOTIFICATION, notification);
            response.sendRedirect(request.getContextPath() + "/menu");
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}