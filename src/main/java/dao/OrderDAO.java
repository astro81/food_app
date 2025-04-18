package dao;

import config.DatabaseConnection;
import dao.helpers.MenuDAOHelpers;
import model.MenuItemModel;
import model.OrderModel;
import dao.helpers.OrderDAOHelpers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.helpers.ConnectionHelper.prepareStatement;

public class OrderDAO {
    // SQL Queries
    private static final String INSERT_ORDER_QUERY = "INSERT INTO orders (user_id, order_date, status) VALUES (?, CURRENT_TIMESTAMP, ?)";
    private static final String INSERT_ORDER_ITEM_QUERY = "INSERT INTO order_items (order_id, food_id, quantity) VALUES (?, ?, ?)";
    private static final String SELECT_PENDING_ORDER_QUERY = "SELECT o.* FROM orders o WHERE o.user_id = ? AND o.status = 'pending'";
    private static final String SELECT_ORDER_ITEMS_QUERY = "SELECT mi.* FROM order_items oi " + 
                    "JOIN MenuItem mi ON oi.food_id = mi.food_id " +
                    "WHERE oi.order_id = ?";
    private static final String UPDATE_ORDER_STATUS_QUERY = "UPDATE orders SET status = 'confirmed' WHERE order_id = ?";

    /**
     * Creates a new order or adds item to existing pending order
     */
    public static void createOrder(int userId, MenuItemModel item) throws SQLException {
        OrderModel pendingOrder = getPendingOrder(userId);
        if (pendingOrder == null) {
            pendingOrder = createNewOrder(userId);
        }

        addItemToOrder(pendingOrder.getOrderId(), item);
    }

    /**
     * Confirms a pending order
     */
    public static boolean confirmOrder(int userId) throws SQLException {
        OrderModel order = getPendingOrder(userId);
        if (order == null || !hasOrderItems(order.getOrderId())) {
            return false;
        }

        try (PreparedStatement pst = prepareStatement(UPDATE_ORDER_STATUS_QUERY)) {
            pst.setInt(1, order.getOrderId());
            return pst.executeUpdate() > 0;
        }
    }

    /**
     * Gets the pending order for a user
     */
    public static OrderModel getPendingOrder(int userId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(SELECT_PENDING_ORDER_QUERY)) {
            pst.setInt(1, userId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    OrderModel order = OrderDAOHelpers.mapResultSetToOrder(rs);
                    order.setItems(getOrderItems(order.getOrderId()));
                    return order;
                }
            }
        }
        return null;
    }

    private static OrderModel createNewOrder(int userId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(INSERT_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            OrderModel newOrder = new OrderModel(userId);
            OrderDAOHelpers.setOrderParameters(pst, newOrder);
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    newOrder.setOrderId(rs.getInt(1));
                }
            }
            return newOrder;
        }
    }

    private static void addItemToOrder(int orderId, MenuItemModel item) throws SQLException {
        try (PreparedStatement pst = prepareStatement(INSERT_ORDER_ITEM_QUERY)) {
            OrderDAOHelpers.setOrderItemParameters(pst, orderId, item);
            pst.executeUpdate();
        }
    }

    private static List<MenuItemModel> getOrderItems(int orderId) throws SQLException {
        List<MenuItemModel> items = new ArrayList<>();
        try (PreparedStatement pst = prepareStatement(SELECT_ORDER_ITEMS_QUERY)) {
            pst.setInt(1, orderId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    items.add(MenuDAOHelpers.mapResultSetToMenuItem(rs));
                }
            }
        }
        return items;
    }

    private static boolean hasOrderItems(int orderId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(SELECT_ORDER_ITEMS_QUERY)) {
            pst.setInt(1, orderId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }
}