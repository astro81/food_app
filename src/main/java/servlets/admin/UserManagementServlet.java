package servlets.admin;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlets.user.UserConstant;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/users")
public class UserManagementServlet extends HttpServlet {
    private UserDAO userDao;

    @Override
    public void init() {
        userDao = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("users", userDao.getAllUsers());
            request.getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("NOTIFICATION", "Error loading users: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/admin/dashboard.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = Integer.parseInt(request.getParameter("userId"));

        try {
            boolean success = false;
            String notification = "";

            switch (action) {
                case "promote":
                    success = userDao.updateUserRole(userId, UserConstant.ROLE_VENDOR);
                    notification = success ? "User promoted to vendor" : "Failed to promote user";
                    break;
                case "demote":
                    success = userDao.updateUserRole(userId, UserConstant.ROLE_CUSTOMER);
                    notification = success ? "User demoted to customer" : "Failed to demote user";
                    break;
                case "delete":
                    success = userDao.deleteUser(String.valueOf(userId));
                    notification = success ? "User deleted" : "Failed to delete user";
                    break;
            }

            request.setAttribute("NOTIFICATION", notification);
            doGet(request, response);

        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("NOTIFICATION", "Error: " + e.getMessage());
            doGet(request, response);
        }
    }
}