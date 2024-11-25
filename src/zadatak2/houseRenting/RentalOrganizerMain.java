package zadatak2.houseRenting;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class RentalOrganizerMain {

    public static void main(String[] args) {
        // Create renting objects
        RentingObjects rentingObject1 = new RentingObjects(1, "Beachside Villa", "House", 2015, "Luxury", 2500);
        RentingObjects rentingObject2 = new RentingObjects(2, "Mountain Lodge", "House", 2018, "Standard", 3500);

        System.out.println(rentingObject1);
        System.out.println(rentingObject2);

        // Db setup
        Connection connection = makeDBConnection("docs/houseRentingDB.mv.db");
        HouseRentingRepository houseRentingRepository = new HouseRentingRepository(connection);
        EventRepository eventRepository = new EventRepository(connection);

        // Create tables if they don't exist
        houseRentingRepository.createTable();
        eventRepository.createTable();

        // Insert renting objects into DB
        houseRentingRepository.insertHouse(rentingObject1);
        houseRentingRepository.insertHouse(rentingObject2);

        // Insert events related to renting objects into DB
        Event event1 = new Event(LocalDateTime.now(), rentingObject1, "Renting object rented to John Doe");
        Event event2 = new Event(LocalDateTime.now().plusDays(1), rentingObject2, "Inspection of renting object scheduled");
        eventRepository.insertEvent(event1);
        eventRepository.insertEvent(event2);

        // Retrieve and print all renting objects and rental events
        List<RentingObjects> rentingObjects = houseRentingRepository.getHouses();
        System.out.println("Renting objects available: " + rentingObjects);

        List<Event> events = eventRepository.getEvents();
        System.out.println("Rental events: " + events);

        System.out.println("Rental data saved successfully!");
    }

    public static Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
