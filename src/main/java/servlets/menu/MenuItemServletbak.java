// servlets/menu/item/MenuItemServlet.java
package servlets.menu;

import dao.MenuItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MenuItemModel;
import servlets.menu.handler.MenuItemHandler;
import servlets.menu.handler.MenuItemFormHandler;
import servlets.menu.util.MenuItemConstants;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "MenuItemServlet",
        value = "/menu",
        description = "Handles CRUD operations for menu items"
)
public class MenuItemServletbak extends HttpServlet {
    private MenuItemHandler menuItemHandler;

    @Override
    public void init() {
        this.menuItemHandler = new MenuItemHandler(new MenuItemDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter(MenuItemConstants.Params.ACTION);

            if (MenuItemConstants.Actions.EDIT.equals(action)) {
                handleEditAction(request, response);
            } else if (MenuItemConstants.Actions.ADD.equals(action)) {
                MenuItemFormHandler.showAddForm(request, response);
            } else {
                menuItemHandler.showMenuItems(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, "Error processing request: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter(MenuItemConstants.Params.ACTION);
        HttpSession session = request.getSession();

        try {
            switch (action) {
                case MenuItemConstants.Actions.CREATE ->
                        menuItemHandler.createMenuItem(request, response, session);
                case MenuItemConstants.Actions.UPDATE ->
                        menuItemHandler.updateMenuItem(request, response, session);
                case MenuItemConstants.Actions.DELETE ->
                        menuItemHandler.deleteMenuItem(request, response, session);
                default -> menuItemHandler.showMenuItems(request, response);
            }
        } catch (Exception e) {
            handleError(request, response, "Error processing request: " + e.getMessage());
        }
    }

    private void handleEditAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int itemId = Integer.parseInt(request.getParameter(MenuItemConstants.Params.ID));
        MenuItemModel menuItem = menuItemHandler.getMenuItemById(itemId);

        if (menuItem != null) {
            MenuItemFormHandler.showEditForm(request, response, menuItem);
        } else {
            handleError(request, response, "Menu item not found with ID: " + itemId);
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws IOException {
        request.getSession().setAttribute(MenuItemConstants.Attrs.ERROR, errorMessage);
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
    }
}