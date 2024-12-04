package t5_marin.coffeemachine;

import java.sql.*;
import java.util.*;

public class CoffeeTypeDAO {

    // CREATE operation
    public void insertCoffeeType(CoffeeType coffeeType, Connection connection) throws SQLException {
        String insertSQL = "INSERT INTO CoffeeType (name, waterNeeded, milkNeeded, coffeeBeansNeeded, price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, coffeeType.getName());
            pstmt.setInt(2, coffeeType.getWaterNeeded()); // waterNeeded instead of water
            pstmt.setInt(3, coffeeType.getMilkNeeded()); // milkNeeded instead of milk
            pstmt.setInt(4, coffeeType.getCoffeeBeansNeeded()); // coffeeBeansNeeded instead of coffee_beans
            pstmt.setFloat(5, coffeeType.getPrice());
            pstmt.executeUpdate();
        }
    }

    // READ operation - Get all coffee types
    public List<CoffeeType> getAllCoffeeTypes(Connection connection) {
        List<CoffeeType> coffeeTypes = new ArrayList<>();
        String selectSQL = "SELECT * FROM CoffeeType";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                CoffeeType coffeeType = new CoffeeType(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("waterNeeded"),
                        rs.getInt("milkNeeded"),
                        rs.getInt("coffeeBeansNeeded"),
                        rs.getFloat("price")
                );
                coffeeTypes.add(coffeeType);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching coffee types: " + e.getMessage());
        }
        return coffeeTypes;
    }


    // READ operation - Get coffee type by ID
    public CoffeeType getCoffeeTypeById(int id, Connection connection) {
        String selectSQL = "SELECT * FROM CoffeeType WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new CoffeeType(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("waterNeeded"), // waterNeeded instead of water
                            rs.getInt("milkNeeded"), // milkNeeded instead of milk
                            rs.getInt("coffeeBeansNeeded"), // coffeeBeansNeeded instead of coffee_beans
                            rs.getFloat("price")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching coffee type by ID: " + e.getMessage());
        }
        return null;
    }

    // UPDATE operation - Update an existing coffee type
    public void updateCoffeeType(CoffeeType coffeeType, Connection connection) throws SQLException {
        String updateSQL = "UPDATE CoffeeType SET name = ?, waterNeeded = ?, milkNeeded = ?, coffeeBeansNeeded = ?, price = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, coffeeType.getName());
            pstmt.setInt(2, coffeeType.getWaterNeeded()); // waterNeeded instead of water
            pstmt.setInt(3, coffeeType.getMilkNeeded()); // milkNeeded instead of milk
            pstmt.setInt(4, coffeeType.getCoffeeBeansNeeded()); // coffeeBeansNeeded instead of coffee_beans
            pstmt.setFloat(5, coffeeType.getPrice());
            pstmt.setInt(6, coffeeType.getId());
            pstmt.executeUpdate();
        }
    }

    // DELETE operation - Delete a coffee type by ID
    public void deleteCoffeeType(int id, Connection connection) throws SQLException {
        String deleteSQL = "DELETE FROM CoffeeType WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
