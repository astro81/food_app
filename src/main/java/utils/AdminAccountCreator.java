package utils;

import dao.UserDAO;
import model.UserModel;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for creating admin accounts directly in the database.
 * This should be run separately from the main application for initial setup.
 */
public class AdminAccountCreator {

    public static void main(String[] args) {
        // Admin account details
        String name = "System Admin";
        String email = "admin@sys.com";
        String password = "root";
        String phone = "1234567890";
        String address = "System Headquarters";

        createAdminAccount(name, email, password, phone, address);
    }

    /**
     * Creates an admin account in the database
     *
     * @param name Full name of the admin
     * @param email Email address (will be used for login)
     * @param password Plain text password (will be hashed)
     * @param phone Phone number
     * @param address Physical address
     */
    public static void createAdminAccount(String name, String email, String password, String phone, String address) {
        try {
            // Hash the password
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Create user model
            UserModel adminUser = new UserModel(
                    name,
                    email,
                    hashedPassword,
                    phone,
                    address,
                    "admin"  // Role set to admin
            );

            // Insert into database
            UserDAO userDao = new UserDAO();
            boolean success = userDao.registerUser(adminUser);

            if (success) {
                System.out.println("Admin account created successfully!");
                System.out.println("Email: " + email);
                System.out.println("Password: " + password); // Only show for initial setup!
            } else {
                System.out.println("Failed to create admin account. It may already exist.");
            }
        } catch (Exception e) {
            System.err.println("Error creating admin account:");
            e.printStackTrace();
        }
    }
}