package servlets.user;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.UserModel;
import model.OrderModel;
import servlets.user.handlers.DeleteOrderHandler;
import servlets.user.handlers.DeleteProfileHandler;
import servlets.user.handlers.ProfileHandler;
import servlets.user.handlers.UpdateProfileHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@WebServlet(
        name = "ProfileServlet",
        value = "/user/profile",
        description = "Controller for user profile operations"
)
public class ProfileServlet extends HttpServlet {
    private Map<String, ProfileHandler> handlers;

    @Override
    public void init() {
        UserDAO userDao = new UserDAO();
        this.handlers = new HashMap<>();
        this.handlers.put(UserConstant.ACTION_UPDATE, new UpdateProfileHandler(userDao));
        this.handlers.put(UserConstant.ACTION_DELETE, new DeleteProfileHandler(userDao));
        this.handlers.put(UserConstant.ACTION_DELETE_ORDER, new DeleteOrderHandler());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        request.setAttribute("orderHistory", new ArrayList<OrderModel>());

        if (isUserAuthenticated(session)) {
            UserModel currentUser = (UserModel) session.getAttribute(UserConstant.ATTR_USER);
            try {
                // Load order history for the user
                List<OrderModel> orderHistory = UserDAO.getOrderHistory(currentUser.getUserId());
                request.setAttribute("orderHistory", orderHistory);
            } catch (SQLException e) {
                request.setAttribute("NOTIFICATION", "Error loading order history: " + e.getMessage());
            }
        }
        request.getRequestDispatcher(UserConstant.PROFILE_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (!isUserAuthenticated(session)) {
            response.sendRedirect(request.getContextPath() + UserConstant.LOGIN_PATH);
            return;
        }

        UserModel currentUser = (UserModel) session.getAttribute(UserConstant.ATTR_USER);
        String action = request.getParameter(UserConstant.PARAM_ACTION);

        try {
            ProfileHandler handler = handlers.get(action);
            if (handler != null) {
                handler.handle(request, response, currentUser, session);
            } else {
                request.setAttribute("NOTIFICATION", UserConstant.MSG_INVALID_ACTION);
                doGet(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("NOTIFICATION", UserConstant.MSG_DB_ERROR + ": " + e.getMessage());
            doGet(request, response);
        }
    }

    private boolean isUserAuthenticated(HttpSession session) {
        return session != null && session.getAttribute(UserConstant.ATTR_USER) != null;
    }
}