package t1_mateo.coffeemachine;

public class CoffeeType {

    private int id;
    private int milkNeeded;
    private int waterNeeded;
    private int coffeeBeansNeeded;
    private double price;
    private String name;

    public CoffeeType(int id, String name, int waterNeeded, int milkNeeded, int coffeeBeansNeeded, double price) {
        this.id = id;
        this.name = name;
        this.milkNeeded = milkNeeded;
        this.waterNeeded = waterNeeded;
        this.coffeeBeansNeeded = coffeeBeansNeeded;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMilkNeeded() {
        return milkNeeded;
    }

    public void setMilkNeeded(int milkNeeded) {
        this.milkNeeded = milkNeeded;
    }

    public int getWaterNeeded() {
        return waterNeeded;
    }

    public void setWaterNeeded(int waterNeeded) {
        this.waterNeeded = waterNeeded;
    }

    public int getCoffeeBeansNeeded() {
        return coffeeBeansNeeded;
    }

    public void setCoffeeBeansNeeded(int coffeeBeansNeeded) {
        this.coffeeBeansNeeded = coffeeBeansNeeded;
    }

    public int getId() {
        return id;
    }
}










