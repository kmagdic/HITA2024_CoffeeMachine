package t5_marin.coffeemachine;

import java.sql.*;
import java.util.*;

public class CoffeeMachineManagerDB {
    private static Connection connection;

    // Constructor to initialize database connection
    public CoffeeMachineManagerDB(Connection connection) {
        CoffeeMachineManagerDB.connection = connection;
    }

    // Check and create necessary tables
    public void checkAndCreateTable() {
        if (connection == null) {
            throw new IllegalStateException("Database connection is not established.");
        }

        try {
            DatabaseMetaData metaData = connection.getMetaData();

            // Check for coffee_machine table
            ResultSet tables = metaData.getTables(null, null, "COFFEE_MACHINE", null);
            if (!tables.next()) {
                System.out.println("Table 'coffee_machine' not found. Creating the table...");
                String createTableSQL = "CREATE TABLE coffee_machine (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "water INT, " +
                        "milk INT, " +
                        "coffee_beans INT, " +
                        "cups INT, " +
                        "money FLOAT)";
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate(createTableSQL);
                    System.out.println("Table 'coffee_machine' created.");
                }
            } else {
                System.out.println("Table 'coffee_machine' found.");
            }

            // Check for coffee_type table
            tables = metaData.getTables(null, null, "COFFEE_TYPE", null);
            if (!tables.next()) {
                System.out.println("Table 'coffee_type' not found. Creating the table...");
                String createCoffeeTypesSQL = "CREATE TABLE coffee_type (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(255), " +
                        "water INT, " +
                        "milk INT, " +
                        "coffee_beans INT, " +
                        "price FLOAT)";
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate(createCoffeeTypesSQL);
                    System.out.println("Table 'coffee_type' created.");

                    // Insert default coffee types
                    String insertDefaults = "INSERT INTO coffee_type (name, water, milk, coffee_beans, price) VALUES " +
                            "('Espresso', 50, 0, 18, 1.50), " +
                            "('Latte', 200, 150, 24, 2.50), " +
                            "('Cappuccino', 200, 100, 24, 3.00)";
                    stmt.executeUpdate(insertDefaults);
                    System.out.println("Default coffee types inserted into 'coffee_type' table.");
                }
            } else {
                System.out.println("Table 'coffee_type' found.");
            }

        } catch (SQLException e) {
            System.out.println("Error checking or creating tables: " + e.getMessage());
        }
    }

    // Add a new coffee machine to the database
    public void addCoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        String insertSQL = "INSERT INTO coffee_machine (water, milk, coffee_beans, cups, money) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, water);
            pstmt.setInt(2, milk);
            pstmt.setInt(3, coffeeBeans);
            pstmt.setInt(4, cups);
            pstmt.setFloat(5, money);
            pstmt.executeUpdate();
            System.out.println("Coffee machine added successfully!");
        } catch (SQLException e) {
            System.out.println("Error inserting coffee machine: " + e.getMessage());
        }
    }

    // View all coffee machines in the database
    public List<CoffeeMachine> viewCoffeeMachines() {
        List<CoffeeMachine> machines = new ArrayList<>();
        String selectSQL = "SELECT * FROM coffee_machine";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int water = rs.getInt("water");
                int milk = rs.getInt("milk");
                int coffeeBeans = rs.getInt("coffee_beans");
                int cups = rs.getInt("cups");
                float money = rs.getFloat("money");

                CoffeeMachine machine = new CoffeeMachine(id, water, milk, coffeeBeans, cups, money);
                machines.add(machine);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching coffee machines: " + e.getMessage());
        }
        return machines;
    }

    // Update a coffee machine's details
    public void updateCoffeeMachine(int id, int water, int milk, int coffeeBeans, int cups, float money) {
        String updateSQL = "UPDATE coffee_machine SET water = ?, milk = ?, coffee_beans = ?, cups = ?, money = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(1, water);
            pstmt.setInt(2, milk);
            pstmt.setInt(3, coffeeBeans);
            pstmt.setInt(4, cups);
            pstmt.setFloat(5, money);
            pstmt.setInt(6, id);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Coffee machine updated successfully!");
            } else {
                System.out.println("No coffee machine found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error updating coffee machine: " + e.getMessage());
        }
    }

    // Delete a coffee machine from the database
    public void deleteCoffeeMachine(int id) {
        String deleteSQL = "DELETE FROM coffee_machine WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Coffee machine deleted successfully!");
            } else {
                System.out.println("No coffee machine found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting coffee machine: " + e.getMessage());
        }
    }
    // Fetch a coffee machine by its ID from the database
    public CoffeeMachine getCoffeeMachineById(int id) {
        String selectSQL = "SELECT * FROM coffee_machine WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int water = rs.getInt("water");
                    int milk = rs.getInt("milk");
                    int coffeeBeans = rs.getInt("coffee_beans");
                    int cups = rs.getInt("cups");
                    float money = rs.getFloat("money");

                    return new CoffeeMachine(id, water, milk, coffeeBeans, cups, money);
                } else {
                    System.out.println("No coffee machine found with ID " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching coffee machine: " + e.getMessage());
        }
        return null;
    }

}
