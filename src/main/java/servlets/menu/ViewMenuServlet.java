package servlets.menu;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.MenuItemModel;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

            // Get search query if present
            String searchQuery = request.getParameter("query");

            // Get filter parameters
            String category = request.getParameter("category");
            String priceRange = request.getParameter("price-range");
            String availability = request.getParameter("availability");
            String sort = request.getParameter("sort");

            if (isVendor(request)) {
                // Vendors only see their own items
                menuItems = menuItemDAO.getMenuItemsByVendor(user.getUserId());
            } else {
                // Apply filters if present
                if (searchQuery != null && !searchQuery.isEmpty()) {
                    menuItems = menuItemDAO.searchMenuItems(searchQuery);
                    request.setAttribute("searchQuery", searchQuery);
                } else if (category != null && !category.isEmpty()) {
                    menuItems = menuItemDAO.getMenuItemsByCategory(category);
                    request.setAttribute("selectedCategory", category);
                } else {
                    // Admins and customers see all items
                    menuItems = menuItemDAO.getAllMenuItems();
                }
            }

            // Apply additional filters
            if (priceRange != null && !priceRange.isEmpty()) {
                menuItems = filterByPriceRange(menuItems, priceRange);
                request.setAttribute("selectedPriceRange", priceRange);
            }

            if (availability != null && availability.equals("available")) {
                menuItems = filterByAvailability(menuItems, "Available");
                request.setAttribute("selectedAvailability", availability);
            }

            if (sort != null && !sort.isEmpty()) {
                menuItems = sortMenuItems(menuItems, sort);
                request.setAttribute("selectedSort", sort);
            }

            request.setAttribute(MenuConstant.ATTR_MENU_ITEMS, menuItems);
            forwardToView(request, response, MenuConstant.MENU_LIST_PAGE);
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }

    private List<MenuItemModel> filterByPriceRange(List<MenuItemModel> items, String priceRange) {
        if (priceRange.equals("50+")) {
            BigDecimal min = new BigDecimal("50");
            return items.stream()
                    .filter(item -> item.getFoodPrice().compareTo(min) >= 0)
                    .collect(Collectors.toList());
        }

        String[] range = priceRange.split("-");
        if (range.length == 2) {
            BigDecimal min = new BigDecimal(range[0]);
            BigDecimal max = new BigDecimal(range[1]);
            return items.stream()
                    .filter(item -> item.getFoodPrice().compareTo(min) >= 0 &&
                            item.getFoodPrice().compareTo(max) <= 0)
                    .collect(Collectors.toList());
        }
        return items;
    }

    private List<MenuItemModel> filterByAvailability(List<MenuItemModel> items, String availability) {
        return items.stream()
                .filter(item -> availability.equals(item.getFoodAvailability()))
                .collect(Collectors.toList());
    }

    private List<MenuItemModel> sortMenuItems(List<MenuItemModel> items, String sortOption) {
        switch (sortOption) {
            case "name-asc":
                items.sort(Comparator.comparing(MenuItemModel::getFoodName));
                break;
            case "name-desc":
                items.sort(Comparator.comparing(MenuItemModel::getFoodName).reversed());
                break;
            case "price-asc":
                items.sort(Comparator.comparing(MenuItemModel::getFoodPrice));
                break;
            case "price-desc":
                items.sort(Comparator.comparing(MenuItemModel::getFoodPrice).reversed());
                break;
        }
        return items;
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