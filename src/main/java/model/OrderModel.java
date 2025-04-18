package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    private int orderId;
    private int userId;
    private List<MenuItemModel> items;
    private Timestamp orderDate;
    private String status;

    // Constructors
    public OrderModel() {
        this.items = new ArrayList<>();
    }

    public OrderModel(int userId) {
        this();
        this.userId = userId;
        this.status = "pending";
    }

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public List<MenuItemModel> getItems() { return items; }
    public void setItems(List<MenuItemModel> items) { this.items = items; }
    public void addItem(MenuItemModel item) { this.items.add(item); }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotal() {
        return items.stream()
                .map(MenuItemModel::getFoodPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}