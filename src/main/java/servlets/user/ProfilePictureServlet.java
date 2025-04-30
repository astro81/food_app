package servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.UserModel;
import dao.UserDAO;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/user/profile-picture")
public class ProfilePictureServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(UserConstant.ATTR_USER) == null) {
            // Serve default profile picture
            try (InputStream is = getServletContext().getResourceAsStream("/images/default-profile.png")) {
                if (is != null) {
                    response.setContentType("image/png");
                    is.transferTo(response.getOutputStream());
                }
            }
            return;
        }

        UserModel user = (UserModel) session.getAttribute(UserConstant.ATTR_USER);
        byte[] profilePicture = user.getProfilePicture();

        if (profilePicture == null || profilePicture.length == 0) {
            // Serve default profile picture
            try (InputStream is = getServletContext().getResourceAsStream("/images/default-profile.png")) {
                if (is != null) {
                    response.setContentType("image/png");
                    is.transferTo(response.getOutputStream());
                }
            }
        } else {
            // Serve user's profile picture
            response.setContentType("image/jpeg"); // or whatever format you're using
            response.getOutputStream().write(profilePicture);
        }
    }
}