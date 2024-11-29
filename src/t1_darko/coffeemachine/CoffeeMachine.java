package t1_darko.coffeemachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CoffeeMachine {

    protected int water;
    protected int milk;
    protected int coffeeBeans;
    protected int cups;
    protected int money;
    private List<CoffeeType> coffeeTypes = new ArrayList<>();

    protected String adminUsername = "admin";
    protected String adminPassword = "admin12345";
    private final String URL = "jdbc:h2:./docs/test_darko";

    private Connection connection;
    private CoffeeTypeRepository coffeeTypeRepository;
    private TransactionRepository transactionRepository;
    private CoffeeMachineRepository coffeeMachineRepository;

    public CoffeeMachine() {
        // setup database
        makeConnection();
        initializeRepositories();
        createTables();
        checkResetPopulateCoffeeTypesWithBasicTypes();

        coffeeMachineRepository.getStatus(this);
        coffeeTypes = coffeeTypeRepository.getAllCoffeeTypes();
    }

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
    }

    private void makeConnection() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeRepositories() {
        coffeeTypeRepository = new CoffeeTypeRepository(connection);
        transactionRepository = new TransactionRepository(connection);
        coffeeMachineRepository = new CoffeeMachineRepository(connection);
    }

    private void createTables() {
        coffeeTypeRepository.createTable();
        transactionRepository.createTable();
        coffeeMachineRepository.createTable();
    }

    private void checkResetPopulateCoffeeTypesWithBasicTypes() {
        // if coffee machine less than 3 populate with 3 basic types
        if(coffeeTypeRepository.getAllCoffeeTypes().size() < 3) {
            transactionRepository.dropTable();
            coffeeTypeRepository.dropTable();
            coffeeTypeRepository.createTable();
            transactionRepository.createTable();

            coffeeTypes.add(new CoffeeType("Espresso", 250, 0,16,4));
            coffeeTypes.add(new CoffeeType("Latte",350, 75,20,7));
            coffeeTypes.add(new CoffeeType("Cappuccino",200, 100,12,6));

            for (CoffeeType coffeeType : coffeeTypes) {
                coffeeTypeRepository.saveCoffeeType(coffeeType);
            }
        }
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

    public int getMoney() {
        return money;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public void setCoffeeBeans(int coffeeBeans) {
        this.coffeeBeans = coffeeBeans;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<CoffeeType> getCoffeeTypes() {
        return coffeeTypeRepository.getAllCoffeeTypes();
    }

    public List<Transaction> getTransactionLog() {
        return transactionRepository.getAllTransactions();
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
            transactionRepository.saveTransaction(buyTransaction);
            return "I have enough resources, making you " + coffeeType.getName() + "\n";
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);
            Transaction notBuyTransaction = new Transaction(LocalDateTime.now(), coffeeType, "Not Bought", missing);
            transactionRepository.saveTransaction(notBuyTransaction);
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
        return true;
    }

    public void stop() {
        coffeeMachineRepository.updateStatus(this);
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