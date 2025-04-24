package dao;

import config.DatabaseConnection;
import dao.helpers.MenuDAOHelpers;
import dao.helpers.OrderDAOHelpers;
import model.MenuItemModel;
import model.OrderModel;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Map;

import static dao.helpers.ConnectionHelper.prepareStatement;

public class OrderDAO {
    private static final String INSERT_ORDER_QUERY =
            "INSERT INTO orders (user_id, total, status) VALUES (?, ?, ?)";

    private static final String INSERT_ORDER_ITEM_QUERY =
            "INSERT INTO order_items (order_id, food_id, quantity, subtotal) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_ORDER_ITEM_QUERY =
            "UPDATE order_items SET quantity = ?, subtotal = ? WHERE order_id = ? AND food_id = ?";

    private static final String SELECT_PENDING_ORDER_QUERY =
            "SELECT * FROM orders WHERE user_id = ? AND status = 'pending'";

    private static final String SELECT_ORDER_ITEMS_QUERY =
            "SELECT mi.*, oi.quantity, oi.subtotal FROM order_items oi " +
                    "JOIN MenuItem mi ON oi.food_id = mi.food_id " +
                    "WHERE oi.order_id = ?";

    private static final String SELECT_ITEM_QUANTITY_QUERY =
            "SELECT quantity, subtotal FROM order_items WHERE order_id = ? AND food_id = ?";

    private static final String UPDATE_ORDER_STATUS_QUERY =
            "UPDATE orders SET status = 'confirmed' WHERE order_id = ?";

    // New query for getting a specific order by ID
    private static final String SELECT_ORDER_BY_ID_QUERY =
            "SELECT * FROM orders WHERE order_id = ?";


    public static void createOrder(int userId, MenuItemModel item) throws SQLException {
        OrderModel pendingOrder = getPendingOrder(userId);
        if (pendingOrder == null) {
            pendingOrder = createNewOrder(userId);
        }

        BigDecimal price = item.getFoodPrice();
        Integer currentQuantity = getItemQuantity(pendingOrder.getOrderId(), item.getFoodId());
        int newQuantity = (currentQuantity != null) ? currentQuantity + 1 : 1;
        BigDecimal subtotal = price.multiply(BigDecimal.valueOf(newQuantity));

        if (currentQuantity != null) {
            updateOrderItem(pendingOrder.getOrderId(), item.getFoodId(), newQuantity, subtotal);
        } else {
            insertOrderItem(pendingOrder.getOrderId(), item.getFoodId(), 1, price);
        }

        updateOrderTotal(pendingOrder.getOrderId());
    }

    public static OrderModel getPendingOrder(int userId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(SELECT_PENDING_ORDER_QUERY)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    OrderModel order = OrderDAOHelpers.mapResultSetToOrder(rs);
                    loadOrderItems(order);
                    return order;
                }
            }
        }
        return null;
    }

    public static boolean confirmOrder(int userId) throws SQLException {
        OrderModel order = getPendingOrder(userId);
        if (order == null || order.getItemsWithQuantities().isEmpty()) {
            return false;
        }

        try (PreparedStatement pst = prepareStatement(UPDATE_ORDER_STATUS_QUERY)) {
            pst.setInt(1, order.getOrderId());
            return pst.executeUpdate() > 0;
        }
    }

    private static OrderModel createNewOrder(int userId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(INSERT_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, userId);
            pst.setBigDecimal(2, BigDecimal.ZERO);
            pst.setString(3, "pending");
            pst.executeUpdate();

            OrderModel newOrder = new OrderModel(userId);
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    newOrder.setOrderId(rs.getInt(1));
                }
            }
            return newOrder;
        }
    }

    private static void insertOrderItem(int orderId, int foodId, int quantity, BigDecimal subtotal) throws SQLException {
        try (PreparedStatement pst = prepareStatement(INSERT_ORDER_ITEM_QUERY)) {
            pst.setInt(1, orderId);
            pst.setInt(2, foodId);
            pst.setInt(3, quantity);
            pst.setBigDecimal(4, subtotal);
            pst.executeUpdate();
        }
    }

    private static void updateOrderItem(int orderId, int foodId, int quantity, BigDecimal subtotal) throws SQLException {
        try (PreparedStatement pst = prepareStatement(UPDATE_ORDER_ITEM_QUERY)) {
            pst.setInt(1, quantity);
            pst.setBigDecimal(2, subtotal);
            pst.setInt(3, orderId);
            pst.setInt(4, foodId);
            pst.executeUpdate();
        }
    }

    private static Integer getItemQuantity(int orderId, int foodId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(SELECT_ITEM_QUANTITY_QUERY)) {
            pst.setInt(1, orderId);
            pst.setInt(2, foodId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? rs.getInt("quantity") : null;
            }
        }
    }

    private static void updateOrderTotal(int orderId) throws SQLException {
        String query = "UPDATE orders SET total = " +
                "(SELECT COALESCE(SUM(subtotal), 0) FROM order_items WHERE order_id = ?) " +
                "WHERE order_id = ?";

        try (PreparedStatement pst = prepareStatement(query)) {
            pst.setInt(1, orderId);
            pst.setInt(2, orderId);
            pst.executeUpdate();
        }
    }

    private static void loadOrderItems(OrderModel order) throws SQLException {
        try (PreparedStatement pst = prepareStatement(SELECT_ORDER_ITEMS_QUERY)) {
            pst.setInt(1, order.getOrderId());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    MenuItemModel item = MenuDAOHelpers.mapResultSetToMenuItem(rs);
                    int quantity = rs.getInt("quantity");
                    BigDecimal subtotal = rs.getBigDecimal("subtotal");
                    order.addItem(item, quantity, subtotal);
                }
            }
        }
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve
     * @return The OrderModel if found, null otherwise
     * @throws SQLException if there's a database access error
     */
    public static OrderModel getOrderById(int orderId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(SELECT_ORDER_BY_ID_QUERY)) {
            pst.setInt(1, orderId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    OrderModel order = OrderDAOHelpers.mapResultSetToOrder(rs);
                    loadOrderItems(order);
                    return order;
                }
            }
        }
        return null;
    }
}