package dao.helpers;

import model.MenuItemModel;
import model.OrderItem;
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
     * Sets parameters for order item INSERT/UPDATE operations
     */
    public static void setOrderItemParameters(PreparedStatement pst, int orderId,
                                              MenuItemModel item, int quantity) throws SQLException {
        // For UPDATE statements
        if (pst.getParameterMetaData().getParameterCount() == 3) {
            pst.setInt(1, quantity);
            pst.setInt(2, orderId);
            pst.setInt(3, item.getFoodId());
        }
        // For INSERT statements
        else {
            pst.setInt(1, orderId);
            pst.setInt(2, item.getFoodId());
            pst.setInt(3, quantity);
        }
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
    public static OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        MenuItemModel item = MenuDAOHelpers.mapResultSetToMenuItem(rs);
        int quantity = rs.getInt("quantity");
        return new OrderItem(item, quantity);
    }
}