package servlets.user;

public class UserConstant {
    // View configuration
    static final String REGISTRATION_PAGE = "/WEB-INF/user/register.jsp";
    static final String LOGIN_PAGE = "/WEB-INF/user/login.jsp";
    public static final String PROFILE_PAGE = "/WEB-INF/user/profile.jsp";

    // Servlet path
    public static final String LOGIN_PATH = "/user/login";

    public static final String VENDOR_DASHBOARD_PATH = "/vendor/dashboard";
    public static final String ADMIN_DASHBOARD_PATH = "/admin/dashboard";
    public static final String ADMIN_MANAGE_USERS_PATH = "/admin/users";

    // Request parameter names
    public static final String PARAM_NAME = "user_name";
    static final String PARAM_EMAIL = "user_mail";
    public static final String PARAM_PASSWORD = "user_passwd";
    public static final String PARAM_PHONE = "user_phone";
    public static final String PARAM_ADDRESS = "user_address";
    public static final String PARAM_PROFILE_PICTURE = "profilePicture";

    static final String PARAM_ACTION = "action";
    static final String ACTION_UPDATE = "update";
    static final String ACTION_DELETE = "delete";
    public static final String ACTION_DELETE_ORDER = "deleteOrder";

    // Session attribute names
    public static final String ATTR_USER = "user";
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_VENDOR = "vendor";
    public static final String ROLE_CUSTOMER = "customer";

    // Notification attributes
    public static final String MSG_NOTIFICATION = "NOTIFICATION";
    public static final String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";
    public static final String NOTIFICATION_SUCCESS = "SUCCESS";
    public static final String NOTIFICATION_ERROR = "ERROR";

    public static final String MSG_REGISTER_SUCCESS = "User Registered Successfully!";
    public static final String MSG_REGISTER_FAILED = "Registration Failed!";
    public static final String MSG_LOGIN_FAILED = "Invalid email or password!";

    public static final String MSG_DELETE_SUCCESS = "Account deleted successfully";
    public static final String MSG_DELETE_FAILED = "Failed to delete account!";
    public static final String MSG_UPDATE_SUCCESS = "Profile updated successfully!";
    public static final String MSG_UPDATE_FAILED = "Failed to update profile!";

    static final String MSG_DB_ERROR = "Database Error: ";

    // Notification messages
    static final String MSG_SESSION_EXPIRED = "Session expired. Please login again.";
    static final String MSG_INVALID_ACTION = "Invalid action requested!";

    public static final String MSG_PROMOTE_SUCCESS = "User promoted to vendor successfully";
    public static final String MSG_DEMOTE_SUCCESS = "User demoted to customer successfully";

}
