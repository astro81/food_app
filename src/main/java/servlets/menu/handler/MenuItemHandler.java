// servlets/menu/item/handler/MenuItemHandler.java
package servlets.menu.handler;

import dao.MenuItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MenuItemModel;
import servlets.menu.util.MenuItemConstants;
import servlets.menu.validator.MenuItemValidator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MenuItemHandler {
    private final MenuItemDAO menuItemDAO;

    public MenuItemHandler(MenuItemDAO menuItemDAO) {
        this.menuItemDAO = menuItemDAO;
    }

    public MenuItemModel getMenuItemById(int id) throws SQLException {
        return menuItemDAO.getMenuItemById(id);
    }

    public void showMenuItems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<MenuItemModel> menuItems = menuItemDAO.getAllMenuItems();
        request.setAttribute(MenuItemConstants.Attrs.MENU_ITEMS, menuItems);
        request.getRequestDispatcher(MenuItemConstants.MENU_PAGE).forward(request, response);
    }

    public void createMenuItem(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, SQLException {
        MenuItemModel menuItem = MenuItemFormHandler.extractMenuItemFromRequest(request);

        if (MenuItemValidator.validateMenuItem(menuItem, session)) {
            response.sendRedirect(request.getContextPath() + "/menu");
            return;
        }

        boolean isAdded = menuItemDAO.addMenuItem(menuItem);
        MenuItemFormHandler.setOperationResultMessage(request, isAdded, "added");
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    public void updateMenuItem(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, SQLException {
        int itemId = Integer.parseInt(request.getParameter(MenuItemConstants.Params.ID));
        MenuItemModel menuItem = MenuItemFormHandler.extractMenuItemFromRequest(request);
        menuItem.setFoodId(itemId);

        if (MenuItemValidator.validateMenuItem(menuItem, session)) {
            response.sendRedirect(request.getContextPath() + "/menu?action=" +
                    MenuItemConstants.Actions.EDIT + "&" + MenuItemConstants.Params.ID + "=" + itemId);
            return;
        }

        boolean isUpdated = menuItemDAO.updateMenuItem(menuItem);
        MenuItemFormHandler.setOperationResultMessage(request, isUpdated, "updated");
        response.sendRedirect(request.getContextPath() + "/menu");
    }

    public void deleteMenuItem(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws IOException, SQLException {
        int itemId = Integer.parseInt(request.getParameter(MenuItemConstants.Params.ID));
        boolean isDeleted = menuItemDAO.deleteMenuItem(itemId);

        MenuItemFormHandler.setOperationResultMessage(request, isDeleted, "deleted");
        response.sendRedirect(request.getContextPath() + "/menu");
    }
}