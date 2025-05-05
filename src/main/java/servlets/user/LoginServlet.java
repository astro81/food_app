package servlets.user;

import java.io.*;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

@WebServlet(
        name = "LoginServlet",
        value = "/user/login",
        description = "Handles user authentication and session creation"
)
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao;

    @Override
    public void init() {
        this.userDao = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(UserConstant.LOGIN_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userMail = request.getParameter(UserConstant.PARAM_EMAIL);
        String userPasswd = request.getParameter(UserConstant.PARAM_PASSWORD);

        try {
            UserModel user = userDao.loginUser(userMail);

            if (user != null && BCrypt.checkpw(userPasswd, user.getUserPasswd())) {
                // Authentication success
                HttpSession session = request.getSession();
                session.setAttribute(UserConstant.ATTR_USER, user);

                handleRememberMe(request, response, user, userMail);
                redirectBasedOnRole(response, request.getContextPath(), user);

            } else {
                handleFailedLogin(request, response);
            }
        } catch (SQLException e) {
            handleDatabaseError(request, response, e);
        } catch (Exception e) {
            handleGenericError(request, response, e);
        }
    }

    private void handleRememberMe(HttpServletRequest request, HttpServletResponse response,
                                  UserModel user, String userMail) throws SQLException {
        String rememberMe = request.getParameter("remember");

        if ("true".equals(rememberMe)) {
            // Create cookies
            Cookie emailCookie = new Cookie("rememberedEmail", userMail);
            emailCookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
            emailCookie.setPath(request.getContextPath());
            emailCookie.setHttpOnly(true);
            response.addCookie(emailCookie);

            String rememberToken = UUID.randomUUID().toString();
            Cookie tokenCookie = new Cookie("rememberToken", rememberToken);
            tokenCookie.setMaxAge(7 * 24 * 60 * 60); // 30 days
            tokenCookie.setPath(request.getContextPath());
            tokenCookie.setHttpOnly(true);
            response.addCookie(tokenCookie);

            // Store token in database
            Timestamp expiryDate = new Timestamp(
                    System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000));
            userDao.storeRememberToken(user.getUserId(), rememberToken, expiryDate);

        } else {
            // Clear any existing remember me cookies
            clearRememberCookies(request, response);
        }
    }

    private void clearRememberCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rememberedEmail".equals(cookie.getName()) ||
                        "rememberToken".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                }
            }
        }
    }

    private void redirectBasedOnRole(HttpServletResponse response, String contextPath, UserModel user)
            throws IOException {
        if (user.getUserRole().equals("admin")) {
            response.sendRedirect(contextPath + "/admin/dashboard");
        } else {
            response.sendRedirect(contextPath + "/menu");
        }
    }

    private void handleFailedLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(UserConstant.MSG_NOTIFICATION, UserConstant.MSG_LOGIN_FAILED);
        request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_ERROR);
        request.getRequestDispatcher(UserConstant.LOGIN_PAGE).forward(request, response);
    }

    private void handleDatabaseError(HttpServletRequest request, HttpServletResponse response,
                                     SQLException e) throws ServletException, IOException {
        request.setAttribute(UserConstant.MSG_NOTIFICATION, UserConstant.MSG_DB_ERROR + e.getMessage());
        request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_ERROR);
        request.getRequestDispatcher(UserConstant.LOGIN_PAGE).forward(request, response);
    }

    private void handleGenericError(HttpServletRequest request, HttpServletResponse response,
                                    Exception e) throws ServletException, IOException {
        request.setAttribute(UserConstant.MSG_NOTIFICATION,
                UserConstant.NOTIFICATION_ERROR + " " + e.getMessage());
        request.setAttribute(UserConstant.NOTIFICATION_TYPE, UserConstant.NOTIFICATION_ERROR);
        request.getRequestDispatcher(UserConstant.LOGIN_PAGE).forward(request, response);
    }
}