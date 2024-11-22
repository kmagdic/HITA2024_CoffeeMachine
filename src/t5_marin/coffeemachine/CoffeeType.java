package t5_marin.coffeemachine;

public class CoffeeType {
    private final String name;
    private final int waterNeeded;
    private final int milkNeeded;
    private final int coffeeBeansNeeded;
    private final float price;

    public CoffeeType(String name, int waterNeeded, int milkNeeded, int coffeeBeansNeeded, float price) {
        this.name = name;
        this.waterNeeded = waterNeeded;
        this.milkNeeded = milkNeeded;
        this.coffeeBeansNeeded = coffeeBeansNeeded;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getWaterNeeded() {
        return waterNeeded;
    }

    public int getMilkNeeded() {
        return milkNeeded;
    }

    public int getCoffeeBeansNeeded() {
        return coffeeBeansNeeded;
    }

    public float getPrice() {
        return price;
    }
}
