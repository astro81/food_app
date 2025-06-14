package model;

import java.math.BigDecimal;

/**
 * Model class representing a menu item in the system.
 * Contains menu item attributes and provides getter/setter methods.
 */
public class MenuItemModel {
    // Menu item attributes
    private  int foodId;
    private String foodName;            // Name of the food item
    private String foodDescription;     // Description of the food item
    private BigDecimal foodPrice;           // Price of the food item
    private String foodCategory;        // Category of the food (meals, snacks, sweets, drinks)
    private String foodAvailability;    // Availability status (available, out_of_order)
    private String foodImage;           // Field for image path
    private int vendorId;

    /**
     * Constructor to create a new MenuItemModel with all attributes.
     *
     * @param foodName Name of the food item
     * @param foodDescription Description of the food item
     * @param foodPrice Price of the food item
     * @param foodCategory Category of the food
     * @param foodAvailability Availability status
     * @param foodImage Food Image
     * @param vendorId Vendor ID
     */
    public MenuItemModel(String foodName, String foodDescription, BigDecimal foodPrice, String foodCategory, String foodAvailability, String foodImage, int vendorId) {
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.foodPrice = foodPrice;
        this.foodCategory = foodCategory;
        this.foodAvailability = foodAvailability;
        this.foodImage = foodImage;
        this.vendorId = vendorId;
    }

    // Constructor for existing items (with ID)
    public MenuItemModel(int foodId, String foodName, String foodDescription, BigDecimal foodPrice, String foodCategory, String foodAvailability, String foodImage, int vendorId) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.foodPrice = foodPrice;
        this.foodCategory = foodCategory;
        this.foodAvailability = foodAvailability;
        this.foodImage = foodImage;
        this.vendorId = vendorId;
    }


    public int getFoodId() { return foodId; }

    public void setFoodId(int foodId) { this.foodId = foodId; }

    /** @return Name of the food item */
    public String getFoodName() { return foodName; }

    /** @param foodName Name of the food item to set */
    public void setFoodName(String foodName) { this.foodName = foodName; }

    /** @return Description of the food item */
    public String getFoodDescription() { return foodDescription; }

    /** @param foodDescription Description to set */
    public void setFoodDescription(String foodDescription) { this.foodDescription = foodDescription; }

    /** @return Price of the food item */
    public BigDecimal getFoodPrice() { return foodPrice; }

    /** @param foodPrice Price to set */
    public void setFoodPrice(BigDecimal foodPrice) { this.foodPrice = foodPrice; }

    /** @return Category of the food */
    public String getFoodCategory() { return foodCategory; }

    /** @param foodCategory Category to set */
    public void setFoodCategory(String foodCategory) { this.foodCategory = foodCategory; }

    /** @return Availability status */
    public String getFoodAvailability() { return foodAvailability; }

    /** @param foodAvailability Availability status to set */
    public void setFoodAvailability(String foodAvailability) { this.foodAvailability = foodAvailability; }

    /** @return Food Image */
    public String getFoodImage() { return foodImage; }

    /** @param foodImage Food Image to set */
    public void setFoodImage(String foodImage) { this.foodImage = foodImage; }

    /** @return Vendor Id */
    public int getVendorId() { return vendorId; }

    /** @param vendorId Vendor Id t set */
    public void setVendorId(int vendorId) { this.vendorId = vendorId; }
}
