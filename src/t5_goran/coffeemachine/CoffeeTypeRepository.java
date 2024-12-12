package t5_goran.coffeemachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeTypeRepository {

    private final Connection connection;      // Database connection instance

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

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create coffee_type table", e);
        }
    }

    public void addCoffeeType(CoffeeType coffeeType) {
        String insertSQL = """
            INSERT INTO coffee_type (name, milk_needed, water_needed, coffee_beans_needed, price)
            VALUES (?, ?, ?, ?, ?);
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, coffeeType.getName());
            preparedStatement.setInt(2, coffeeType.getMilkNeeded());
            preparedStatement.setInt(3, coffeeType.getWaterNeeded());
            preparedStatement.setInt(4, coffeeType.getCoffeeBeansNeeded());
            preparedStatement.setInt(5, coffeeType.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add coffee type", e);
        }
    }

    public List<CoffeeType> getAllCoffeeTypes() {
        String selectSQL = "SELECT * FROM coffee_type";
        List<CoffeeType> coffeeTypes = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                CoffeeType coffeeType = new CoffeeType(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("water_needed"),
                        resultSet.getInt("milk_needed"),
                        resultSet.getInt("coffee_beans_needed"),
                        resultSet.getInt("price")
                );
                coffeeType.setId(resultSet.getInt("id"));
                coffeeTypes.add(coffeeType);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve coffee types", e);
        }
        return coffeeTypes;
    }
}
