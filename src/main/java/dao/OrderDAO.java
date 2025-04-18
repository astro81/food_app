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
    private static final String SELECT_ORDER_ITEMS_QUERY =
            "SELECT mi.*, oi.quantity FROM order_items oi " +
                    "JOIN MenuItem mi ON oi.food_id = mi.food_id " +
                    "WHERE oi.order_id = ?";
    private static final String UPDATE_ORDER_STATUS_QUERY = "UPDATE orders SET status = 'confirmed' WHERE order_id = ?";


    public static void createOrder(int userId, MenuItemModel item) throws SQLException {
        OrderModel pendingOrder = getPendingOrder(userId);
        if (pendingOrder == null) {
            pendingOrder = createNewOrder(userId);
        }
        pendingOrder.addItem(item);
        saveOrderItem(pendingOrder.getOrderId(), item);
    }

    private static void saveOrderItem(int orderId, MenuItemModel item) throws SQLException {
        // Check if item already exists in order
        Integer currentQuantity = getItemQuantity(orderId, item.getFoodId());
        int newQuantity = currentQuantity != null ? currentQuantity + 1 : 1;

        if (currentQuantity != null) {
            // Update existing item
            try (PreparedStatement pst = prepareStatement(
                    "UPDATE order_items SET quantity = ? WHERE order_id = ? AND food_id = ?")) {
                pst.setInt(1, newQuantity);
                pst.setInt(2, orderId);
                pst.setInt(3, item.getFoodId());
                pst.executeUpdate();
            }
        } else {
            // Insert new item
            try (PreparedStatement pst = prepareStatement(INSERT_ORDER_ITEM_QUERY)) {
                pst.setInt(1, orderId);
                pst.setInt(2, item.getFoodId());
                pst.setInt(3, 1); // Initial quantity
                pst.executeUpdate();
            }
        }
    }

    private static Integer getItemQuantity(int orderId, int foodId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(
                "SELECT quantity FROM order_items WHERE order_id = ? AND food_id = ?")) {
            pst.setInt(1, orderId);
            pst.setInt(2, foodId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? rs.getInt("quantity") : null;
            }
        }
    }


    // Update getPendingOrder to load quantities
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

    private static void loadOrderItems(OrderModel order) throws SQLException {
        try (PreparedStatement pst = prepareStatement(SELECT_ORDER_ITEMS_QUERY)) {
            pst.setInt(1, order.getOrderId());

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    MenuItemModel item = MenuDAOHelpers.mapResultSetToMenuItem(rs);
                    int quantity = rs.getInt("quantity");
                    order.getItemsWithQuantities().put(item, quantity);
                }
            }
        }
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
        // First check if item already exists in order
        Integer currentQuantity = getItemQuantity(orderId, item.getFoodId());
        int newQuantity = currentQuantity != null ? currentQuantity + 1 : 1;

        if (currentQuantity != null) {
            // Update existing item quantity
            try (PreparedStatement pst = prepareStatement(
                    "UPDATE order_items SET quantity = ? WHERE order_id = ? AND food_id = ?")) {
                OrderDAOHelpers.setOrderItemParameters(pst, orderId, item, newQuantity);
                pst.executeUpdate();
            }
        } else {
            // Insert new item with quantity 1
            try (PreparedStatement pst = prepareStatement(INSERT_ORDER_ITEM_QUERY)) {
                OrderDAOHelpers.setOrderItemParameters(pst, orderId, item, 1);
                pst.executeUpdate();
            }
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