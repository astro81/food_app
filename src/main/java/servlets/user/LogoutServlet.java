package servlets.user;

import java.io.*;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.sql.SQLException;

@WebServlet(
        name = "LogoutServlet",
        value = "/user/logout",
        description = "Handles secure session termination and user logout"
)
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao;

    @Override
    public void init() {
        this.userDao = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String loginPage = request.getContextPath() + UserConstant.LOGIN_PATH;
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Clear remember me token from database
            clearRememberTokenFromDatabase(request);

            // Clear cookies
            clearRememberCookies(response, request.getContextPath());

            // Invalidate session
            session.invalidate();
        }

        response.sendRedirect(loginPage);
    }

    private void clearRememberTokenFromDatabase(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String rememberToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rememberToken".equals(cookie.getName())) {
                    rememberToken = cookie.getValue();
                    break;
                }
            }
        }

        if (rememberToken != null) {
            try {
                userDao.deleteRememberToken(rememberToken);
            } catch (SQLException e) {
                // Log error but continue with logout
                e.printStackTrace();
            }
        }
    }

    private void clearRememberCookies(HttpServletResponse response, String contextPath) {
        Cookie emailCookie = new Cookie("rememberedEmail", "");
        emailCookie.setMaxAge(0);
        emailCookie.setPath(contextPath);
        response.addCookie(emailCookie);

        Cookie tokenCookie = new Cookie("rememberToken", "");
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath(contextPath);
        response.addCookie(tokenCookie);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}