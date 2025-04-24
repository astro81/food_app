package model;

import java.math.BigDecimal;

public class OrderItemModel {
    private final MenuItemModel item;
    private final int quantity;
    private final BigDecimal subtotal;

    public OrderItemModel(MenuItemModel item, int quantity, BigDecimal subtotal) {
        this.item = item;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public MenuItemModel getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    @Override
    public String toString() {
        return String.format("%d × %s - %s",
                quantity,
                item.getFoodName(),
                subtotal.toString());
    }
}