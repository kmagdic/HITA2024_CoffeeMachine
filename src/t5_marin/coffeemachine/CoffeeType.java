package t5_marin.coffeemachine;

public class CoffeeType {

    private int id;
    private String name;
    private int waterNeeded;      // Water needed for the coffee (in milliliters)
    private int milkNeeded;       // Milk needed for the coffee (in milliliters)
    private int coffeeBeansNeeded; // Coffee beans needed for the coffee (in grams)
    private float price;          // Price of the coffee

    // Constructor
    public CoffeeType(int id, String name, int waterNeeded, int milkNeeded, int coffeeBeansNeeded, float price) {
        this.id = id;
        this.name = name;
        this.waterNeeded = waterNeeded;
        this.milkNeeded = milkNeeded;
        this.coffeeBeansNeeded = coffeeBeansNeeded;
        this.price = price;
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

    public int getWaterNeeded() {
        return waterNeeded;
    }

    public void setWaterNeeded(int waterNeeded) {
        this.waterNeeded = waterNeeded;
    }

    public int getMilkNeeded() {
        return milkNeeded;
    }

    public void setMilkNeeded(int milkNeeded) {
        this.milkNeeded = milkNeeded;
    }

    public int getCoffeeBeansNeeded() {
        return coffeeBeansNeeded;
    }

    public void setCoffeeBeansNeeded(int coffeeBeansNeeded) {
        this.coffeeBeansNeeded = coffeeBeansNeeded;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
