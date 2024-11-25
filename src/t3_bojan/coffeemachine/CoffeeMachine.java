package t3_bojan.coffeemachine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CoffeeMachine {

    protected int water;
    protected int milk;
    protected int coffeeBeans;
    protected int cups;
    protected float money;
    protected String adminUsername = "admin";
    protected String adminPassword = "admin12345";
    protected String statusFileName = "src/t3_bojan/coffeemachine/coffee_machine_status.txt";
    private List<CoffeeType> coffeeTypes = new ArrayList<>();

    private final String TRANSACTION_SUCCESS_ACTION = "Bought";
    private final String TRANSACTION_FAIL_ACTION = "Not Bought";
    private final String I_HAVE_ENOUGH_RESOURCES = "I have enough resources, making you ";
    private final String I_DONT_HAVE_ENOUGH_RESOURCES = "Sorry, not enough ";

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

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

            DBManager.getInstance().insertTransactionLogToDB(LocalDateTime.now(), coffeeType.getName(), TRANSACTION_SUCCESS_ACTION, null);

            return I_HAVE_ENOUGH_RESOURCES + coffeeType.getName() + "\n";
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);

            DBManager.getInstance().insertTransactionLogToDB(LocalDateTime.now(), coffeeType.getName(), TRANSACTION_FAIL_ACTION, missing);

            return I_DONT_HAVE_ENOUGH_RESOURCES + missing + "\n";
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


    public void changePassword(String newPassword) {
        adminPassword = newPassword;
    }


    public boolean start() {
        return true;
    }


    public void stop() {
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