package t5_marin.coffeemachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO {
    private final Connection connection;

    public LocationDAO(Connection connection) {
        this.connection = connection;
    }

    // Create a new Location
    public void createLocation(Location location) throws SQLException {
        String sql = "INSERT INTO Location (drzava, zupanija, postanskiBroj, adresa) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, location.getDrzava());
            stmt.setString(2, location.getZupanija());
            stmt.setString(3, location.getPostanskiBroj()); // Change to setString()
            stmt.setString(4, location.getAdresa());
            stmt.executeUpdate();

            // Retrieve and set the generated ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    location.setId(rs.getInt(1));
                }
            }
        }
    }

    // Read a Location by ID
    public Location readLocationById(int id) throws SQLException {
        String sql = "SELECT * FROM Location WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Location(
                            rs.getInt("id"),
                            rs.getString("drzava"),
                            rs.getString("zupanija"),
                            rs.getString("postanskiBroj"), // Change to getString()
                            rs.getString("adresa")
                    );
                }
            }
        }
        return null;
    }

    // Read all Locations
    public List<Location> readAllLocations() throws SQLException {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM Location";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                locations.add(new Location(
                        rs.getInt("id"),
                        rs.getString("drzava"),
                        rs.getString("zupanija"),
                        rs.getString("postanskiBroj"), // Change to getString()
                        rs.getString("adresa")
                ));
            }
        }
        return locations;
    }

    // Update an existing Location
    public void updateLocation(Location location) throws SQLException {
        String sql = "UPDATE Location SET drzava = ?, zupanija = ?, postanskiBroj = ?, adresa = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, location.getDrzava());
            stmt.setString(2, location.getZupanija());
            stmt.setString(3, location.getPostanskiBroj()); // Change to setString()
            stmt.setString(4, location.getAdresa());
            stmt.setInt(5, location.getId());
            stmt.executeUpdate();
        }
    }

    // Delete a Location by ID
    public void deleteLocation(int id) throws SQLException {
        String sql = "DELETE FROM Location WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
