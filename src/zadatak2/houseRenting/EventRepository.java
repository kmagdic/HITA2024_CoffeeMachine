package zadatak2.houseRenting;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    private Connection connection;

    // Constructor
    public EventRepository(Connection connection) {
        this.connection = connection;
    }

    // Create table if it doesn't exist
    public void createTable() {
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS rental_events (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "timestamp TIMESTAMP," +
                    "rentingObject_id INT," +
                    "description VARCHAR(255)," +
                    "FOREIGN KEY (rentingObject_id) REFERENCES renting_objects(id))";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert event into DB
    public void insertEvent(Event event) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO rental_events (timestamp, rentingObject_id, description) VALUES (?, ?, ?)")) {
            stmt.setTimestamp(1, Timestamp.valueOf(event.getTimestamp()));
            stmt.setInt(2, event.getRentingObject().getId());
            stmt.setString(3, event.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all events from DB
    public List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rental_events")) {
            while (rs.next()) {
                int rentingObjectId = rs.getInt("rentingObject_id");
                String description = rs.getString("description");
                Timestamp timestamp = rs.getTimestamp("timestamp");

                // Retrieve rentingObject from DB (you would need a separate query or repository for renting objects)
                RentingObjects rentingObject = new RentingObjects(rentingObjectId, "", "", 0, "", 0); // Simplified for now
                Event event = new Event(timestamp.toLocalDateTime(), rentingObject, description);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}
