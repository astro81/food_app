package servlets.user;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.UserModel;
import servlets.user.handlers.ProfilePictureHandler;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(
        name = "ProfilePictureUpdateServlet",
        value = "/user/update-profile-picture",
        description = "Controller for user profile picture updates"
)
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 20,  // 20 MB
        maxRequestSize = 1024 * 1024 * 30 // 30 MB
)
public class ProfilePictureUpdateServlet extends HttpServlet {
    private ProfilePictureHandler profilePictureHandler;

    @Override
    public void init() {
        UserDAO userDao = new UserDAO();
        this.profilePictureHandler = new ProfilePictureHandler(userDao);
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

        try {
            profilePictureHandler.handle(request, response, currentUser, session);
        } catch (SQLException e) {
            request.setAttribute("NOTIFICATION", UserConstant.MSG_DB_ERROR + ": " + e.getMessage());
            request.getRequestDispatcher(UserConstant.PROFILE_PAGE).forward(request, response);
        }
    }

    private boolean isUserAuthenticated(HttpSession session) {
        return session != null && session.getAttribute(UserConstant.ATTR_USER) != null;
    }
}