package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/menu/delete")
public class DeleteMenuServlet extends BaseMenuServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            int foodId = Integer.parseInt(request.getParameter(MenuConstant.PARAM_FOOD_ID));

            boolean success = menuItemDAO.deleteMenuItem(foodId);
            String notification = success ? MenuConstant.MSG_DELETE_SUCCESS : MenuConstant.MSG_DELETE_FAILURE;

            request.getSession().setAttribute(MenuConstant.MSG_NOTIFICATION, notification);
            response.sendRedirect(request.getContextPath() + "/menu");
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }
}