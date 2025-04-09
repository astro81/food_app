package controller;

import app.config.DatabaseConnection;
import model.MenuItemModel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for menu item operations.
 * <p>
 * This class provides database operations related to menu items, including:
 * <ul>
 *   <li>Inserting new menu items into the database</li>
 *   <li>Handling database connections and resources</li>
 *   <li>Managing SQL exceptions and error reporting</li>
 * </ul>
 *
 * <p><strong>Database Operations:</strong>
 * <ul>
 *   <li>Uses prepared statements to prevent SQL injection</li>
 *   <li>Converts Java types to appropriate SQL types</li>
 *   <li>Properly manages database connections</li>
 * </ul>
 *
 * <p><strong>Error Handling:</strong>
 * <ul>
 *   <li>Wraps ClassNotFoundException as SQLException</li>
 *   <li>Logs detailed SQL error information</li>
 *   <li>Propagates exceptions for service layer handling</li>
 * </ul>
 */
public class MenuItemDAO {

    /**
     * Inserts a new menu item into the database.
     * <p>
     * This method orchestrates the complete insert operation workflow:
     * <ol>
     *   <li>Delegates statement preparation to helper method</li>
     *   <li>Executes the parameterized SQL statement</li>
     *   <li>Evaluates the operation success based on affected rows</li>
     *   <li>Handles exceptions with appropriate logging and wrapping</li>
     * </ol>
     *
     * @param menuItem The MenuItemModel object containing all menu item attributes
     * @return true if exactly one row was affected by the insert operation
     * @throws SQLException if any database access error occurs, including:
     *                      <ul>
     *                        <li>Connection failures (unable to establish connection)</li>
     *                        <li>SQL syntax errors (malformed query)</li>
     *                        <li>Constraint violations (duplicate or invalid data)</li>
     *                        <li>Driver configuration issues</li>
     *                      </ul>
     */
    public boolean addMenuItem(MenuItemModel menuItem) throws SQLException {
        try {
            // SQL query with parameter placeholders
            PreparedStatement pst = getPreparedStatement(menuItem);

            // Execute update and check if successful
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (ClassNotFoundException e) {
            // Wrap driver not found exception as SQLException
            throw new SQLException("Database driver not found", e);
        } catch (SQLException e) {
            // Log detailed SQL error information
            System.err.println("SQL Error: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Vendor Error: " + e.getErrorCode());
            throw e;
        }
    }

    /**
     * Prepares a parameterized SQL statement for menu item insertion.
     * <p>
     * This helper method encapsulates the statement preparation logic:
     * <ol>
     *   <li>Defines the parameterized SQL insert statement</li>
     *   <li>Establishes database connection</li>
     *   <li>Binds all menu item attributes to their respective parameters</li>
     * </ol>
     *
     * @param menuItem The MenuItemModel containing data to be persisted
     * @return A fully parameterized PreparedStatement ready for execution
     * @throws SQLException if database access fails during statement preparation
     * @throws ClassNotFoundException if the database driver is not properly configured
     */
    private static PreparedStatement getPreparedStatement(MenuItemModel menuItem) throws SQLException, ClassNotFoundException {
        String INSERT_ITEM_SQL = "INSERT INTO MenuItem" +
                "(food_name, food_description, food_price, food_category, food_availability) " +
                "VALUES (?, ?, ?, ?, ?)";

        // Get database connection and prepare statement
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement pst = connection.prepareStatement(INSERT_ITEM_SQL);

        // Set parameters from menuItem object
        pst.setString(1, menuItem.getFoodName());
        pst.setString(2, menuItem.getFoodDescription());
        pst.setBigDecimal(3, menuItem.getFoodPrice());
        pst.setString(4, menuItem.getFoodCategory());
        pst.setString(5, menuItem.getFoodAvailability());
        return pst;
    }
}
