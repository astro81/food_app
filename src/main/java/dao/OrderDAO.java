package dao;

import config.DatabaseConnection;
import model.MenuItemModel;
import model.OrderModel;
import model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.helpers.ConnectionHelper.prepareStatement;

public class OrderDAO {
    private static final List<OrderModel> pendingOrders = new ArrayList<>();

    // SQL Queries
    private static final String INSERT_ORDER_QUERY =
            "INSERT INTO orders (user_id, order_date, status) VALUES (?, CURRENT_TIMESTAMP, ?)";
    private static final String INSERT_ORDER_ITEM_QUERY =
            "INSERT INTO order_items (order_id, food_id, quantity) VALUES (?, ?, ?)";
    private static final String SELECT_LAST_ORDER_ID =
            "SELECT LAST_INSERT_ID()";

    public static void createOrder(int userId, MenuItemModel item) {
        OrderModel order = pendingOrders.stream()
                .filter(o -> o.getUserId() == userId && "pending".equals(o.getStatus()))
                .findFirst()
                .orElseGet(() -> {
                    OrderModel newOrder = new OrderModel(userId);
                    pendingOrders.add(newOrder);
                    return newOrder;
                });

        order.addItem(item);
        printOrder(order);
    }

    public static boolean confirmOrder(int userId) throws SQLException, ClassNotFoundException {
        OrderModel order = pendingOrders.stream()
                .filter(o -> o.getUserId() == userId && "pending".equals(o.getStatus()))
                .findFirst()
                .orElse(null);

        if (order == null || order.getItems().isEmpty()) {
            return false;
        }

        // Start database transaction
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // 1. Insert order header
            try (PreparedStatement pst = connection.prepareStatement(INSERT_ORDER_QUERY,
                    Statement.RETURN_GENERATED_KEYS)) {
                pst.setInt(1, order.getUserId());
                pst.setString(2, "confirmed");
                pst.executeUpdate();

                // Get generated order ID
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);

                    // 2. Insert order items
                    try (PreparedStatement itemPst = connection.prepareStatement(INSERT_ORDER_ITEM_QUERY)) {
                        for (MenuItemModel item : order.getItems()) {
                            itemPst.setInt(1, orderId);
                            itemPst.setInt(2, item.getFoodId());
                            itemPst.setInt(3, 1); // Default quantity
                            itemPst.addBatch();
                        }
                        itemPst.executeBatch();
                    }
                }
            }

            connection.commit();
            pendingOrders.remove(order);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    public static OrderModel getPendingOrder(int userId) {
        return pendingOrders.stream()
                .filter(o -> o.getUserId() == userId && "pending".equals(o.getStatus()))
                .findFirst()
                .orElse(null);
    }

    private static void printOrder(OrderModel order) {
        System.out.println("\n=== Pending Order ===");
        System.out.println("User ID: " + order.getUserId());
        System.out.println("Items Count: " + order.getItems().size());
        System.out.println("====================\n");
    }
}