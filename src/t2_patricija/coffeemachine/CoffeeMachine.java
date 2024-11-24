package t2_patricija.coffeemachine;

import java.util.List;
import java.util.ArrayList;

public class CoffeeMachine {

    protected int water;
    protected int milk;
    protected int coffeeBeans;
    protected int cups;
    protected float money;
    protected List <CoffeeType> coffeeTypes = new ArrayList<>();
    protected String statusFileName = "docs/coffee_machine_status.txt";

    protected String adminUsername = "admin";
    protected String adminPassword = "admin12345";


    protected List<TransactionLog> transactionLogList = new ArrayList<>(); //služi mi za spremanje svih logova u listu

    public List<TransactionLog> getHistoryLogList() { //služi mi za dohvaćanje te liste logova
        return transactionLogList;
    }

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        coffeeTypes.add (new CoffeeType("Espresso", 350, 0, 16, 4));
        coffeeTypes.add(new CoffeeType("Latte", 350, 75, 20, 7));
        coffeeTypes.add(new CoffeeType("Capuccino", 200, 100, 12, 6));
    }

    public List<CoffeeType> getCoffeeTypes() {
        return coffeeTypes;
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

    public double getMoney() {
        return money;
    }

    public boolean hasEnoughResources(CoffeeType coffeeType) {
        if (water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1) {
            return true;
        } else
            return false;
    }

    public String buyCoffee(CoffeeType coffeeType) {
        if (hasEnoughResources(coffeeType)) {
            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.cups -= 1;
            this.money += coffeeType.getPrice();

            addRecordToHistoryList("coffee type: " + coffeeType.getName() + ", " + "action: Bought");
            return "I have enough resources, making you " + coffeeType.getName() + "\n";
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);

            addRecordToHistoryList("coffee type: " + coffeeType.getName() + ", " + "action: Not bought, no enough ingredients: " + missing);
            return "Sorry, not enough " + missing + "\n"; // ovo pokaže samo prvi resurs koji nedostaje, ne i oba ili više njih koji nedostaju
        }
    }

    public float takeMoney() {
        float moneyReturn = money;
        money = 0;
        return moneyReturn;
    }

    public String calculateWhichIngredientIsMissing(CoffeeType coffeeType) {
        String ingredientMissing = null;
        if (water < coffeeType.getWaterNeeded()) {
            ingredientMissing = "water";
        } else if (milk < coffeeType.getMilkNeeded()) {
            ingredientMissing = "milk";
        } else if (coffeeBeans < coffeeType.getCoffeeBeansNeeded()) {
            ingredientMissing = "coffee beans";
        } else if (cups < 1) {
            ingredientMissing = "cups";
        }
        return ingredientMissing;
    }

    public void fill(int water, int milk, int coffeeBeans, int cups) {
        this.water += water;
        this.milk += milk;
        this.coffeeBeans += coffeeBeans;
        this.cups += cups;
    }

    public boolean login(String username, String password) {
        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            return true;
        } else
            return false;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void addRecordToHistoryList(String res) {
        TransactionLog transactionLog = new TransactionLog(res);
        transactionLogList.add(transactionLog);
    }

    public void stop() {
    }

    public boolean start() {
        return true;
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