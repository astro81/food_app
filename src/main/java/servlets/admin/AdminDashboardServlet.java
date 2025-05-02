package servlets.admin;

import dao.MenuItemDAO;
import dao.OrderDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private UserDAO userDao;
    private MenuItemDAO menuItemDao;
    private OrderDAO orderDao;

    @Override
    public void init() {
        userDao = new UserDAO();
        menuItemDao = new MenuItemDAO();
        orderDao = new OrderDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Load statistics
            request.setAttribute("userCount", userDao.getUserCount());
            request.setAttribute("vendorCount", userDao.getVendorCount());
            request.setAttribute("menuItemCount", menuItemDao.getMenuItemCount());
            request.setAttribute("orderCount", orderDao.getOrderCount());

            // Get role filter parameter
            String roleFilter = request.getParameter("role");

            // Load users based on filter
            List<UserModel> allUsers = userDao.getAllUsers();
            if (roleFilter != null && !roleFilter.equals("all")) {
                allUsers = allUsers.stream()
                        .filter(user -> user.getUserRole().equals(roleFilter))
                        .toList();
            }

            // Load users for management section
            request.setAttribute("users", allUsers);
            request.getRequestDispatcher("/WEB-INF/admin/dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("NOTIFICATION", "Error loading dashboard data: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/admin/dashboard.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = Integer.parseInt(request.getParameter("userId"));

        try {
            boolean success = false;
            String notification = switch (action) {
                case "promote" -> {
                    success = userDao.updateUserRole(userId, UserConstant.ROLE_VENDOR);
                    yield success ? "User promoted to vendor" : "Failed to promote user";
                }
                case "demote" -> {
                    success = userDao.updateUserRole(userId, UserConstant.ROLE_CUSTOMER);
                    yield success ? "User demoted to customer" : "Failed to demote user";
                }
                case "delete" -> {
                    success = userDao.deleteUser(userId);
                    yield success ? "User deleted" : "Failed to delete user";
                }
                default -> "";
            };

            request.setAttribute("NOTIFICATION", notification);
            doGet(request, response);

        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("NOTIFICATION", "Error: " + e.getMessage());
            doGet(request, response);
        }
    }
}