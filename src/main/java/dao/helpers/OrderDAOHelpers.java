package dao.helpers;

import model.MenuItemModel;
import model.OrderModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOHelpers {
    /**
     * Sets parameters for order INSERT operations
     */
    public static void setOrderParameters(PreparedStatement pst, OrderModel order) throws SQLException {
        pst.setInt(1, order.getUserId());
        pst.setString(2, order.getStatus());
    }

    /**
     * Sets parameters for order item INSERT operations
     */
    public static void setOrderItemParameters(PreparedStatement pst, int orderId, MenuItemModel item) throws SQLException {
        pst.setInt(1, orderId);
        pst.setInt(2, item.getFoodId());
        pst.setInt(3, 1); // Default quantity
    }

    /**
     * Maps a ResultSet row to an OrderModel object
     */
    public static OrderModel mapResultSetToOrder(ResultSet rs) throws SQLException {
        OrderModel order = new OrderModel(rs.getInt("user_id"));
        order.setOrderId(rs.getInt("order_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setStatus(rs.getString("status"));
        return order;
    }
}