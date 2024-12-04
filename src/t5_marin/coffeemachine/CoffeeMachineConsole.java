package t5_marin.coffeemachine;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachineConsole {
    private CoffeeMachine coffeeMachine;
    private Scanner sc = new Scanner(System.in);
    private Connection connection;

    public CoffeeMachineConsole(Connection connection, int coffeeMachineId) {
        this.connection = connection;
        this.coffeeMachine = getCoffeeMachineById(coffeeMachineId);
    }

    // Method to fetch the CoffeeMachine object based on the ID
    private CoffeeMachine getCoffeeMachineById(int coffeeMachineId) {
        try {
            String sql = "SELECT * FROM CoffeeMachine WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, coffeeMachineId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new CoffeeMachine(
                            rs.getInt("id"),
                            rs.getInt("water"),
                            rs.getInt("milk"),
                            rs.getInt("coffeeBeans"),
                            rs.getInt("cups"),
                            rs.getFloat("money")
                    );
                } else {
                    System.out.println("Coffee Machine with ID " + coffeeMachineId + " not found.");
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Coffee Machine: " + e.getMessage());
            return null;
        }
    }

    public void activate() {
        if (coffeeMachine == null) {
            System.out.println("Unable to activate the console due to missing coffee machine.");
            return;
        }

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Buy");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> buy();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void buy() {
        System.out.println("Available Coffee Types:");
        CoffeeTypeDAO coffeeTypeDAO = new CoffeeTypeDAO();
        List<CoffeeType> coffeeTypes = coffeeTypeDAO.getAllCoffeeTypes(connection);

        // List coffee types with their IDs and prices
        for (int i = 0; i < coffeeTypes.size(); i++) {
            CoffeeType type = coffeeTypes.get(i);
            System.out.println((i + 1) + ". " + type.getName() + " - Price: " + type.getPrice() + " | Water Needed: " + type.getWaterNeeded() + " | Milk Needed: " + type.getMilkNeeded() + " | Coffee Beans Needed: " + type.getCoffeeBeansNeeded());
        }

        System.out.println("Enter coffee type ID to buy:");
        int coffeeTypeId = sc.nextInt();

        // Check if coffee type exists
        if (coffeeTypeId < 1 || coffeeTypeId > coffeeTypes.size()) {
            System.out.println("Invalid coffee type ID.");
            return;
        }

        CoffeeType selectedCoffeeType = coffeeTypes.get(coffeeTypeId - 1);

        // Check if the coffee machine has enough ingredients
        if (coffeeMachine.getWater() < selectedCoffeeType.getWaterNeeded() ||
                coffeeMachine.getMilk() < selectedCoffeeType.getMilkNeeded() ||
                coffeeMachine.getCoffeeBeans() < selectedCoffeeType.getCoffeeBeansNeeded() ||
                coffeeMachine.getCups() < 1) {
            System.out.println("Not enough ingredients or cups.");
            return;
        }

        // Deduct the ingredients and update the coffee machine
        coffeeMachine.setWater(coffeeMachine.getWater() - selectedCoffeeType.getWaterNeeded());
        coffeeMachine.setMilk(coffeeMachine.getMilk() - selectedCoffeeType.getMilkNeeded());
        coffeeMachine.setCoffeeBeans(coffeeMachine.getCoffeeBeans() - selectedCoffeeType.getCoffeeBeansNeeded());
        coffeeMachine.setCups(coffeeMachine.getCups() - 1);
        coffeeMachine.setMoney(coffeeMachine.getMoney() + selectedCoffeeType.getPrice());

        // Update the coffee machine in the database
        updateCoffeeMachine(coffeeMachine);

        // Log the transaction
        logTransaction(coffeeMachine.getId(), "Bought " + selectedCoffeeType.getName() + " for " + selectedCoffeeType.getPrice());

        System.out.println("Purchase successful! Enjoy your coffee.");
    }

    private void login() {
        System.out.println("Choose an option:");
        System.out.println("1. Change Password");
        System.out.println("2. Print Transaction Log");
        System.out.println("3. Add Ingredient");
        System.out.println("4. Take Money");
        System.out.println("5. Add Coffee Type");
        System.out.println("6. Exit");

        int choice = sc.nextInt();
        switch (choice) {
            case 1 -> changePassword();
            case 2 -> printTransactionLog();
            case 3 -> addIngredient();
            case 4 -> takeMoney();
            case 5 -> addCoffeeType();
            case 6 -> {
                System.out.println("Exiting admin console.");
                return;
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
    }

    // Admin function to change the password
    private void changePassword() {
        System.out.println("Enter the new password:");
        String newPassword = sc.next();
        // Logic to update the password (example, update in the database)
        System.out.println("Password successfully changed.");
    }

    // Admin function to print the transaction log
    private void printTransactionLog() {
        String selectSQL = "SELECT * FROM TransactionLog";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " + rs.getString("log") + " | Date: " + rs.getTimestamp("timestamp"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transaction log: " + e.getMessage());
        }
    }

    // Admin function to add ingredients to the coffee machine
    private void addIngredient() {
        System.out.println("Enter the ingredient to add (1: Water, 2: Milk, 3: Coffee Beans):");
        int ingredientChoice = sc.nextInt();
        System.out.println("Enter the amount to add:");
        int amount = sc.nextInt();

        switch (ingredientChoice) {
            case 1 -> coffeeMachine.setWater(coffeeMachine.getWater() + amount);
            case 2 -> coffeeMachine.setMilk(coffeeMachine.getMilk() + amount);
            case 3 -> coffeeMachine.setCoffeeBeans(coffeeMachine.getCoffeeBeans() + amount);
            default -> System.out.println("Invalid ingredient choice.");
        }

        updateCoffeeMachine(coffeeMachine);
        System.out.println("Ingredient added successfully.");
    }

    // Admin function to take money from the coffee machine
    private void takeMoney() {
        System.out.println("Enter the amount of money to take:");
        float amount = sc.nextFloat();
        if (coffeeMachine.getMoney() >= amount) {
            coffeeMachine.setMoney(coffeeMachine.getMoney() - amount);
            System.out.println("Money taken successfully.");
        } else {
            System.out.println("Insufficient funds in the coffee machine.");
        }
        updateCoffeeMachine(coffeeMachine);
    }

    // Admin function to add a new coffee type
    private void addCoffeeType() {
        // Prompt the user for the coffee type details
        System.out.println("Enter the coffee name:");
        String name = sc.next();
        System.out.println("Enter the price of the coffee:");
        int price = sc.nextInt();
        System.out.println("Enter the amount of water needed:");
        int water = sc.nextInt();
        System.out.println("Enter the amount of milk needed:");
        int milk = sc.nextInt();
        System.out.println("Enter the amount of coffee beans needed:");
        int coffeeBeans = sc.nextInt();

        // Assuming ID is auto-generated by the database, you don't need to set it manually
        // Create a new CoffeeType object. The ID is not needed here since it will be auto-generated.
        CoffeeType newCoffeeType = new CoffeeType(0,name, price, water, milk, coffeeBeans);

        // Create a new DAO object to interact with the database
        CoffeeTypeDAO coffeeTypeDAO = new CoffeeTypeDAO();

        try {
            // Insert the new coffee type into the database
            coffeeTypeDAO.insertCoffeeType(newCoffeeType, connection);

            // Provide feedback to the user
            System.out.println("Coffee type added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding coffee type: " + e.getMessage());
        }
    }


    // Update the coffee machine in the database
    private void updateCoffeeMachine(CoffeeMachine coffeeMachine) {
        String updateSQL = "UPDATE CoffeeMachine SET water = ?, milk = ?, coffeeBeans = ?, cups = ?, money = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateSQL)) {
            stmt.setInt(1, coffeeMachine.getWater());
            stmt.setInt(2, coffeeMachine.getMilk());
            stmt.setInt(3, coffeeMachine.getCoffeeBeans());
            stmt.setInt(4, coffeeMachine.getCups());
            stmt.setFloat(5, coffeeMachine.getMoney());
            stmt.setInt(6, coffeeMachine.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating coffee machine: " + e.getMessage());
        }
    }

    // Log a transaction
    private void logTransaction(int coffeeMachineId, String logMessage) {
        String insertSQL = "INSERT INTO TransactionLog (coffeeMachineId, log) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, coffeeMachineId);
            pstmt.setString(2, logMessage);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }
}
