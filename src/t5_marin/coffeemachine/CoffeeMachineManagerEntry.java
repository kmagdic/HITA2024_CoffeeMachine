package t5_marin.coffeemachine;

import java.sql.Timestamp;

public class CoffeeMachineManagerEntry {
    private int id;
    private CoffeeMachine coffeeMachine;
    private Location location;
    private Timestamp timeOfInstalment;

    // Constructor
    public CoffeeMachineManagerEntry(int id, CoffeeMachine coffeeMachine, Location location, Timestamp timeOfInstalment) {
        this.id = id;
        this.coffeeMachine = coffeeMachine;
        this.location = location;
        this.timeOfInstalment = timeOfInstalment;
    }

    // Getters
    public int getId() {
        return id;
    }

    public CoffeeMachine getCoffeeMachine() {
        return coffeeMachine;
    }

    public Location getLocation() {
        return location;
    }

    public Timestamp getTimeOfInstalment() {
        return timeOfInstalment;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCoffeeMachine(CoffeeMachine coffeeMachine) {
        this.coffeeMachine = coffeeMachine;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setTimeOfInstalment(Timestamp timeOfInstalment) {
        this.timeOfInstalment = timeOfInstalment;
    }
}
