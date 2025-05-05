package filters;

import dao.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;
import servlets.user.UserConstant;

import java.io.IOException;

@WebFilter("/*")
public class AutoLoginFilter implements Filter {

    private UserDAO userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.userDao = new UserDAO();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Skip if user is already logged in or this is a login/logout request
        if (session != null && session.getAttribute(UserConstant.ATTR_USER) != null ||
                httpRequest.getRequestURI().contains("/user/login") ||
                httpRequest.getRequestURI().contains("/user/logout")) {
            chain.doFilter(request, response);
            return;
        }

        // Check for remember me cookies
        Cookie[] cookies = httpRequest.getCookies();
        String rememberedEmail = null;
        String rememberToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("rememberedEmail".equals(cookie.getName())) {
                    rememberedEmail = cookie.getValue();
                } else if ("rememberToken".equals(cookie.getName())) {
                    rememberToken = cookie.getValue();
                }
            }
        }

        // If both cookies exist, try auto-login
        if (rememberedEmail != null && rememberToken != null) {
            try {
                UserModel user = userDao.getUserByRememberToken(rememberToken);
                if (user != null && user.getUserMail().equals(rememberedEmail)) {
                    // Valid token - create session
                    HttpSession newSession = httpRequest.getSession();
                    newSession.setAttribute(UserConstant.ATTR_USER, user);

                    // Optionally refresh the token
                    refreshRememberToken(user.getUserId(), httpResponse);
                } else {
                    // Invalid token - clear cookies
                    clearRememberCookies(httpResponse);
                }
            } catch (Exception e) {
                // Log error and continue
                e.printStackTrace();
                clearRememberCookies(httpResponse);
            }
        }

        chain.doFilter(request, response);
    }

    private void refreshRememberToken(int userId, HttpServletResponse response) {
        // Implement if you want to refresh the token on each auto-login
    }

    private void clearRememberCookies(HttpServletResponse response) {
        Cookie emailCookie = new Cookie("rememberedEmail", "");
        emailCookie.setMaxAge(0);
        emailCookie.setPath("/");
        response.addCookie(emailCookie);

        Cookie tokenCookie = new Cookie("rememberToken", "");
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}