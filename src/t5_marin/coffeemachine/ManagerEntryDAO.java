package t5_marin.coffeemachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerEntryDAO {
    private Connection connection;

    public ManagerEntryDAO(Connection connection) {
        this.connection = connection;
    }

    // Create a new ManagerEntry record
    public void createManagerEntry(CoffeeMachineManagerEntry entry) throws SQLException {
        String sql = "INSERT INTO CoffeeMachineManagerEntry (coffeeMachineId, locationId, timeOfInstalment) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entry.getCoffeeMachine().getId());
            stmt.setInt(2, entry.getLocation().getId());
            stmt.setTimestamp(3, entry.getTimeOfInstalment());
            stmt.executeUpdate();
        }
    }

    // Read a ManagerEntry by ID
    public CoffeeMachineManagerEntry readManagerEntryById(int id) throws SQLException {
        String sql = "SELECT * FROM CoffeeMachineManagerEntry WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CoffeeMachine coffeeMachine = getCoffeeMachineById(rs.getInt("coffeeMachineId"));
                    Location location = getLocationById(rs.getInt("locationId"));
                    Timestamp timeOfInstalment = rs.getTimestamp("timeOfInstalment");
                    return new CoffeeMachineManagerEntry(id, coffeeMachine, location, timeOfInstalment);
                }
            }
        }
        return null;
    }

    // Read all ManagerEntries
    public List<CoffeeMachineManagerEntry> readAllManagerEntries() throws SQLException {
        List<CoffeeMachineManagerEntry> entries = new ArrayList<>();
        String sql = "SELECT * FROM CoffeeMachineManagerEntry";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CoffeeMachine coffeeMachine = getCoffeeMachineById(rs.getInt("coffeeMachineId"));
                Location location = getLocationById(rs.getInt("locationId"));
                Timestamp timeOfInstalment = rs.getTimestamp("timeOfInstalment");
                CoffeeMachineManagerEntry entry = new CoffeeMachineManagerEntry(rs.getInt("id"), coffeeMachine, location, timeOfInstalment);
                entries.add(entry);
            }
        }
        return entries;
    }

    // Update an existing ManagerEntry
    public void updateManagerEntry(CoffeeMachineManagerEntry entry) throws SQLException {
        String sql = "UPDATE CoffeeMachineManagerEntry SET coffeeMachineId = ?, locationId = ?, timeOfInstalment = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entry.getCoffeeMachine().getId());
            stmt.setInt(2, entry.getLocation().getId());
            stmt.setTimestamp(3, entry.getTimeOfInstalment());
            stmt.setInt(4, entry.getId());
            stmt.executeUpdate();
        }
    }

    // Delete a ManagerEntry by ID
    public void deleteManagerEntry(int id) throws SQLException {
        String sql = "DELETE FROM CoffeeMachineManagerEntry WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Helper method to fetch CoffeeMachine by ID
    private CoffeeMachine getCoffeeMachineById(int id) throws SQLException {
        CoffeeMachineDAO coffeeMachineDAO = new CoffeeMachineDAO(connection);
        return coffeeMachineDAO.readCoffeeMachineById(id);
    }

    // Helper method to fetch Location by ID
    private Location getLocationById(int id) throws SQLException {
        LocationDAO locationDAO = new LocationDAO(connection);
        return locationDAO.readLocationById(id);
    }
}
