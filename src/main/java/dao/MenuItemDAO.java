package dao;

import config.DatabaseConnection;
import model.MenuItemModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for menu item operations.
 * Provides CRUD functionality for menu items in the database.
 */
public class MenuItemDAO {

    // SQL Query Constants
    private static final String SELECT_ALL_ITEMS_SQL = "SELECT * FROM MenuItem ORDER BY food_id";
    private static final String INSERT_ITEM_SQL = "INSERT INTO MenuItem(food_name, food_description, food_price, " + "food_category, food_availability) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_ITEM_SQL = "UPDATE MenuItem SET food_name=?, food_description=?, food_price=?, food_category=?, food_availability=? WHERE food_id=?";
    private static final String DELETE_ITEM_SQL = "DELETE FROM MenuItem WHERE food_id=?";
    private static final String SELECT_ITEM_BY_ID_SQL = "SELECT * FROM MenuItem WHERE food_id=?";

    /**
     * Retrieves all menu items from the database.
     *
     * @return List of all menu items ordered by food_id
     * @throws SQLException if any database error occurs
     */
    public List<MenuItemModel> getAllMenuItems() throws SQLException {
        List<MenuItemModel> menuItems = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(SELECT_ALL_ITEMS_SQL);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                menuItems.add(mapResultSetToMenuItem(rs));
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
        return menuItems;
    }

    /**
     * Adds a new menu item to the database.
     *
     * @param menuItem The menu item to be added
     * @return true if the operation was successful
     * @throws SQLException if any database error occurs
     */
    public boolean addMenuItem(MenuItemModel menuItem) throws SQLException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(INSERT_ITEM_SQL);

            setInsertParameters(pst, menuItem);
            return pst.executeUpdate() > 0;

        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }

    /**
     * Sets parameters for an INSERT operation.
     *
     * @param pst The PreparedStatement to set parameters on
     * @param menuItem The menu item containing the data
     * @throws SQLException if there's an error setting parameters
     */
    private void setInsertParameters(PreparedStatement pst, MenuItemModel menuItem)
            throws SQLException {
        pst.setString(1, menuItem.getFoodName());
        pst.setString(2, menuItem.getFoodDescription());
        pst.setBigDecimal(3, menuItem.getFoodPrice());
        pst.setString(4, menuItem.getFoodCategory());
        pst.setString(5, menuItem.getFoodAvailability());
    }

    /**
     * Updates an existing menu item in the database.
     *
     * @param menuItem The menu item with updated values
     * @return true if the operation was successful
     * @throws SQLException if any database error occurs
     */
    public boolean updateMenuItem(MenuItemModel menuItem) throws SQLException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(UPDATE_ITEM_SQL);

            setUpdateParameters(pst, menuItem);
            return pst.executeUpdate() > 0;

        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }

    /**
     * Sets parameters for an UPDATE operation.
     *
     * @param pst The PreparedStatement to set parameters on
     * @param menuItem The menu item containing the updated data
     * @throws SQLException if there's an error setting parameters
     */
    private void setUpdateParameters(PreparedStatement pst, MenuItemModel menuItem)
            throws SQLException {
        setInsertParameters(pst, menuItem);
        pst.setInt(6, menuItem.getFoodId());
    }

    /**
     * Deletes a menu item from the database.
     *
     * @param itemId The ID of the item to be deleted
     * @return true if the operation was successful
     * @throws SQLException if any database error occurs
     */
    public boolean deleteMenuItem(int itemId) throws SQLException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(DELETE_ITEM_SQL);

            pst.setInt(1, itemId);
            return pst.executeUpdate() > 0;

        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }

    /**
     * Retrieves a single menu item by its ID.
     *
     * @param itemId The ID of the item to retrieve
     * @return The menu item if found, null otherwise
     * @throws SQLException if any database error occurs
     */
    public MenuItemModel getMenuItemById(int itemId) throws SQLException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(SELECT_ITEM_BY_ID_SQL);

            pst.setInt(1, itemId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next() ? mapResultSetToMenuItem(rs) : null;
            }

        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }

    /**
     * Maps a ResultSet row to a MenuItemModel object.
     *
     * @param rs The ResultSet containing the menu item data
     * @return A populated MenuItemModel object
     * @throws SQLException if there's an error accessing the ResultSet
     */
    private MenuItemModel mapResultSetToMenuItem(ResultSet rs) throws SQLException {
        return new MenuItemModel(
                rs.getInt("food_id"),
                rs.getString("food_name"),
                rs.getString("food_description"),
                rs.getBigDecimal("food_price"),
                rs.getString("food_category"),
                rs.getString("food_availability")
        );
    }

}