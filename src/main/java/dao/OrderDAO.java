package dao;

import config.DatabaseConnection;
import dao.helpers.MenuDAOHelpers;
import dao.helpers.OrderDAOHelpers;
import model.MenuItemModel;
import model.OrderModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.helpers.ConnectionHelper.prepareStatement;

/**
 * Data Access Object for order-related database operations.
 * Handles CRUD operations for orders and order items in the database.
 *
 * This class provides methods for:
 * - Creating new orders for users
 * - Adding menu items to orders
 * - Retrieving pending orders with their items
 * - Confirming orders by changing their status
 *
 * The class uses prepared statements for all database operations to prevent SQL injection
 * and follows a consistent pattern for exception handling.
 */
public class OrderDAO {
    // SQL Query constants for database operations
    /**
     * Query to insert a new order with pending status and current timestamp
     */
    private static final String INSERT_ORDER_QUERY =
            "INSERT INTO orders (user_id, order_date, status) VALUES (?, CURRENT_TIMESTAMP, ?)";

    /**
     * Query to insert an item into an existing order with a specified quantity
     */
    private static final String INSERT_ORDER_ITEM_QUERY =
            "INSERT INTO order_items (order_id, food_id, quantity) VALUES (?, ?, ?)";

    /**
     * Query to update the quantity of an item already in an order
     */
    private static final String UPDATE_ORDER_ITEM_QUERY =
            "UPDATE order_items SET quantity = ? WHERE order_id = ? AND food_id = ?";

    /**
     * Query to find a user's pending order (if any)
     */
    private static final String SELECT_PENDING_ORDER_QUERY =
            "SELECT o.* FROM orders o WHERE o.user_id = ? AND o.status = 'pending'";

    /**
     * Query to retrieve all items in an order with their details and quantities
     */
    private static final String SELECT_ORDER_ITEMS_QUERY =
            "SELECT mi.*, oi.quantity FROM order_items oi " +
                    "JOIN MenuItem mi ON oi.food_id = mi.food_id " +
                    "WHERE oi.order_id = ?";

    /**
     * Query to check the current quantity of a specific item in an order
     */
    private static final String SELECT_ITEM_QUANTITY_QUERY =
            "SELECT quantity FROM order_items WHERE order_id = ? AND food_id = ?";

    /**
     * Query to update an order's status from pending to confirmed
     */
    private static final String UPDATE_ORDER_STATUS_QUERY =
            "UPDATE orders SET status = 'confirmed' WHERE order_id = ?";

    /**
     * Adds a menu item to a user's order. Creates a new order if no pending order exists.
     *
     * This method performs the following steps:
     * 1. Check if the user has a pending order
     * 2. If not, create a new pending order
     * 3. Add the item to the order model
     * 4. Save the item to the database
     *
     * @param userId The ID of the user placing the order
     * @param item   The menu item to add to the order
     * @throws SQLException If any database operation fails
     */
    public static void createOrder(int userId, MenuItemModel item) throws SQLException {
        // Get or create a pending order for this user
        OrderModel pendingOrder = getPendingOrder(userId);
        if (pendingOrder == null) {
            // No pending order exists, create a new one
            pendingOrder = createNewOrder(userId);
        }

        // Add the item to the model and save to database
        pendingOrder.addItem(item);
        saveOrderItem(pendingOrder.getOrderId(), item);
    }

