package t5_goran.coffeemachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeTypeRepository {

    private final Connection connection;

    public CoffeeTypeRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS coffee_type (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                milk_needed INT NOT NULL,
                water_needed INT NOT NULL,
                coffee_beans_needed INT NOT NULL,
                price INT NOT NULL
            );
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create coffee_type table", e);
        }
    }

    public void addCoffeeType(CoffeeType coffeeType) {
        String insertSQL = """
            INSERT INTO coffee_type (name, milk_needed, water_needed, coffee_beans_needed, price)
            VALUES (?, ?, ?, ?, ?);
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, coffeeType.getName());
            pstmt.setInt(2, coffeeType.getMilkNeeded());
            pstmt.setInt(3, coffeeType.getWaterNeeded());
            pstmt.setInt(4, coffeeType.getCoffeeBeansNeeded());
            pstmt.setInt(5, coffeeType.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add coffee type", e);
        }
    }

    public List<CoffeeType> getAllCoffeeTypes() {
        String selectSQL = "SELECT * FROM coffee_type";
        List<CoffeeType> coffeeTypes = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                CoffeeType coffeeType = new CoffeeType(
                        rs.getString("name"),
                        rs.getInt("water_needed"),
                        rs.getInt("milk_needed"),
                        rs.getInt("coffee_beans_needed"),
                        rs.getInt("price")
                );
                coffeeType.setId(rs.getInt("id"));
                coffeeTypes.add(coffeeType);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve coffee types", e);
        }
        return coffeeTypes;
    }
}
