package t5_marin.coffeemachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeMachineDAO {
    private final Connection connection;

    public CoffeeMachineDAO(Connection connection) {
        this.connection = connection;
    }

    // Create a new CoffeeMachine
    public void createCoffeeMachine(CoffeeMachine coffeeMachine) throws SQLException {
        String sql = "INSERT INTO CoffeeMachine (water, milk, coffeeBeans, cups, money, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, coffeeMachine.getWater());
            stmt.setInt(2, coffeeMachine.getMilk());
            stmt.setInt(3, coffeeMachine.getCoffeeBeans());
            stmt.setInt(4, coffeeMachine.getCups());
            stmt.setFloat(5, coffeeMachine.getMoney());
            stmt.setString(6, "admin"); // Default password
            stmt.executeUpdate();

            // Retrieve and set the generated ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    coffeeMachine.setId(rs.getInt(1));
                }
            }
        }
    }

    // Read a CoffeeMachine by ID
    public CoffeeMachine readCoffeeMachineById(int id) throws SQLException {
        String sql = "SELECT * FROM CoffeeMachine WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CoffeeMachine coffeeMachine = new CoffeeMachine(
                            rs.getInt("id"),
                            rs.getInt("water"),
                            rs.getInt("milk"),
                            rs.getInt("coffeeBeans"),
                            rs.getInt("cups"),
                            rs.getFloat("money")
                    );

                    // Optional: Load associated coffee types
                    loadCoffeeTypes(coffeeMachine);

                    return coffeeMachine;
                }
            }
        }
        return null;
    }

    // Read all CoffeeMachines
    public List<CoffeeMachine> readAllCoffeeMachines() throws SQLException {
        List<CoffeeMachine> coffeeMachines = new ArrayList<>();
        String sql = "SELECT * FROM CoffeeMachine";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CoffeeMachine coffeeMachine = new CoffeeMachine(
                        rs.getInt("id"),
                        rs.getInt("water"),
                        rs.getInt("milk"),
                        rs.getInt("coffeeBeans"),
                        rs.getInt("cups"),
                        rs.getFloat("money")
                );

                // Optional: Load associated coffee types
                loadCoffeeTypes(coffeeMachine);

                coffeeMachines.add(coffeeMachine);
            }
        }
        return coffeeMachines;
    }

    // Update an existing CoffeeMachine
    public void updateCoffeeMachine(CoffeeMachine coffeeMachine) throws SQLException {
        String sql = "UPDATE CoffeeMachine SET water = ?, milk = ?, coffeeBeans = ?, cups = ?, money = ?, password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, coffeeMachine.getWater());
            stmt.setInt(2, coffeeMachine.getMilk());
            stmt.setInt(3, coffeeMachine.getCoffeeBeans());
            stmt.setInt(4, coffeeMachine.getCups());
            stmt.setFloat(5, coffeeMachine.getMoney());
            stmt.setString(6, coffeeMachine.getPassword());
            stmt.setInt(7, coffeeMachine.getId());
            stmt.executeUpdate();
        }
    }

    // Delete a CoffeeMachine by ID
    public void deleteCoffeeMachine(int id) throws SQLException {
        String sql = "DELETE FROM CoffeeMachine WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Helper method: Load associated CoffeeTypes for a CoffeeMachine
    private void loadCoffeeTypes(CoffeeMachine coffeeMachine) throws SQLException {
        String sql = "SELECT CoffeeType.* FROM CoffeeType " +
                "JOIN CoffeeMachineCoffeeType ON CoffeeType.id = CoffeeMachineCoffeeType.coffeeTypeId " +
                "WHERE CoffeeMachineCoffeeType.coffeeMachineId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, coffeeMachine.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CoffeeType coffeeType = new CoffeeType(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("waterNeeded"),
                            rs.getInt("milkNeeded"),
                            rs.getInt("coffeeBeansNeeded"),
                            rs.getFloat("price")
                    );
                    //coffeeMachine.addCoffeeType(coffeeType);
                }
            }
        }
    }
}
