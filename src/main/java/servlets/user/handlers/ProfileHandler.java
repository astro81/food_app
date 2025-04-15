package servlets.user.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Interface for profile operation handlers.
 * <p>
 * Defines the contract for all profile operation handlers,
 * ensuring consistent handling of requests across different operations.
 */
public interface ProfileHandler {
    /**
     * Handles a specific profile operation.
     *
     * @param request     the HttpServletRequest object
     * @param response    the HttpServletResponse object
     * @param currentUser the currently authenticated user
     * @param session     the current HttpSession
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     * @throws SQLException     if a database access error occurs
     */
    void handle(HttpServletRequest request, HttpServletResponse response,
                UserModel currentUser, HttpSession session)
            throws ServletException, IOException, SQLException;
}