    /**
     * Retrieves a user's pending order with all items and quantities loaded.
     *
     * This method fetches the basic order details and then loads all associated
     * menu items with their quantities from the database.
     *
     * @param userId The ID of the user whose order to retrieve
     * @return The pending order with all items loaded, or null if no pending order exists
     * @throws SQLException If any database operation fails
     */
    public static OrderModel getPendingOrder(int userId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(SELECT_PENDING_ORDER_QUERY)) {
            // Set user ID parameter
            pst.setInt(1, userId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // Found a pending order - map it from the result set
                    OrderModel order = OrderDAOHelpers.mapResultSetToOrder(rs);

                    // Load all the order items and quantities
                    loadOrderItems(order);
                    return order;
                }
            }
        }
        // No pending order found for this user
        return null;
    }

    /**
     * Confirms a pending order by updating its status from 'pending' to 'confirmed'.
     *
     * This method only confirms orders that:
     * 1. Belong to the specified user
     * 2. Have status 'pending'
     * 3. Contain at least one item
     *
     * @param userId The ID of the user whose order to confirm
     * @return true if the order was successfully confirmed, false if no valid order found
     * @throws SQLException If any database operation fails
     */
    public static boolean confirmOrder(int userId) throws SQLException {
        // Get the user's pending order with items
        OrderModel order = getPendingOrder(userId);

        // Check if order exists and has items
        if (order == null || order.isEmpty()) {
            return false; // No valid order to confirm
        }

        // Update the order status to 'confirmed'
        try (PreparedStatement pst = prepareStatement(UPDATE_ORDER_STATUS_QUERY)) {
            pst.setInt(1, order.getOrderId());
            return pst.executeUpdate() > 0; // Returns true if update was successful
        }
    }

    /**
     * Creates a new pending order for a user and stores it in the database.
     *
     * This method:
     * 1. Creates a new OrderModel with 'pending' status
     * 2. Inserts it into the database
     * 3. Retrieves and sets the generated order ID
     *
     * @param userId The ID of the user for whom to create an order
     * @return The newly created order with its database-generated ID
     * @throws SQLException If any database operation fails
     */
    private static OrderModel createNewOrder(int userId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(INSERT_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            // Create a new order model
            OrderModel newOrder = new OrderModel(userId);

            // Set parameters and execute the insert
            OrderDAOHelpers.setOrderParameters(pst, newOrder);
            pst.executeUpdate();

            // Retrieve the database-generated order ID
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    newOrder.setOrderId(rs.getInt(1));
                }
            }
            return newOrder;
        }
    }

    /**
     * Saves an item to an order, either by inserting a new record or updating an existing one.
     *
     * This method:
     * 1. Checks if the item already exists in the order
     * 2. If it exists, increments the quantity
     * 3. If not, adds it with quantity 1
     *
     * @param orderId The ID of the order to modify
     * @param item    The menu item to save to the order
     * @throws SQLException If any database operation fails
     */
    private static void saveOrderItem(int orderId, MenuItemModel item) throws SQLException {
        // Check if item already exists in order
        Integer currentQuantity = getItemQuantity(orderId, item.getFoodId());
        int newQuantity = (currentQuantity != null) ? currentQuantity + 1 : 1;

        if (currentQuantity != null) {
            // Item exists - update its quantity
            try (PreparedStatement pst = prepareStatement(UPDATE_ORDER_ITEM_QUERY)) {
                pst.setInt(1, newQuantity);        // New quantity
                pst.setInt(2, orderId);            // Order ID
                pst.setInt(3, item.getFoodId());   // Food ID
                pst.executeUpdate();
            }
        } else {
            // Item doesn't exist - insert it with quantity 1
            try (PreparedStatement pst = prepareStatement(INSERT_ORDER_ITEM_QUERY)) {
                pst.setInt(1, orderId);            // Order ID
                pst.setInt(2, item.getFoodId());   // Food ID
                pst.setInt(3, 1);                  // Initial quantity
                pst.executeUpdate();
            }
        }
    }

    /**
     * Gets the current quantity of a specific menu item in an order.
     *
     * This is used to determine whether to update an existing order item
     * or insert a new one when adding items to an order.
     *
     * @param orderId The ID of the order to check
     * @param foodId  The ID of the food item to look for
     * @return The current quantity if item exists in the order, null otherwise
     * @throws SQLException If any database operation fails
     */
    private static Integer getItemQuantity(int orderId, int foodId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(SELECT_ITEM_QUANTITY_QUERY)) {
            // Set parameters for the query
            pst.setInt(1, orderId);
            pst.setInt(2, foodId);

            // Execute query and check if item exists
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? rs.getInt("quantity") : null;
            }
        }
    }

    /**
     * Loads all items for an order along with their quantities.
     *
     * This populates the order's itemsWithQuantities map with menu items
     * and their corresponding quantities from the database.
     *
     * @param order The order to load items for
     * @throws SQLException If any database operation fails
     */
    private static void loadOrderItems(OrderModel order) throws SQLException {
        try (PreparedStatement pst = prepareStatement(SELECT_ORDER_ITEMS_QUERY)) {
            // Set the order ID parameter
            pst.setInt(1, order.getOrderId());

            // Execute query and process results
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // For each row, create a menu item from the result set
                    MenuItemModel item = MenuDAOHelpers.mapResultSetToMenuItem(rs);

                    // Get the quantity and add to the order's map
                    int quantity = rs.getInt("quantity");
                    order.getItemsWithQuantities().put(item, quantity);
                }
            }
        }
    }
}