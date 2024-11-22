package t5_marin.coffeemachine;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CoffeeMachine {

    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private float money;
    private CoffeeType[] coffeeTypes = new CoffeeType[3];
    private String adminUsername = "admin";
    private String adminPassword = "admin12345";
    private String statusFileName = "docs/coffee_machine_status.txt";
    private List<String> transactionLog = new ArrayList<>();  // Transaction log to store all actions

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        coffeeTypes[0] = new CoffeeType("Espresso", 350, 0, 16, 4);
        coffeeTypes[1] = new CoffeeType("Latte", 350, 75, 20, 7);
        coffeeTypes[2] = new CoffeeType("Cappuccino", 200, 100, 12, 6);
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

    public boolean hasEnoughResources(CoffeeType coffeeType) {
        return water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1;
    }

    public String buyCoffee(CoffeeType coffeeType) {
        String resultMessage;
        if (hasEnoughResources(coffeeType)) {
            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.cups -= 1;
            this.money += coffeeType.getPrice();

            resultMessage = "I have enough resources, making you " + coffeeType.getName() + "\n";
            logTransaction(coffeeType.getName(), "Bought");
        } else {
            resultMessage = "Sorry, not enough " + calculateWhichIngredientIsMissing(coffeeType) + "\n";
            logTransaction(coffeeType.getName(), "Not bought, no enough ingredients: " + calculateWhichIngredientIsMissing(coffeeType));
        }
        return resultMessage;
    }

    private void logTransaction(String coffeeName, String action) {
        String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
        String logEntry = "Date/time: " + timeStamp + ", coffee type: " + coffeeName + ", action: " + action;
        transactionLog.add(logEntry);
    }

    public List<String> getTransactionLog() {
        return transactionLog;
    }

    public float takeMoney() {
        float moneyReturn = money;
        money = 0;
        return moneyReturn;
    }

    public String calculateWhichIngredientIsMissing(CoffeeType coffeeType) {
        if (water < coffeeType.getWaterNeeded()) return "water";
        if (milk < coffeeType.getMilkNeeded()) return "milk";
        if (coffeeBeans < coffeeType.getCoffeeBeansNeeded()) return "coffee beans";
        if (cups < 1) return "cups";
        return "unknown";
    }

    public void fill(int water, int milk, int coffeeBeans, int cups) {
        this.water += water;
        this.milk += milk;
        this.coffeeBeans += coffeeBeans;
        this.cups += cups;
    }

    public boolean login(String username, String password) {
        return adminUsername.equals(username) && adminPassword.equals(password);
    }

    public boolean changePassword(String newPassword) {
        if (newPassword.length() >= 7 && newPassword.matches(".*\\d.*")) {
            adminPassword = newPassword;
            return true;
        }
        return false;
    }

    public boolean loadFromFile(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            Scanner fileScanner = new Scanner(reader);
            fileScanner.useDelimiter("; |\\n");

            water = fileScanner.nextInt();
            milk = fileScanner.nextInt();
            coffeeBeans = fileScanner.nextInt();
            cups = fileScanner.nextInt();
            money = Float.parseFloat(fileScanner.next());

            adminUsername = fileScanner.next();
            adminPassword = fileScanner.next().trim();

            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + fileName);
            return false;
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    public void saveToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(water + "; " + milk + "; " + coffeeBeans + "; " + cups + "; " + money);
            writer.write("\n");
            writer.write(adminUsername + "; " + adminPassword);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean start() {
        return loadFromFile(statusFileName);
    }

    public void stop() {
        saveToFile(statusFileName);
        saveTransactionLogToFile("docs/transaction_log.txt"); // Save transaction log to file
    }

    private void saveTransactionLogToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String logEntry : transactionLog) {
                writer.write(logEntry + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving transaction log: " + e.getMessage());
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
