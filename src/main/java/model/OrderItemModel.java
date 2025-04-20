package model;

import java.math.BigDecimal;

/**
 * Represents a single item within an order, combining a menu item with its quantity.
 * This immutable class provides methods to access the item, quantity, and calculated subtotal.
 */
public class OrderItemModel {
    private final MenuItemModel item;
    private final int quantity;

    /**
     * Constructs an order item with the specified menu item and quantity.
     *
     * @param item     The menu item
     * @param quantity The quantity ordered
     */
    public OrderItemModel(MenuItemModel item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    /**
     * Returns the menu item.
     *
     * @return The menu item
     */
    public MenuItemModel getItem() {
        return item;
    }

    /**
     * Returns the quantity ordered.
     *
     * @return The quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Calculates the subtotal for this item (price × quantity).
     * Useful for order summaries and invoices.
     *
     * @return The subtotal as BigDecimal
     */
    public BigDecimal getSubtotal() {
        return item.getFoodPrice().multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Returns a string representation of this order item.
     * Format: "quantity × item name - subtotal"
     *
     * @return String representation of the order item
     */
    @Override
    public String toString() {
        return String.format("%d × %s - %s",
                quantity,
                item.getFoodName(),
                getSubtotal().toString());
    }
}