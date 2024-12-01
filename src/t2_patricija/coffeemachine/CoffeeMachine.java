package t2_patricija.coffeemachine;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.h2.jdbcx.JdbcDataSource;

import java.util.List;
import java.util.ArrayList;

public class CoffeeMachine {

    // Before creating the CoffeeMachineWithStatusInFile class, these variables were private.
    // After extending the CoffeeMachine class, we changed the variables to 'protected'
    // so they can be accessed within the CoffeeMachineWithStatusInFile subclass.

    protected int water;
    protected int milk;
    protected int coffeeBeans;
    protected int cups;
    protected float money;
    protected List<CoffeeType> coffeeTypes = new ArrayList<>();
    TransactionLog transactionLog;
    protected String statusFileName = "docs/coffee_machine_status.txt";

    protected String adminUsername = "admin";
    protected String adminPassword = "admin12345";

    private TransactionLog log;
    DbClient dbClient = DbClient.getInstance();

    // Getting the data source
    JdbcDataSource dataSource = dbClient.getDataSource();
    private CoffeeTypeDAO coffeeTypeDao = new CoffeeTypeDAO(dataSource);
    // Now you can create your DAOs and pass the data source
    TransactionLogDAO transactionLogDao = new TransactionLogDAO(dataSource, coffeeTypeDao);



    // Explenation for dbClient part in the constructor:
    // If the database object is not provided (null), we initialize it using the singleton instance of dbClient.
    // This ensures that the database connection is properly set up, using the default file path if no other is specified.

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        // Inicijalizirajte listu coffeeTypes
        this.coffeeTypes = new ArrayList<>();

        // Check if coffee types exist and add them if not
        loadCoffeeTypes(coffeeTypeDao);
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

            LocalDateTime dateTime = LocalDateTime.now();

            log = new TransactionLog(dateTime, coffeeType, "Bought", null);
            transactionLogDao.add(log);
            return "I have enough resources, making you " + coffeeType.getName() + "\n";
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);
            LocalDateTime dateTime = LocalDateTime.now();

            log = new TransactionLog(dateTime, coffeeType, "Bought", missing);

            transactionLogDao.add(log);
            return "Sorry, not enough " + missing + "\n";
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

    public void stop() {
    }

    public boolean start() {
        return true;
    }

    private void loadCoffeeTypes(CoffeeTypeDAO coffeeTypeDAO) {
        // Initialize coffee types if there aren't any
        List<CoffeeType> storedCoffeeTypes = coffeeTypeDAO.findAll();

        if (storedCoffeeTypes.isEmpty()) {
            // Add coffee types if there aren't any
            CoffeeType espresso = new CoffeeType("Espresso", 350, 0, 16, 4);
            CoffeeType latte = new CoffeeType("Latte", 350, 75, 20, 7);
            CoffeeType cappuccino = new CoffeeType("Capuccino", 200, 100, 12, 6);

            coffeeTypeDAO.add(espresso);
            coffeeTypeDAO.add(latte);
            coffeeTypeDAO.add(cappuccino);
        } else {
            // If there are coffee types, just load them
            coffeeTypes.addAll(storedCoffeeTypes);
        }
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