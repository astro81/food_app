package dao.helpers;

import model.MenuItemModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.helpers.ConnectionHelper.prepareStatement;

public class MenuDAOHelpers {
    /**
     * Sets parameters for INSERT/UPDATE operations
     */
    public static void setMenuItemParameters(PreparedStatement pst, MenuItemModel menuItem) throws SQLException {
        pst.setString(1, menuItem.getFoodName());
        pst.setString(2, menuItem.getFoodDescription());
        pst.setBigDecimal(3, menuItem.getFoodPrice());
        pst.setString(4, menuItem.getFoodCategory());
        pst.setString(5, menuItem.getFoodAvailability());
        pst.setString(6, menuItem.getFoodImage());
        pst.setInt(7, menuItem.getVendorId());
    }

    /**
     * Maps a ResultSet row to a MenuItemModel object.
     *
     * @param rs The ResultSet containing the menu item data
     * @return A populated MenuItemModel object
     * @throws SQLException if there's an error accessing the ResultSet
     */
    public static MenuItemModel mapResultSetToMenuItem(ResultSet rs) throws SQLException {
        return new MenuItemModel(
                rs.getInt("food_id"),
                rs.getString("food_name"),
                rs.getString("food_description"),
                rs.getBigDecimal("food_price"),
                rs.getString("food_category"),
                rs.getString("food_availability"),
                rs.getString("food_image"),
                rs.getInt("vendor_id")
        );
    }

    public static List<MenuItemModel> getMenuItemModels(String filterType, String selectByFilterType) throws SQLException {
        List<MenuItemModel> menuItems = new ArrayList<>();

        try (PreparedStatement pst = prepareStatement(selectByFilterType)) {
            pst.setString(1, filterType);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) menuItems.add(mapResultSetToMenuItem(rs));
            }
        }

        return menuItems;
    }

}
