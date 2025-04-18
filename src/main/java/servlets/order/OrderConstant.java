package servlets.order;

import servlets.menu.MenuConstant;

public class OrderConstant {
    // View configuration
    public static final String ORDER_PREVIEW_PAGE = "/WEB-INF/order/order-preview.jsp";

    // Request parameters (reusing menu constants where applicable)
    public static final String PARAM_FOOD_ID = MenuConstant.PARAM_FOOD_ID;

    // Notification messages
    public static final String MSG_NOTIFICATION = "NOTIFICATION";
    public static final String MSG_CONFIRM_SUCCESS = "Order confirmed successfully!";
    public static final String MSG_NO_ITEMS = "No items in your order!";
    public static final String MSG_DB_ERROR = "Database Error: ";
    public static final String MSG_ORDER_CREATED = "Item added to order successfully!";
    public static final String MSG_ITEM_NOT_FOUND = "Menu item not found!";
}