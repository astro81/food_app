package servlets.menu;

public class MenuConstant {
    // View configuration
    public static final String MENU_LIST_PAGE = "/WEB-INF/menu/menu-list.jsp";
    public static final String MENU_EDIT_PAGE = "/WEB-INF/menu/menu-edit.jsp";
    public static final String MENU_DETAIL_PAGE = "/WEB-INF/menu/menu-detail.jsp";

    // Servlet paths
    public static final String MENU_PATH = "/menu";

    // Request parameter names
    public static final String PARAM_FOOD_ID = "food_id";
    public static final String PARAM_FOOD_NAME = "food_name";
    public static final String PARAM_FOOD_DESC = "food_description";
    public static final String PARAM_FOOD_PRICE = "food_price";
    public static final String PARAM_FOOD_CATEGORY = "food_category";
    public static final String PARAM_FOOD_AVAILABILITY = "food_availability";
    public static final String PARAM_ACTION = "action";

    // Action values
    public static final String ACTION_VIEW = "view";
    public static final String ACTION_ADD = "add";
    public static final String ACTION_EDIT = "edit";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_FILTER = "filter";

    // Request attributes
    public static final String ATTR_MENU_ITEM = "menuItem";
    public static final String ATTR_MENU_ITEMS = "menuItems";
    public static final String ATTR_FILTER_CATEGORY = "filterCategory";
    public static final String ATTR_FILTER_AVAILABILITY = "filterAvailability";

    // Notification messages
    public static final String MSG_NOTIFICATION = "NOTIFICATION";
    public static final String MSG_ADD_SUCCESS = "Menu item added successfully!";
    public static final String MSG_ADD_FAILURE = "Failed to add menu item!";
    public static final String MSG_UPDATE_SUCCESS = "Menu item updated successfully!";
    public static final String MSG_UPDATE_FAILURE = "Failed to update menu item!";
    public static final String MSG_DELETE_SUCCESS = "Menu item deleted successfully!";
    public static final String MSG_DELETE_FAILURE = "Failed to delete menu item!";
    public static final String MSG_UNAUTHORIZED = "You are not authorized to perform this action!";
    public static final String MSG_ERROR = "Error: ";
    public static final String MSG_DB_ERROR = "Database Error: ";
}