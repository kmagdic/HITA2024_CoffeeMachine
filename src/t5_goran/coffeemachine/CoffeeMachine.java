package t5_goran.coffeemachine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CoffeeMachine {

    int water;
    int milk;
    int coffeeBeans;
    int cups;
    float money;
    private List<CoffeeType> coffeeTypes = new ArrayList<>();
    private String adminUsername = "admin";
    private String adminPassword = "admin12345";
    private final String statusFileName = "docs/coffee_machine_status.txt";

    // Constructor to initialize the coffee machine with given resources
    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        coffeeTypes.add(new CoffeeType(1, "Espresso", 350, 0, 16, 4));
        coffeeTypes.add(new CoffeeType(2, "Latte", 350, 75, 20, 7));
        coffeeTypes.add(new CoffeeType(3, "Cappuccino", 200, 100, 12, 6));
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

    public List<CoffeeType> getCoffeeTypes() {
        return coffeeTypes;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    protected void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    protected void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
    // Check if the machine has enough resources for the selected coffee type
    public boolean hasEnoughResources(CoffeeType coffeeType) {
        return water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1;
    }
        // Method to buy a coffee, and log the transaction
    public String buyCoffee(CoffeeType coffeeType) {
        String result;
        LocalDateTime transactionTime = LocalDateTime.now();
        TransactionLogRepository logRepo = new TransactionLogRepository(DataConnection.getInstance().getConnection());

        if (hasEnoughResources(coffeeType)) {
            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.cups -= 1;
            this.money += coffeeType.getPrice();
            result = "I have enough resources, making you " + coffeeType.getName();
            logRepo.logTransaction(transactionTime, coffeeType.getId(), "Bought", null);
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);
            result = "Sorry, not enough " + missing;
            logRepo.logTransaction(transactionTime, coffeeType.getId(), "Not bought", missing);
        }
        return result;
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

    public float takeMoney() {
        float taken = money;
        money = 0;
        return taken;
    }

    public boolean login(String username, String password) {
        return adminUsername.equals(username) && adminPassword.equals(password);
    }

    public boolean changePassword(String newPassword) {
        if (newPassword.length() >= 7 && newPassword.matches(".*\\d.*")) {
            adminPassword = newPassword;
            System.out.println("Password is changed");
            return true;
        } else {
            return false;
        }
    }

//    public boolean loadFromFile(String fileName) {
//        try (FileReader reader = new FileReader(fileName);
//             Scanner fileScanner = new Scanner(reader)) {
//
//            fileScanner.useDelimiter("; |\n");
//            if (!fileScanner.hasNextInt()) throw new IOException("Invalid file format.");
//
//            water = fileScanner.nextInt();
//            milk = fileScanner.nextInt();
//            coffeeBeans = fileScanner.nextInt();
//            cups = fileScanner.nextInt();
//            money = Float.parseFloat(fileScanner.next());
//            adminUsername = fileScanner.next();
//            adminPassword = fileScanner.next().trim();
//            return true;
//
//        } catch (FileNotFoundException e) {
//            System.out.println("Status file not found. Using default values.");
//            return false;
//        } catch (Exception e) {
//            System.out.println("Failed to load the file. Using default values.");
//            return false;
//        }
//    }
//
//    public void saveToFile(String fileName) {
//        try (FileWriter writer = new FileWriter(fileName)) {
//            writer.write(water + "; " + milk + "; " + coffeeBeans + "; " + cups + "; " + money + "\n");
//            writer.write(adminUsername + "; " + adminPassword + "\n");
//        } catch (IOException e) {
//            System.out.println("Failed to save the status file.");
//        }
//    }

// Start the machine by initializing resources and database
    public boolean start() {
        DataConnection dataConnection = DataConnection.getInstance();
        CoffeeTypeRepository coffeeTypeRepository = new CoffeeTypeRepository(dataConnection.getConnection());
        TransactionLogRepository transactionLogRepository = new TransactionLogRepository(dataConnection.getConnection());

        coffeeTypeRepository.createTable();
        transactionLogRepository.createTable();

        coffeeTypes = coffeeTypeRepository.getAllCoffeeTypes();
        if (coffeeTypes.isEmpty()) {
            coffeeTypes.add(new CoffeeType(1, "Espresso", 350, 0, 16, 4));
            coffeeTypes.add(new CoffeeType(2, "Latte", 350, 75, 20, 7));
            coffeeTypes.add(new CoffeeType(3, "Cappuccino", 200, 100, 12, 6));

        }

        return true;
    }

    public void stop() {

    }
    // String representation of the coffee machine state
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
