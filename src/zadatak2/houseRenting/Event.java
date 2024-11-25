package zadatak2.houseRenting;

import java.time.LocalDateTime;

public class Event {
    private LocalDateTime timestamp;
    private RentingObjects rentingObject;
    private String description;

    // Constructor
    public Event(LocalDateTime timestamp, RentingObjects rentingObject, String description) {
        this.timestamp = timestamp;
        this.rentingObject = rentingObject;
        this.description = description;
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public RentingObjects getRentingObject() {
        return rentingObject;
    }

    public void setRentingObject(RentingObjects rentingObject) {
        this.rentingObject = rentingObject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Override toString for better readability
    @Override
    public String toString() {
        return "Event [timestamp=" + timestamp + ", rentingObject=" + rentingObject.getName() +
                ", description=" + description + "]";
    }
}
