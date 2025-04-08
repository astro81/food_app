package servlets.user.profile;

import java.io.*;
import java.sql.SQLException;

import controller.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;

@WebServlet(name = "ProfileServlet", value = "/user/profile")
public class ProfileServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String PROFILE_PAGE = "/WEB-INF/user/welcome.jsp";

    private UserDAO userDao;

    public void init () {
        userDao = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        System.out.println(request.getContextPath());

        if (session == null || session.getAttribute("user") == null){
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        UserModel currentUser = (UserModel) session.getAttribute("user");

        try {

            if ("update".equals(action)) {
                handleUpdateProfile(request, response, currentUser, session);
            } else if ("delete".equals(action)) {
                handleDeleteProfile(request, response, currentUser, session);
            } else {
                request.setAttribute("NOTIFICATION", "Invalid action!");
                request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("NOTIFICATION", "Database error: " + e.getMessage());
            request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
        }

    }

    private void handleUpdateProfile(HttpServletRequest request, HttpServletResponse response,
                                     UserModel currentUser, HttpSession session)
            throws SQLException, ServletException, IOException {
        // Get updated user data from form
        String userName = request.getParameter("user_name");
        String userPhone = request.getParameter("user_phone");
        String userAddress = request.getParameter("user_address");
        String userPasswd = request.getParameter("user_passwd");

        // Create updated user model
        UserModel updatedUser = new UserModel(
                userName != null ? userName : currentUser.getUserName(),
                currentUser.getUserMail(), // Email shouldn't be changed
                userPasswd != null && !userPasswd.isEmpty() ? userPasswd : currentUser.getUserPasswd(),
                userPhone != null ? userPhone : currentUser.getUserPhone(),
                userAddress != null ? userAddress : currentUser.getUserAddress()
        );

        // Update in database
        if (userDao.updateUser(currentUser.getUserMail(), updatedUser)) {
            // Update session with new user data
            session.setAttribute("user", updatedUser);
            request.setAttribute("NOTIFICATION", "Profile updated successfully!");
        } else {
            request.setAttribute("NOTIFICATION", "Failed to update profile!");
        }

        request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
    }

    private void handleDeleteProfile(HttpServletRequest request, HttpServletResponse response,
                                     UserModel currentUser, HttpSession session)
            throws SQLException, IOException, ServletException {
        // Delete user from database
        if (userDao.deleteUser(currentUser.getUserMail())) {
            // Invalidate session and redirect to login
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/user/login?notification=Account+deleted+successfully");
        } else {
            request.setAttribute("NOTIFICATION", "Failed to delete account!");
            request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
        }
    }

    public void destroy() {
    }
}