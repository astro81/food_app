package dao;

import model.MenuItemModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.helpers.ConnectionHelper.prepareStatement;
import static dao.helpers.MenuDAOHelpers.*;

/**
 * Data Access Object (DAO) for menu item operations.
 * Provides CRUD functionality for menu items in the database.
 */
public class MenuItemDAO {

    // SQL Query Constants
    private static final String SELECT_ALL_ITEMS_QUERY = "SELECT * FROM MenuItem ORDER BY food_id";
//    private static final String INSERT_ITEM_QUERY = "INSERT INTO MenuItem(food_name, food_description, food_price, food_category, food_availability, food_image) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String INSERT_ITEM_QUERY = "INSERT INTO MenuItem(food_name, food_description, food_price, food_category, food_availability, food_image, vendor_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
//    private static final String UPDATE_ITEM_QUERY = "UPDATE MenuItem SET food_name=?, food_description=?, food_price=?, food_category=?, food_availability=?, food_image=? WHERE food_id=?";
    private static final String UPDATE_ITEM_QUERY = "UPDATE MenuItem SET food_name=?, food_description=?, food_price=?, food_category=?, food_availability=?, food_image=?, vendor_id=? WHERE food_id=?";
    private static final String DELETE_ITEM_QUERY = "DELETE FROM MenuItem WHERE food_id=?";
    private static final String SELECT_ITEM_BY_ID_QUERY = "SELECT * FROM MenuItem WHERE food_id=?";
    private static final String SELECT_BY_CATEGORY_QUERY = "SELECT * FROM MenuItem WHERE food_category = ?";
    private static final String SELECT_BY_AVAILABILITY_QUERY = "SELECT * FROM MenuItem WHERE food_availability = ?";
    private static final String SELECT_BY_VENDOR_QUERY = "SELECT * FROM MenuItem WHERE vendor_id = ?";

    /**
     * Retrieves all menu items from the database.
     *
     * @return List of all menu items ordered by food_id
     * @throws SQLException if any database error occurs
     */
    public List<MenuItemModel> getAllMenuItems() throws SQLException {
        List<MenuItemModel> menuItems = new ArrayList<>();

        try (PreparedStatement pst = prepareStatement(SELECT_ALL_ITEMS_QUERY);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                menuItems.add(mapResultSetToMenuItem(rs));
            }
        }

        return menuItems;
    }

    /**
     * Retrieves a single menu item by its ID.
     *
     * @param itemId The ID of the item to retrieve
     * @return The menu item if found, null otherwise
     * @throws SQLException if any database error occurs
     */
    public MenuItemModel getMenuItemById(int itemId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(SELECT_ITEM_BY_ID_QUERY)) {
            pst.setInt(1, itemId);

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? mapResultSetToMenuItem(rs) : null;
            }
        }
    }

    /**
     * Get menu items by category
     */
    public List<MenuItemModel> getMenuItemsByCategory(String category) throws SQLException {
        return getMenuItemModels(category, SELECT_BY_CATEGORY_QUERY);
    }

    /**
     * Get menu items by availability
     */
    public List<MenuItemModel> getMenuItemsByAvailability(String availability) throws SQLException {
        return getMenuItemModels(availability, SELECT_BY_AVAILABILITY_QUERY);
    }

    /**
     * Adds a new menu item to the database.
     *
     * @param menuItem The menu item to be added
     * @return true if the operation was successful
     * @throws SQLException if any database error occurs
     */
    public boolean addMenuItem(MenuItemModel menuItem) throws SQLException {
        try (PreparedStatement pst = prepareStatement(INSERT_ITEM_QUERY)) {
            setMenuItemParameters(pst, menuItem);
            return pst.executeUpdate() > 0;
        }
    }

    /**
     * Updates an existing menu item in the database.
     *
     * @param menuItem The menu item with updated values
     * @return true if the operation was successful
     * @throws SQLException if any database error occurs
     */
    public boolean updateMenuItem(MenuItemModel menuItem) throws SQLException {
        try (PreparedStatement pst = prepareStatement(UPDATE_ITEM_QUERY)) {
            setMenuItemParameters(pst, menuItem);
            pst.setInt(8, menuItem.getFoodId());
            return pst.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a menu item from the database.
     *
     * @param itemId The ID of the item to be deleted
     * @return true if the operation was successful
     * @throws SQLException if any database error occurs
     */
    public boolean deleteMenuItem(int itemId) throws SQLException {
        try (PreparedStatement pst = prepareStatement(DELETE_ITEM_QUERY)) {
            pst.setInt(1, itemId);
            return pst.executeUpdate() > 0;
        }
    }

    // Add method to get items by vendor
    public List<MenuItemModel> getMenuItemsByVendor(int vendorId) throws SQLException {
        return getMenuItemModels(String.valueOf(vendorId), SELECT_BY_VENDOR_QUERY);
    }

}