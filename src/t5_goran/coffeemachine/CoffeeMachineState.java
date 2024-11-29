package t5_goran.coffeemachine;

// Class to represent the state ingredients  of the coffee machine
public class CoffeeMachineState {

    private final int water;
    private final int milk;
    private final int coffeeBeans;
    private final int cups;
    private final float money;
    private final String adminUsername;
    private final String adminPassword;

    public CoffeeMachineState(int water, int milk, int coffeeBeans, int cups, float money, String adminUsername, String adminPassword) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public int getCups() {
        return cups;
    }

    public float getMoney() {
        return money;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    // Method to create a new CoffeeMachineState instance
    private CoffeeMachineState getState() {
        return new CoffeeMachineState(
                getWater(),
                getMilk(),
                getCoffeeBeans(),
                getCups(),
                getMoney(),
                getAdminUsername(),
                getAdminPassword()
        );
    }
}
