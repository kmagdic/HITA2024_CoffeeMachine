package t5_goran.coffeemachine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class CoffeeMachine {

    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private float money;
    private ArrayList<CoffeeType> coffeeTypes = new ArrayList<>();
    private ArrayList<String> transactionLog = new ArrayList<>();

    private String adminUsername = "admin";
    private String adminPassword = "admin12345";
    private String statusFileName = "docs/coffee_machine_status.txt";

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        coffeeTypes.add(new CoffeeType("Espresso", 350, 0, 16, 4));
        coffeeTypes.add(new CoffeeType("Latte", 350, 75, 20, 7));
        coffeeTypes.add(new CoffeeType("Capuccino", 200, 100, 12, 6));
    }

    public ArrayList<CoffeeType> getCoffeeTypes() {
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

    public float getMoney() {
        return money;
    }

    public boolean hasEnoughResources(CoffeeType coffeeType) {
        return water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1;
    }

    public String buyCoffee(CoffeeType coffeeType) {
        String result;
        if (hasEnoughResources(coffeeType)) {
            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.cups -= 1;
            this.money += coffeeType.getPrice();
            result = "I have enough resources, making you " + coffeeType.getName();
            logTransaction(coffeeType.getName(), "Bought", null);
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);
            result = "Sorry, not enough " + missing;
            logTransaction(coffeeType.getName(), "Not bought", missing);
        }
        return result;
    }

    public void logTransaction(String coffeeType, String action, String reason) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);

        if (reason == null) {
            transactionLog.add("Date/time: " + timestamp + ", coffee type: " + coffeeType + ", action: " + action);
        } else {
            transactionLog.add("Date/time: " + timestamp + ", coffee type: " + coffeeType + ", action: " + action + ", no enough ingredients: " + reason);
        }
    }


    public void printTransactionLog() {
        System.out.println("Transaction log:");
        for (String entry : transactionLog) {
            System.out.println(entry);
        }
        System.out.println();
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
            saveToFile(statusFileName);
            return true;
        }
        return false;
    }

    public boolean loadFromFile(String fileName) {
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

    public void saveToFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write(water + "; " + milk + "; " + coffeeBeans + "; " + cups + "; " + money);
            writer.write("\n");
            writer.write(adminUsername + "; " + adminPassword);
            writer.write("\n");

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean start() {
        return loadFromFile(statusFileName);
    }

    public void stop() {
        saveToFile(statusFileName);
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
