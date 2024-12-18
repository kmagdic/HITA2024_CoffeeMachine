package t3_dinko.coffeemachine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachine {

    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private float money;
    private CoffeeType[] coffeeTypes = new CoffeeType[3];
    private CoffeeTypeRepository coffeeMachineRepository;
    private TransactionLog transactionLog;
    private final String TRANSACTION_SUCCESS_ACTION = "Bought";
    private final String TRANSACTION_FAIL_ACTION = "Not Bought";
    private final String I_HAVE_ENOUGH_RESOURCES = "I have enough resources, making you ";
    private final String I_DONT_HAVE_ENOUGH_RESOURCES = "Sorry, not enough ";

    private String adminUsername = "admin";
    private String adminPassword = "admin12345";
    private String statusFileName = "docs/coffee_machine_status.txt";

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        coffeeTypes[0] = new CoffeeType(1, "Espresso", 350, 0,16,4);
        coffeeTypes[1] = new CoffeeType(2,"Latte",350, 75,20,7);
        coffeeTypes[2] = new CoffeeType(3, "Capuccino",200, 100,12,6);
    }

    public CoffeeType[] getCoffeeTypes() {
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

            transactionLog = new TransactionLog(LocalDateTime.now(), coffeeType, TRANSACTION_SUCCESS_ACTION);

            return "I have enough resources, making you " + coffeeType.getName() + "\n";
        } else {

            String missing = calculateWhichIngredientIsMissing(coffeeType);
            transactionLog = new TransactionLog(LocalDateTime.now(), coffeeType, TRANSACTION_FAIL_ACTION, missing);

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


    public boolean loadFromFile(String fileName)  {
        FileReader reader = null;

        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }

        Scanner fileScanner = new Scanner(reader);

        // FILE format:
        // <water_status>; <milk_status>; <coffee_beans_status>; <cups_status>; <money_status>
        // <admin_username>; <admin_password>

        fileScanner.useDelimiter("; |\n"); // delimiter is "; " or "\n" (for the last value)

        water = fileScanner.nextInt();
        milk = fileScanner.nextInt();
        coffeeBeans = fileScanner.nextInt();
        cups = fileScanner.nextInt();
        money = Float.parseFloat(fileScanner.next());

        adminUsername = fileScanner.next();
        adminPassword = (fileScanner.next()).trim();

        return true;


    }

    public void saveToFile(String fileName){
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write(water + "; " +  milk + "; " + coffeeBeans + "; " + cups + "; " + money);
            writer.write("\n");
            writer.write(adminUsername + "; " + adminPassword);
            writer.write("\n");

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean start(Connection connection) {
        coffeeMachineRepository = new CoffeeTypeRepository(connection);
        coffeeMachineRepository.createTable();

        int rows = coffeeMachineRepository.getCoffeeTypeRowCount();

        if (rows < 3){
            for (CoffeeType coffeeType : coffeeTypes){
                coffeeMachineRepository.insert(coffeeType);
            }
        }

        return true;
    }

    public void stop() {
        saveToFile(statusFileName);
    }

    public TransactionLog getTransactionLog(){
        return transactionLog;
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