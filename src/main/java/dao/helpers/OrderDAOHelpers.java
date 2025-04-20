package dao.helpers;

import model.MenuItemModel;
import model.OrderItemModel;
import model.OrderModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helper class for OrderDAO database operations.
 * Provides utility methods for working with order-related database operations.
 *
 * This class contains methods that:
 * - Set parameters for prepared statements
 * - Map database result sets to model objects
 * - Handle common database operation patterns
 *
 * By centralizing these operations, we ensure consistent behavior
 * across all order-related database interactions.
 */
public class OrderDAOHelpers {

    /**
     * Sets parameters for order INSERT operations.
     *
     * This method populates a PreparedStatement with data from an OrderModel
     * for inserting a new order into the database. The expected SQL pattern is:
     * "INSERT INTO orders (user_id, order_date, status) VALUES (?, CURRENT_TIMESTAMP, ?)"
     *
     * Note that order_date uses CURRENT_TIMESTAMP in the SQL itself.
     *
     * @param pst   The prepared statement to populate with parameters
     * @param order The order containing the data to set
     * @throws SQLException If setting a parameter fails
     */
    public static void setOrderParameters(PreparedStatement pst, OrderModel order) throws SQLException {
        pst.setInt(1, order.getUserId());     // Set user ID as first parameter
        pst.setString(2, order.getStatus());  // Set status as second parameter
    }

    /**
     * Sets parameters for order item INSERT/UPDATE operations.
     *
     * This method handles both update and insert statements based on parameter count:
     * - For UPDATE: The pattern is "UPDATE order_items SET quantity = ? WHERE order_id = ? AND food_id = ?"
     * - For INSERT: The pattern is "INSERT INTO order_items (order_id, food_id, quantity) VALUES (?, ?, ?)"
     *
     * The method detects which statement type is being used by checking the parameter count
     * and arranges parameters in the correct order.
     *
     * @param pst      The prepared statement to populate with parameters
     * @param orderId  The ID of the order
     * @param item     The menu item being added/updated
     * @param quantity The quantity of the item
     * @throws SQLException If setting parameters fails
     */
    public static void setOrderItemParameters(PreparedStatement pst, int orderId,
                                              MenuItemModel item, int quantity) throws SQLException {
        // Check parameter count to determine statement type
        int paramCount = pst.getParameterMetaData().getParameterCount();

        // For UPDATE statements: quantity, orderId, foodId
        if (paramCount == 3) {
            pst.setInt(1, quantity);           // New quantity value
            pst.setInt(2, orderId);            // For WHERE clause
            pst.setInt(3, item.getFoodId());   // For WHERE clause
        }
        // For INSERT statements: orderId, foodId, quantity
        else {
            pst.setInt(1, orderId);            // Order ID as first value
            pst.setInt(2, item.getFoodId());   // Food ID as second value
            pst.setInt(3, quantity);           // Quantity as third value
        }
    }

    /**
     * Maps a ResultSet row to an OrderModel object.
     *
     * This method extracts order data from a database result set and creates
     * a populated OrderModel instance. Expected columns in the result set are:
     * - order_id: The unique identifier for the order
     * - user_id: The ID of the user who placed the order
     * - order_date: The timestamp when the order was created
     * - status: The current status of the order (pending, confirmed, etc.)
     *
     * @param rs The result set positioned at the row to map
     * @return A fully populated OrderModel instance
     * @throws SQLException If reading from the result set fails
     */
    public static OrderModel mapResultSetToOrder(ResultSet rs) throws SQLException {
        // Create order with the user ID
        OrderModel order = new OrderModel(rs.getInt("user_id"));

        // Set remaining properties from result set
        order.setOrderId(rs.getInt("order_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setStatus(rs.getString("status"));

        return order;
    }

    /**
     * Maps a ResultSet row to an OrderItemModel object.
     *
     * This method creates an OrderItemModel that combines a menu item with its quantity.
     * It relies on MenuDAOHelpers to extract the menu item details, and then reads
     * the quantity from the result set. Expected columns include all menu item columns
     * plus:
     * - quantity: The number of this item in the order
     *
     * @param rs The result set positioned at the row to map
     * @return A populated OrderItemModel containing the menu item and its quantity
     * @throws SQLException If reading from the result set fails
     */
    public static OrderItemModel mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        // First extract the menu item using the helper from MenuDAOHelpers
        MenuItemModel item = MenuDAOHelpers.mapResultSetToMenuItem(rs);

        // Get the quantity for this item
        int quantity = rs.getInt("quantity");

        // Create and return the combined order item model
        return new OrderItemModel(item, quantity);
    }
}