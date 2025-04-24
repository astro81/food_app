package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class OrderModel {
    private int orderId;
    private int userId;
    private Timestamp orderDate;
    private String status;
    private BigDecimal total;
    private Map<MenuItemModel, Integer> itemsWithQuantities;
    private Map<MenuItemModel, BigDecimal> subtotals;

    public OrderModel() {
        this.itemsWithQuantities = new HashMap<>();
        this.subtotals = new HashMap<>();
        this.total = BigDecimal.ZERO;
    }

    public OrderModel(int userId) {
        this();
        this.userId = userId;
        this.status = "pending";
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Map<MenuItemModel, Integer> getItemsWithQuantities() {
        return itemsWithQuantities;
    }

    public Map<MenuItemModel, BigDecimal> getSubtotals() {
        return subtotals;
    }

    public void addItem(MenuItemModel item, int quantity, BigDecimal subtotal) {
        itemsWithQuantities.put(item, quantity);
        subtotals.put(item, subtotal);
    }
}