package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a customer order in the system.
 * Tracks order details including items, quantities, status, and pricing information.
 * An order can contain multiple menu items with their respective quantities.
 */
public class OrderModel {
    private int orderId;
    private int userId;
    private Timestamp orderDate;
    private String status;
    private Map<MenuItemModel, Integer> itemsWithQuantities;

    /**
     * Default constructor.
     * Initializes a new empty order with no items.
     */
    public OrderModel() {
        this.itemsWithQuantities = new HashMap<>();
    }

    /**
     * Constructs an order for a specific user with "pending" status.
     *
     * @param userId The ID of the user placing the order
     */
    public OrderModel(int userId) {
        this();
        this.userId = userId;
        this.status = "pending";
    }

    /**
     * Returns the unique identifier for this order.
     *
     * @return The order ID
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the unique identifier for this order.
     *
     * @param orderId The order ID
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Returns the ID of the user who placed this order.
     *
     * @return The user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who placed this order.
     *
     * @param userId The user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the timestamp when this order was placed.
     *
     * @return The order date timestamp
     */
    public Timestamp getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the timestamp when this order was placed.
     *
     * @param orderDate The order date timestamp
     */
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Returns the current status of this order (e.g., "pending", "confirmed").
     *
     * @return The order status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of this order.
     *
     * @param status The order status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Adds a menu item to the order or increments its quantity if already present.
     *
     * @param item The menu item to add
     */
    public void addItem(MenuItemModel item) {
        itemsWithQuantities.merge(item, 1, Integer::sum);
    }

    /**
     * Returns the raw map of menu items and their quantities.
     *
     * @return A map of menu items to their quantities
     */
    public Map<MenuItemModel, Integer> getItemsWithQuantities() {
        return itemsWithQuantities;
    }

    /**
     * Returns a list of order items with their quantities as OrderItemModel objects.
     * This is useful for display in views and processing order details.
     *
     * @return List of OrderItemModel objects
     */
    public List<OrderItemModel> getItems() {
        List<OrderItemModel> orderItems = new ArrayList<>();
        for (Map.Entry<MenuItemModel, Integer> entry : itemsWithQuantities.entrySet()) {
            orderItems.add(new OrderItemModel(entry.getKey(), entry.getValue()));
        }
        return orderItems;
    }

    /**
     * Calculates the total cost of the order based on item prices and quantities.
     *
     * @return The total order amount
     */
    public BigDecimal getTotal() {
        return itemsWithQuantities.entrySet().stream()
                .map(entry -> entry.getKey().getFoodPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Checks if the order contains any items.
     *
     * @return true if the order is empty, false otherwise
     */
    public boolean isEmpty() {
        return itemsWithQuantities.isEmpty();
    }

    /**
     * Returns the total number of items in the order (sum of all quantities).
     *
     * @return The total item count
     */
    public int getTotalItemCount() {
        return itemsWithQuantities.values().stream().mapToInt(Integer::intValue).sum();
    }
}