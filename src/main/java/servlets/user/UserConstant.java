package servlets.user;

public class UserConstant {
    // View configuration
    static final String REGISTRATION_PAGE = "/WEB-INF/user/register.jsp";
    static final String LOGIN_PAGE = "/WEB-INF/user/login.jsp";
    public static final String PROFILE_PAGE = "/WEB-INF/user/profile.jsp";

    // Servlet path
    public static final String LOGIN_PATH = "/user/login";

    // Request parameter names
    public static final String PARAM_NAME = "user_name";
    static final String PARAM_EMAIL = "user_mail";
    public static final String PARAM_PASSWORD = "user_passwd";
    public static final String PARAM_PHONE = "user_phone";
    public static final String PARAM_ADDRESS = "user_address";

    static final String PARAM_ACTION = "action";
    static final String ACTION_UPDATE = "update";
    static final String ACTION_DELETE = "delete";

    // Session attribute names
    public static final String ATTR_USER = "user";

    // Notification attributes
    public static final String MSG_NOTIFICATION = "NOTIFICATION";
    static final String MSG_SUCCESS = "User Registered Successfully!";
    public static final String MSG_DELETE_SUCCESS = "Account deleted successfully";
    public static final String MSG_DELETE_FAILED = "Failed to delete account!";
    public static final String MSG_UPDATE_SUCCESS = "Profile updated successfully!";
    public static final String MSG_UPDATE_FAILED = "Failed to update profile!";

    static final String MSG_FAILURE = "Registration Failed!";
    static final String MSG_ERROR = "Error: ";
    static final String MSG_AUTH_FAILED = "Invalid email or password!";
    static final String MSG_DB_ERROR = "Database Error: ";

    // Notification messages
    static final String MSG_SESSION_EXPIRED = "Session expired. Please login again.";
    static final String MSG_INVALID_ACTION = "Invalid action requested!";

}
