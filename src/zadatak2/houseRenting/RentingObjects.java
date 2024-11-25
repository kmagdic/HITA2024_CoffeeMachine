package zadatak2.houseRenting;

public class RentingObjects {
    private int id;
    private String name;
    private String type;
    private int yearOfProduction;
    private String category;
    private double priceForOneNight;

    // Constructor
    public RentingObjects(int id, String name, String type, int yearOfProduction, String category, double priceForOneNight) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.yearOfProduction = yearOfProduction;
        this.category = category;
        this.priceForOneNight = priceForOneNight;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPriceForOneNight() {
        return priceForOneNight;
    }

    public void setPriceForOneNight(double priceForOneNight) {
        this.priceForOneNight = priceForOneNight;
    }

    // Override toString for better readability
    @Override
    public String toString() {
        return "RentingObject [id=" + id + ", name=" + name + ", type=" + type +
                ", yearOfProduction=" + yearOfProduction + ", category=" + category +
                ", priceForOneNight=" + priceForOneNight + "]";
    }
}
