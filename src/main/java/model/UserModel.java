package model;

/**
 * Model class representing a user in the system.
 * Contains user attributes and provides getter/setter methods.
 */
public class UserModel {
    // User attributes
    private int userId;
    private String userName;     // User's full name
    private String userMail;     // User's email address (used as username)
    private String userPasswd;   // User's password (should be hashed)
    private String userPhone;    // User's phone number
    private String userAddress;  // User's physical address
    private String userRole;
    private byte[] profilePicture;

    /**
     * Constructor to create a new UserModel with all attributes.
     *
     * @param userName User's full name
     * @param userMail User's email address
     * @param userPasswd User's password
     * @param userPhone User's phone number
     * @param userAddress User's physical address
     * @param userRole User's role
     */
    public UserModel(String userName, String userMail, String userPasswd, String userPhone, String userAddress, String userRole) {
        this.userName = userName;
        this.userMail = userMail;
        this.userPasswd = userPasswd;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userRole = userRole;
    }

    public UserModel(int userId, String userName, String userMail, String userPasswd, String userPhone, String userAddress, String userRole, byte[] profilePicture) {
        this.userId = userId;
        this.userName = userName;
        this.userMail = userMail;
        this.userPasswd = userPasswd;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userRole = userRole;
        this.profilePicture = profilePicture;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** @return User's full name */
    public String getUserName() { return userName; }

    /** @param userName User's full name to set */
    public void setUserName(String userName) { this.userName = userName; }

    /** @return User's email address */
    public String getUserMail() { return userMail; }

    /** @param userMail User's email address to set */
    public void setUserMail(String userMail) { this.userMail = userMail; }

    /** @return User's password (hashed) */
    public String getUserPasswd() { return userPasswd; }

    /** @param userPasswd User's password to set (should be hashed first) */
    public void setUserPasswd(String userPasswd) { this.userPasswd = userPasswd; }

    /** @return User's phone number */
    public String getUserPhone() { return userPhone; }

    /** @param userPhone User's phone number to set */
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }

    /** @return User's physical address */
    public String getUserAddress() { return userAddress; }

    /** @param userAddress User's physical address to set */
    public void setUserAddress(String userAddress) { this.userAddress = userAddress; }

    /** @return User's role (admin, customer, vendor) */
    public String getUserRole() { return userRole; }

    /** @param userRole User's role to set */
    public void setUserRole(String userRole) { this.userRole = userRole; }

    /** @return User's profile picture */
    public byte[] getProfilePicture() { return profilePicture; }

    /** @param profilePicture User's profile picture to set */
    public void setProfilePicture(byte[] profilePicture) { this.profilePicture = profilePicture; }
}
