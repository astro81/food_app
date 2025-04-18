package model;

import java.math.BigDecimal;

public class OrderItem {
    private final MenuItemModel item;
    private final int quantity;

    public OrderItem(MenuItemModel item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    // Getters
    public MenuItemModel getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    // Helper method for JSP if needed
    public BigDecimal getSubtotal() {
        return item.getFoodPrice().multiply(BigDecimal.valueOf(quantity));
    }
}