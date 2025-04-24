package dao.helpers;

import model.MenuItemModel;
import model.OrderItemModel;
import model.OrderModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

public class OrderDAOHelpers {
    public static OrderModel mapResultSetToOrder(ResultSet rs) throws SQLException {
        OrderModel order = new OrderModel(rs.getInt("user_id"));
        order.setOrderId(rs.getInt("order_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setStatus(rs.getString("status"));
        order.setTotal(rs.getBigDecimal("total"));
        return order;
    }

    public static OrderItemModel mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        MenuItemModel item = MenuDAOHelpers.mapResultSetToMenuItem(rs);
        int quantity = rs.getInt("quantity");
        BigDecimal subtotal = rs.getBigDecimal("subtotal");
        return new OrderItemModel(item, quantity, subtotal);
    }
}