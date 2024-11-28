package t5_marin.coffeemachine;

public class CoffeeType {
    private final int id; // Add an ID field
    private final String name;
    private final int waterNeeded;
    private final int milkNeeded;
    private final int coffeeBeansNeeded;
    private final float price;

    public CoffeeType(int id, String name, int waterNeeded, int milkNeeded, int coffeeBeansNeeded, float price) {
        this.id = id;
        this.name = name;
        this.waterNeeded = waterNeeded;
        this.milkNeeded = milkNeeded;
        this.coffeeBeansNeeded = coffeeBeansNeeded;
        this.price = price;
    }

    public int getId() {
        return id; // Getter for ID
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

    @Override
    public String toString() {
        return "CoffeeType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", waterNeeded=" + waterNeeded +
                ", milkNeeded=" + milkNeeded +
                ", coffeeBeansNeeded=" + coffeeBeansNeeded +
                ", price=" + price +
                '}';
    }
}
