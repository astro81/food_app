// servlets/menu/item/util/MenuItemConstants.java
package servlets.menu.util;

import java.util.Arrays;
import java.util.List;

public class MenuItemConstants {
    // JSP view paths
    public static final String MENU_PAGE = "/WEB-INF/menu/index.jsp";
    public static final String EDIT_PAGE = "/WEB-INF/menu/editMenuItem.jsp";
    public static final String ADD_PAGE = "/WEB-INF/menu/addMenuItem.jsp";

    // Request parameter names
    public static final class Params {
        public static final String ACTION = "action";
        public static final String ID = "food_id";
        public static final String NAME = "food_name";
        public static final String DESCRIPTION = "food_description";
        public static final String PRICE = "food_price";
        public static final String CATEGORY = "food_category";
        public static final String AVAILABILITY = "food_availability";
    }

    // Action values
    public static final class Actions {
        public static final String ADD = "add";
        public static final String CREATE = "create";
        public static final String UPDATE = "update";
        public static final String DELETE = "delete";
        public static final String EDIT = "edit";
    }

    // Session attribute names
    public static final class Attrs {
        public static final String SUCCESS = "successMessage";
        public static final String ERROR = "errorMessage";
        public static final String MENU_ITEMS = "menuItems";
        public static final String MENU_ITEM = "menuItem";
    }

    // Valid values
    public static final class ValidValues {
        public static final List<String> CATEGORIES = Arrays.asList("meals", "snacks", "sweets", "drinks");
        public static final List<String> AVAILABILITY = Arrays.asList("available", "out_of_order");
    }
}