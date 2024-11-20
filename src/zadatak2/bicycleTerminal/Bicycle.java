package zadatak2.bicycleTerminal;

public class Bicycle {
    private int id;
    private String type;
    private boolean isRented;
    private int servicedTimes;
    private String color;
    private String batteryLevel;
    private int userLevel;

    public Bicycle(int id, String type, String color, String batteryLevel, int userLevel) {
        this.id = id;
        this.type = type;
        this.isRented = false;
        this.servicedTimes = 0;
        this.color = color;
        this.batteryLevel = batteryLevel;
        this.userLevel = userLevel;
    }

    public void userFeedback(int rating) {
        System.out.println("User rating for bicycle " + id + ": " + rating);
    }

    public void rentBicycle(int userId) {
        if (!isRented) {
            isRented = true;
            System.out.println("Bicycle " + id + " rented to user " + userId);
        } else {
            System.out.println("Bicycle " + id + " is already rented.");
        }
    }

    public void returnBicycle(int userId) {
        if (isRented) {
            isRented = false;
            System.out.println("Bicycle " + id + " returned by user " + userId);
        } else {
            System.out.println("Bicycle " + id + " was not rented.");
        }
    }

    public void addBicycleType(String newType) {
        this.type = newType;
        System.out.println("Bicycle " + id + " type set to " + newType);
    }

    public void serviced(int times) {
        this.servicedTimes += times;
        System.out.println("Bicycle " + id + " serviced " + times + " time(s). Total services: " + servicedTimes);
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Bicycle [ID=" + id + ", Type=" + type + ", Rented=" + isRented + ", ServicedTimes=" + servicedTimes +
                ", Color=" + color + ", BatteryLevel=" + batteryLevel + ", UserLevel=" + userLevel + "]";
    }
}
