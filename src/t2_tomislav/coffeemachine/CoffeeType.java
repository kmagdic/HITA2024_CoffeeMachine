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

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
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
}









