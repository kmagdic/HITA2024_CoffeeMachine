package t5_marin.coffeemachine;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private float money;
    private CoffeeType[] coffeeTypes = new CoffeeType[3];
    private String adminUsername = "admin";
    private String adminPassword = "admin12345";
    private Connection connection;  // Connection to H2 database

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

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        coffeeTypes[0] = new CoffeeType("Espresso", 350, 0, 16, 4);
        coffeeTypes[1] = new CoffeeType("Latte", 350, 75, 20, 7);
        coffeeTypes[2] = new CoffeeType("Cappuccino", 200, 100, 12, 6);
        setupDatabase();  // Initialize the H2 database
    }

    // Database setup method
    private void setupDatabase() {
        try {
            String dbPath = "jdbc:h2:./src/t5_marin/coffeemachine/coffee_machine_db";
            connection = DriverManager.getConnection(dbPath, "marin", "");
            String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS transaction_log (
                        id IDENTITY PRIMARY KEY,
                        datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        coffee_type VARCHAR(255),
                        success BOOLEAN,
                        amount DECIMAL(10, 2)
                    );
                    """;
            connection.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Error setting up database: " + e.getMessage(), e);
        }
    }

    // Log transaction to the database
    private void logTransaction(String coffeeName, boolean success, float amount) {
        String insertSQL = "INSERT INTO transaction_log (coffee_type, success, amount) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, coffeeName);
            pstmt.setBoolean(2, success);
            pstmt.setFloat(3, amount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }

    // Other CoffeeMachine methods...
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

            logTransaction(coffeeType.getName(), true, coffeeType.getPrice());
            resultMessage = "I have enough resources, making you " + coffeeType.getName() + "\n";
        } else {
            String missingIngredient = calculateWhichIngredientIsMissing(coffeeType);
            logTransaction(coffeeType.getName(), false, 0);
            resultMessage = "Sorry, not enough " + missingIngredient + "\n";
        }
        return resultMessage;
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
        float moneyReturn = money;
        money = 0;
        return moneyReturn;
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

    public CoffeeType[] getCoffeeTypes() {
        return coffeeTypes;
    }

    public boolean start() {
        System.out.println("Coffee machine started. Database connection initialized.");
        return true; // Simplified for demonstration purposes
    }

    public void stop() {
        System.out.println("Coffee machine stopped.");
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }

    public List<String> getTransactionLog() {
        List<String> logs = new ArrayList<>();
        String query = "SELECT datetime, coffee_type, success, amount FROM transaction_log ORDER BY datetime DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String datetime = rs.getTimestamp("datetime").toString();
                String coffeeType = rs.getString("coffee_type");
                boolean success = rs.getBoolean("success");
                float amount = rs.getFloat("amount");

                logs.add(String.format(
                        "Date: %s | Coffee: %s | Success: %s | Amount: $%.2f",
                        datetime, coffeeType, success ? "Yes" : "No", amount
                ));
            }
        } catch (SQLException e) {
            logs.add("Error fetching transaction log: " + e.getMessage());
        }

        return logs;
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
