package servlets.menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.MenuItemModel;
import model.UserModel;
import servlets.menu.helpers.MenuRequestHandler;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "AddMenuServlet",
        value = "/menu/add",
        description = "Servlet for adding new menu items to the system."
)
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class AddMenuServlet extends BaseMenuServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAuthorized(request)) {
            unauthorized(request, response);
            return;
        }

        // Set vendor flag for form customization
        request.setAttribute("isVendor", isVendor(request));
        forwardToView(request, response, MenuConstant.MENU_EDIT_PAGE);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAuthorized(request)) {
            unauthorized(request, response);
            return;
        }

        try {
            UserModel user = getCurrentUser(request);
            MenuItemModel newItem = MenuRequestHandler.extractMenuItemFromRequest(request);

            // Set vendor ID for new items
            if (isVendor(request)) {
                newItem.setVendorId(user.getUserId());
            } else if (isAdmin(request)) {
                // Admins can optionally set vendor ID via request parameter
                String vendorIdParam = request.getParameter("vendor_id");
                if (vendorIdParam != null && !vendorIdParam.isEmpty()) {
                    newItem.setVendorId(Integer.parseInt(vendorIdParam));
                }
            }

            boolean success = menuItemDAO.addMenuItem(newItem);
            String notification = success ? MenuConstant.MSG_ADD_SUCCESS : MenuConstant.MSG_ADD_FAILURE;

            setNotification(request, notification);
            redirectToMenu(response, request);
        } catch (SQLException e) {
            handleError(request, response, e);
        } catch (NumberFormatException e) {
            request.setAttribute(MenuConstant.MSG_NOTIFICATION, "Invalid vendor ID format");
            forwardToView(request, response, MenuConstant.MENU_EDIT_PAGE);
        }
    }
}