package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderModel {
    private int orderId;
    private int userId;
    private List<MenuItemModel> items;
    private Timestamp orderDate;
    private String status;
    private Map<MenuItemModel, Integer> itemsWithQuantities = new HashMap<>(); // Item -> Quantity

    // Constructors
    public OrderModel() {
        this.itemsWithQuantities = new HashMap<>();
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

    public void setItems(List<MenuItemModel> items) { this.items = items; }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Add or update item quantity
    public void addItem(MenuItemModel item) {
        itemsWithQuantities.merge(item, 1, Integer::sum);
    }

    // Get all items with quantities
    public Map<MenuItemModel, Integer> getItemsWithQuantities() {
        return itemsWithQuantities;
    }

    public List<OrderItem> getItems() {
        List<OrderItem> items = new ArrayList<>();
        for (Map.Entry<MenuItemModel, Integer> entry : itemsWithQuantities.entrySet()) {
            items.add(new OrderItem(entry.getKey(), entry.getValue()));
        }
        return items;
    }

    // Calculate total
    public BigDecimal getTotal() {
        return itemsWithQuantities.entrySet().stream()
                .map(entry -> entry.getKey().getFoodPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}