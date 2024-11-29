package t2_tomislav.coffeemachine;


import java.util.ArrayList;
import java.util.List;

public class CoffeeMachine {

    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private float money;

    private final CoffeeType[] coffeeTypes = new CoffeeType[3];
    private String adminUsername = "admin";
    private String adminPassword = "admin12345";

    private final H2DatabaseManager databaseManager = new H2DatabaseManager("C:/Tomislav/coffee_machine");
    private final List<TransactionLog> transactionLogList = new ArrayList<>();

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        coffeeTypes[0] = new CoffeeType("Espresso", 250, 0, 16, 4);
        coffeeTypes[1] = new CoffeeType("Latte", 350, 75, 20, 7);
        coffeeTypes[2] = new CoffeeType("Cappuccino", 200, 100, 12, 6);
    }

    // Getter i Setter metode za pristup privatnim atributima
    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public void setCoffeeBeans(int coffeeBeans) {
        this.coffeeBeans = coffeeBeans;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public CoffeeType[] getCoffeeTypes() {
        return coffeeTypes;
    }

    public List<TransactionLog> getHistoryLogList() {
        return transactionLogList;
    }

    // Prijava admin korisnika
    public boolean login(String username, String password) {
        return adminUsername.equals(username) && adminPassword.equals(password);
    }

    // Promjena lozinke
    public boolean changeAdminPassword(String newPassword) {
        if (newPassword.length() >= 7 && newPassword.matches(".*\\d.*")) {
            adminPassword = newPassword;
            return true;
        } else {
            return false;
        }
    }

    // Kupovina kave
    public String buyCoffee(CoffeeType coffeeType) {
        boolean success = hasEnoughResources(coffeeType);
        float amount = success ? coffeeType.getPrice() : 0;
        String ingredient = success ? "" : calculateWhichIngredientIsMissing(coffeeType);

        if (success) {
            water -= coffeeType.getWaterNeeded();
            milk -= coffeeType.getMilkNeeded();
            coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            cups--;
            money += coffeeType.getPrice();
        }

        addRecordToHistoryList(coffeeType.getName(), success ? "Bought" : "Failed", ingredient);
        databaseManager.insertTransactionLog(new TransactionLog(coffeeType.getName(), success ? "Bought" : "Failed", ingredient));


        return success ? "I have enough resources, making you " + coffeeType.getName()
                : "Sorry, not enough " + ingredient;
    }

    // Dodavanje zapisa u lokalnu povijest
    public void addRecordToHistoryList(String coffeeType, String success, String ingredient) {
        TransactionLog log = new TransactionLog(coffeeType, success, ingredient);
        transactionLogList.add(log);
    }

    // Provjera resursa
    public boolean hasEnoughResources(CoffeeType coffeeType) {
        return water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1;
    }

    // Izračunavanje koji sastojak nedostaje
    public String calculateWhichIngredientIsMissing(CoffeeType coffeeType) {
        if (water < coffeeType.getWaterNeeded()) return "water";
        if (milk < coffeeType.getMilkNeeded()) return "milk";
        if (coffeeBeans < coffeeType.getCoffeeBeansNeeded()) return "coffee beans";
        if (cups < 1) return "cups";
        return null;
    }

    // Punjenje resursa
    public void fill(int water, int milk, int coffeeBeans, int cups) {
        this.water += water;
        this.milk += milk;
        this.coffeeBeans += coffeeBeans;
        this.cups += cups;
    }

    // Uzimanje novca iz aparata
    public float takeMoney() {
        float moneyReturn = money;
        money = 0;
        return moneyReturn;
    }

    @Override
    public String toString() {
        return "CoffeeMachine{" +
                "water=" + water +
                ", milk=" + milk +
                ", coffeeBeans=" + coffeeBeans +
                ", cups=" + cups +
                ", money=" + money +
                '}';
    }
}
