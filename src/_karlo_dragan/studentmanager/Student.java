package _karlo_dragan.studentmanager;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String firstName;
    private String lastName;
    private String oib;
    private int currentPoints;
    private String username;
    private String password;

    public Student() {
    }

    public Student(String firstName, String lastName, String oib) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.setCurrentPoints(0);
    }

    public Student(String firstName, String lastName, String oib, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.oib = oib;
        this.username = username;
        this.password = password;
        this.setCurrentPoints(0);
    }
    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getOib() {
        return oib;
    }


    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", oib='" + oib + '\'' +
                ", currentPoints=" + currentPoints +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +

                '}';
    }
}
