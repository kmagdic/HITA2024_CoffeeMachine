package zadatak2.houseRenting;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HouseRentingRepository {
    private Connection connection;

    // Constructor
    public HouseRentingRepository(Connection connection) {
        this.connection = connection;
    }

    // Create table if it doesn't exist
    public void createTable() {
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS renting_objects (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100)," +
                    "type VARCHAR(50)," +
                    "yearOfProduction INT," +
                    "category VARCHAR(50)," +
                    "priceForOneNight DOUBLE)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert renting object into DB
    public void insertHouse(RentingObjects rentingObject) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO renting_objects (id, name, type, yearOfProduction, category, priceForOneNight) VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setInt(1, rentingObject.getId());
            stmt.setString(2, rentingObject.getName());
            stmt.setString(3, rentingObject.getType());
            stmt.setInt(4, rentingObject.getYearOfProduction());
            stmt.setString(5, rentingObject.getCategory());
            stmt.setDouble(6, rentingObject.getPriceForOneNight());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all renting objects from DB
    public List<RentingObjects> getHouses() {
        List<RentingObjects> rentingObjects = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM renting_objects")) {
            while (rs.next()) {
                rentingObjects.add(new RentingObjects(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getInt("yearOfProduction"),
                        rs.getString("category"),
                        rs.getDouble("priceForOneNight")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentingObjects;
    }
}
