package t5_goran.coffeemachine;

public class CoffeeType {

    private int id;
    private String name;
    private int milkNeeded;
    private int waterNeeded;
    private int coffeeBeansNeeded;
    private int price;

    public CoffeeType(String name, int waterNeeded, int milkNeeded, int coffeeBeansNeeded, int price) {
        this.name = name;
        this.waterNeeded = waterNeeded;
        this.milkNeeded = milkNeeded;
        this.coffeeBeansNeeded = coffeeBeansNeeded;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getMilkNeeded() {
        return milkNeeded;
    }

    public int getWaterNeeded() {
        return waterNeeded;
    }

    public int getCoffeeBeansNeeded() {
        return coffeeBeansNeeded;
    }

    public int getPrice() {
        return price;
    }
}
