package t5_marin.coffeemachine;

import java.sql.*;
import java.util.*;

public class CoffeeTypeDAO {

    public void insertCoffeeType(CoffeeType coffeeType, Connection connection) throws SQLException {
        String insertSQL = "INSERT INTO coffee_type (name, water, milk, coffee_beans, price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, coffeeType.getName());
            pstmt.setInt(2, coffeeType.getWaterNeeded());
            pstmt.setInt(3, coffeeType.getMilkNeeded());
            pstmt.setInt(4, coffeeType.getCoffeeBeansNeeded());
            pstmt.setFloat(5, coffeeType.getPrice());
            pstmt.executeUpdate();
        }
    }

    public List<CoffeeType> getAllCoffeeTypes(Connection connection) {
        List<CoffeeType> coffeeTypes = new ArrayList<>();
        String selectSQL = "SELECT * FROM coffee_type";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                CoffeeType coffeeType = new CoffeeType(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("water"),
                        rs.getInt("milk"),
                        rs.getInt("coffee_beans"),
                        rs.getFloat("price")
                );
                coffeeTypes.add(coffeeType);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching coffee types: " + e.getMessage());
        }

        return coffeeTypes;
    }

    public CoffeeType getCoffeeTypeById(int id, Connection connection) {
        String selectSQL = "SELECT * FROM coffee_type WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new CoffeeType(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("water"),
                            rs.getInt("milk"),
                            rs.getInt("coffee_beans"),
                            rs.getFloat("price")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching coffee type by ID: " + e.getMessage());
        }
        return null;
    }
}
