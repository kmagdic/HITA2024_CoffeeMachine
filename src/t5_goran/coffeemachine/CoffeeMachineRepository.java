package t5_goran.coffeemachine;

import java.sql.*;

public class CoffeeMachineRepository {

    private final Connection connection;
    // Constructor to initialize the repository with a database connection
    public CoffeeMachineRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        String createTableSQL = """
        CREATE TABLE IF NOT EXISTS coffee_machine_state (
            id INT PRIMARY KEY,
            water INT NOT NULL,
            milk INT NOT NULL,
            coffee_beans INT NOT NULL,
            cups INT NOT NULL,
            money FLOAT NOT NULL,
            admin_username VARCHAR(50) NOT NULL,
            admin_password VARCHAR(50) NOT NULL
        );
    """;

        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create coffee_machine_state table", e);
        }
    }

        // Load the state from the database
    public CoffeeMachineState loadState() {
        String selectSQL = "SELECT * FROM coffee_machine_state WHERE id = 1";

        try (PreparedStatement statement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                // Return a new CoffeeMachineState object populated with data from the database
                return new CoffeeMachineState(
                        resultSet.getInt("water"),
                        resultSet.getInt("milk"),
                        resultSet.getInt("coffee_beans"),
                        resultSet.getInt("cups"),
                        resultSet.getFloat("money"),
                        resultSet.getString("admin_username"),
                        resultSet.getString("admin_password")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load coffee machine state from database", e);
        }
        return null;
    }

    public void saveState(CoffeeMachineState state) {
        String selectSQL = "SELECT COUNT(*) FROM coffee_machine_state WHERE id = 1";
        String updateSQL = """
        UPDATE coffee_machine_state
        SET water = ?, milk = ?, coffee_beans = ?, cups = ?, money = ?, admin_username = ?, admin_password = ?
        WHERE id = 1;
    """;
        String insertSQL = """
    INSERT INTO coffee_machine_state (id, water, milk, coffee_beans, cups, money, admin_username, admin_password)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?);
    """;


        try (PreparedStatement selectStatement = connection.prepareStatement(selectSQL)) {
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();
            boolean exists = resultSet.getInt(1) > 0; // Check if a state already exists

            if (exists) {
                try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                    updateState(updateStatement, state);     // Update the existing state
                }
            } else {
                try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {
                    insertState(insertStatement, state);    // Insert a new state
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save coffee machine state to database", e);
        }
    }

    private void updateState(PreparedStatement statement, CoffeeMachineState state) throws SQLException {
        statement.setInt(1, state.getWater());
        statement.setInt(2, state.getMilk());
        statement.setInt(3, state.getCoffeeBeans());
        statement.setInt(4, state.getCups());
        statement.setFloat(5, state.getMoney());
        statement.setString(6, state.getAdminUsername());
        statement.setString(7, state.getAdminPassword());
        statement.executeUpdate();  // Execute the update statement
    }

    private void insertState(PreparedStatement statement, CoffeeMachineState state) throws SQLException {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
            statement.setInt(1, 1);
            statement.setInt(2, state.getWater());
            statement.setInt(3, state.getMilk());
            statement.setInt(4, state.getCoffeeBeans());
            statement.setInt(5, state.getCups());
            statement.setFloat(6, state.getMoney());
            statement.setString(7, state.getAdminUsername());
            statement.setString(8, state.getAdminPassword());
            statement.executeUpdate();  // Execute the insert statement
        }

    }