package dao;

import config.DatabaseConnection;
import dao.helpers.MenuDAOHelpers;
import dao.helpers.OrderDAOHelpers;
import model.MenuItemModel;
import model.OrderModel;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

import static dao.helpers.ConnectionHelper.*;

public class OrderDAO {
    // SQL Queries
    private static final String INSERT_ORDER = "INSERT INTO orders (user_id, total, status) VALUES (?, ?, ?)";
    private static final String INSERT_ORDER_ITEM = "INSERT INTO order_items (order_id, food_id, quantity, subtotal) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ORDER_ITEM = "UPDATE order_items SET quantity = ?, subtotal = ? WHERE order_id = ? AND food_id = ?";
    private static final String GET_PENDING_ORDER = "SELECT * FROM orders WHERE user_id = ? AND status = 'pending'";
    private static final String GET_ORDER_ITEMS = """
            SELECT mi.*, oi.quantity, oi.subtotal FROM order_items oi\s
            JOIN MenuItem mi ON oi.food_id = mi.food_id\s
            WHERE oi.order_id = ?""";
    private static final String GET_ITEM_QUANTITY = "SELECT quantity, subtotal FROM order_items WHERE order_id = ? AND food_id = ?";
    private static final String CONFIRM_ORDER = "UPDATE orders SET status = 'confirmed' WHERE order_id = ?";
    private static final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE order_id = ? ORDER BY order_date DESC";
    private static final String DELETE_ORDER_ITEMS = "DELETE FROM order_items WHERE order_id = ?";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id = ? AND user_id = ?";
    private static final String UPDATE_ORDER_TOTAL = """
            UPDATE orders SET total =\s
            (SELECT COALESCE(SUM(subtotal), 0) FROM order_items WHERE order_id = ?)\s
            WHERE order_id = ?""";

    public static void createOrder(int userId, MenuItemModel item) throws SQLException {
        OrderModel pendingOrder = getPendingOrder(userId);
        if (pendingOrder == null) {
            pendingOrder = createNewOrder(userId);
        }

        updateOrderWithItem(pendingOrder, item);
        updateOrderTotal(pendingOrder.getOrderId());
    }

    public static OrderModel getPendingOrder(int userId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(GET_PENDING_ORDER)) {
            pst.setInt(1, userId);
            return executeOrderQuery(pst);
        }
    }

    public static boolean confirmOrder(int userId) throws SQLException {
        OrderModel order = getPendingOrder(userId);
        if (order == null || order.getItemsWithQuantities().isEmpty()) {
            return false;
        }

        try (PreparedStatement pst = prepareStatement(CONFIRM_ORDER)) {
            pst.setInt(1, order.getOrderId());
            return pst.executeUpdate() > 0;
        }
    }

    public static OrderModel getOrderById(int orderId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(GET_ORDER_BY_ID)) {
            pst.setInt(1, orderId);
            return executeOrderQuery(pst);
        }
    }

    public static boolean deleteOrder(int orderId, int userId) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            deleteOrderItems(conn, orderId);
            boolean success = deleteOrderRecord(conn, orderId, userId);

            if (success) {
                conn.commit();
            } else {
                conn.rollback();
            }
            return success;
        } catch (SQLException | ClassNotFoundException e) {
            rollbackTransaction(conn);
            throw new SQLException("Failed to delete order", e);
        } finally {
            closeConnection(conn);
        }
    }

    private static OrderModel createNewOrder(int userId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
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

    private static void updateOrderWithItem(OrderModel order, MenuItemModel item) throws SQLException {
        int orderId = order.getOrderId();
        int foodId = item.getFoodId();
        BigDecimal price = item.getFoodPrice();

        Integer currentQuantity = getItemQuantity(orderId, foodId);
        int newQuantity = (currentQuantity != null) ? currentQuantity + 1 : 1;
        BigDecimal subtotal = price.multiply(BigDecimal.valueOf(newQuantity));

        if (currentQuantity != null) {
            updateOrderItem(orderId, foodId, newQuantity, subtotal);
        } else {
            insertOrderItem(orderId, foodId, 1, price);
        }
    }

    private static void insertOrderItem(int orderId, int foodId, int quantity, BigDecimal subtotal) throws SQLException {
        executeUpdate(INSERT_ORDER_ITEM, orderId, foodId, quantity, subtotal);
    }

    private static void updateOrderItem(int orderId, int foodId, int quantity, BigDecimal subtotal) throws SQLException {
        executeUpdate(UPDATE_ORDER_ITEM, quantity, subtotal, orderId, foodId);
    }

    private static Integer getItemQuantity(int orderId, int foodId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(GET_ITEM_QUANTITY)) {
            pst.setInt(1, orderId);
            pst.setInt(2, foodId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? rs.getInt("quantity") : null;
            }
        }
    }

    private static void updateOrderTotal(int orderId) throws SQLException {
        executeUpdate(UPDATE_ORDER_TOTAL, orderId, orderId);
    }

    private static OrderModel executeOrderQuery(PreparedStatement pst) throws SQLException {
        try (ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                OrderModel order = OrderDAOHelpers.mapResultSetToOrder(rs);
                loadOrderItems(order);
                return order;
            }
        }
        return null;
    }

    private static void loadOrderItems(OrderModel order) throws SQLException {
        try (PreparedStatement pst = prepareStatement(GET_ORDER_ITEMS)) {
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

    private static void deleteOrderItems(Connection conn, int orderId) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(DELETE_ORDER_ITEMS)) {
            pst.setInt(1, orderId);
            pst.executeUpdate();
        }
    }

    private static boolean deleteOrderRecord(Connection conn, int orderId, int userId) throws SQLException {
        try (PreparedStatement pst = conn.prepareStatement(DELETE_ORDER)) {
            pst.setInt(1, orderId);
            pst.setInt(2, userId);
            return pst.executeUpdate() > 0;
        }
    }

    private static void executeUpdate(String query, Object... params) throws SQLException {
        try (PreparedStatement pst = prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
            pst.executeUpdate();
        }
    }

    private static void rollbackTransaction(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                // Log the error if needed
            }
        }
    }

    private static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                // Log the error if needed
            }
        }
    }
}