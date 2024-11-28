package t1_darko.coffeemachine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CoffeeMachine {

    protected int water;
    protected int milk;
    protected int coffeeBeans;
    protected int cups;
    protected float money;
    private List<CoffeeType> coffeeTypes = new ArrayList<>();

    protected String adminUsername = "admin";
    protected String adminPassword = "admin12345";

    // transaction log
    private List<Transaction> transactionLog = new ArrayList<>();

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        // List.of
        coffeeTypes.add(new CoffeeType("Espresso", 350, 0,16,4));
        coffeeTypes.add(new CoffeeType("Latte",350, 75,20,7));
        coffeeTypes.add(new CoffeeType("Cappuccino",200, 100,12,6));
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

    public List<Transaction> getTransactionLog() {
        return transactionLog;
    }

    public boolean hasEnoughResources(CoffeeType coffeeType){
        if (water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1) {
            return true;
        } else
            return false;
    }

    public String buyCoffee(CoffeeType coffeeType){
        if (hasEnoughResources(coffeeType)) {
            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.cups -= 1;
            this.money += coffeeType.getPrice();
            Transaction buyTransaction = new Transaction(LocalDateTime.now(), coffeeType, "Bought");
            transactionLog.add(buyTransaction);
            return "I have enough resources, making you " + coffeeType.getName() + "\n";
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);
            Transaction notBuyTransaction = new Transaction(LocalDateTime.now(), coffeeType, "Not Bought", missing);
            transactionLog.add(notBuyTransaction);
            return "Sorry, not enough " + missing + "\n";
        }
    }

    public float takeMoney(){
        float moneyReturn = money;
        money = 0;
        return moneyReturn;
    }

    public String calculateWhichIngredientIsMissing(CoffeeType coffeeType){
        String ingredientMissing = null;
        if (water < coffeeType.getWaterNeeded()) {
            ingredientMissing = "water";
        }
        else if (milk < coffeeType.getMilkNeeded()) {
            ingredientMissing = "milk" ;
        }
        else if (coffeeBeans < coffeeType.getCoffeeBeansNeeded()) {
            ingredientMissing = "coffee beans" ;
        }
        else if (cups < 1) {
            ingredientMissing = "cups" ;
        }
        return ingredientMissing;
    }

    public void fill(int water, int milk, int coffeeBeans, int cups){
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

    // password validation
    public boolean validateNewAdminPassword (String password) {
        if (password.length() > 7) {
            for(char c : password.toCharArray()) {
                if (Character.isDigit(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String changePassword(String password) {
        this.adminPassword = password;
        return "Password is changed";
    }

    public boolean start() {
        return false;
    }

    public void stop() {
    }

    @Override
    public String toString() {
        return "stage2.CoffeeMachine{" +
                "water=" + water +
                ", milk=" + milk +
                ", coffeeBeans=" + coffeeBeans +
                ", cups=" + cups +
                ", money=" + money +
                '}';
    }

}