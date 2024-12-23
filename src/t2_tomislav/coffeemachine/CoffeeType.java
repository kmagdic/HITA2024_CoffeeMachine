package t2_tomislav.coffeemachine;

public class CoffeeType {

    private int milkNeeded;
    private int waterNeeded;
    private int coffeeBeansNeeded;
    private int price;
    private String name;

    public CoffeeType(String name, int waterNeeded, int milkNeeded, int coffeeBeansNeeded, int price) {
        this.milkNeeded = milkNeeded;
        this.waterNeeded = waterNeeded;
        this.coffeeBeansNeeded = coffeeBeansNeeded;
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
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

    @Override
    public String toString() {
        return "CoffeeType{" +
                "name='" + name + '\'' +
                '}';
    }
}
