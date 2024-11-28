package _karlo_dragan.studentmanager;

import java.time.LocalDateTime;
import java.util.Date;

public class Event {
    int id;
    LocalDateTime datetime;
    Student student;
    String event;

    public Event(LocalDateTime datetime, Student student, String event) {
        this.datetime = datetime;
        this.student = student;
        this.event = event;
    }

    public Event() {

    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", datetime=" + datetime +
                ", student=" + student +
                ", event='" + event + '\'' +
                '}';
    }
}
