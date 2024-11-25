package zadatak2.houseRenting;

import java.time.LocalDateTime;
import java.util.Date;

public class Event {

    int id;
    LocalDateTime datetime;
    RentingObjects rentingObjects;
    String event;

    public Event() {
    }

    public Event(int id, LocalDateTime datetime, RentingObjects rentingObjects, String event) {
        this.id = id;
        this.datetime = datetime;
        this.rentingObjects = rentingObjects;
        this.event = event;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RentingObjects getRentingObjects() {
        return rentingObjects;
    }

    public void setRentingObjects(RentingObjects rentingObjects) {
        this.rentingObjects = rentingObjects;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", datetime=" + datetime +
                ", rentingobjects=" + rentingObjects +
                ", event='" + event + '\'' +
                '}';
    }
}
