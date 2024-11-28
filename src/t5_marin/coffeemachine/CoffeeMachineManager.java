package t5_marin.coffeemachine;

import java.sql.*;
import java.util.Scanner;

public class CoffeeMachineManager {
    private static final Scanner sc = new Scanner(System.in);
    private static Connection connection;

    public static void main(String[] args) {
        try {
            // Establishing connection to the database
            connection = DriverManager.getConnection("jdbc:h2:./src/t5_marin/coffeemachine/coffee_machine_db.mv.db", "marin", "");

            // Check if connection is successful
            if (connection != null) {
                System.out.println("Connection successful!");
            }

            // Initialize manager and check for table existence
            CoffeeMachineManager manager = new CoffeeMachineManager();
            manager.checkAndCreateTable();

            // Start the Coffee Machine Manager functionality
            manager.run();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    // Check and create necessary tables
    public void checkAndCreateTable() {
        if (connection == null) {
            throw new IllegalStateException("Database connection is not established.");
        }

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "COFFEE_MACHINE", null);

            if (!tables.next()) {
                System.out.println("Table 'coffee_machine' not found. Creating the table...");
                String createTableSQL = "CREATE TABLE coffee_machine (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "water INT, " +
                        "milk INT, " +
                        "coffee_beans INT, " +
                        "cups INT, " +
                        "money FLOAT)";
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate(createTableSQL);
                    System.out.println("Table 'coffee_machine' created.");
                }
            } else {
                System.out.println("Table 'coffee_machine' found.");
            }

            // Create coffee_types table if not already present
            tables = metaData.getTables(null, null, "COFFEE_TYPES", null);
            if (!tables.next()) {
                System.out.println("Table 'coffee_types' not found. Creating the table...");
                String createCoffeeTypesSQL = "CREATE TABLE coffee_types (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(255), " +
                        "description TEXT)";
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate(createCoffeeTypesSQL);
                    System.out.println("Table 'coffee_types' created.");
                }
            } else {
                System.out.println("Table 'coffee_types' found.");
            }

        } catch (SQLException e) {
            System.out.println("Error checking or creating tables: " + e.getMessage());
        }
    }

    // Run the Coffee Machine Manager's main functionality
    public void run() {
        System.out.println("Coffee Machine Manager");
        int action = 0;

        while (action != 6) {
            System.out.println("Choose action:");
            System.out.println("1. Add coffee machine");
            System.out.println("2. View coffee machines");
            System.out.println("3. Update coffee machine");
            System.out.println("4. Delete coffee machine");
            System.out.println("5. Activate coffee machine");
            System.out.println("6. Exit");

            action = sc.nextInt();

            switch (action) {
                case 1:
                    addCoffeeMachine();
                    break;
                case 2:
                    viewCoffeeMachines();
                    break;
                case 3:
                    updateCoffeeMachine();
                    break;
                case 4:
                    deleteCoffeeMachine();
                    break;
                case 5:
                    activateCoffeeMachine();
                    break;
                case 6:
                    System.out.println("Exiting Coffee Machine Manager.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number between 1 and 6.");
            }
        }
    }

    // Add a new coffee machine to the database
    public void addCoffeeMachine() {
        System.out.println("Enter coffee machine details:");

        System.out.print("Enter water amount (ml): ");
        int water = sc.nextInt();

        System.out.print("Enter milk amount (ml): ");
        int milk = sc.nextInt();

        System.out.print("Enter coffee beans amount (grams): ");
        int coffeeBeans = sc.nextInt();

        System.out.print("Enter number of cups: ");
        int cups = sc.nextInt();

        System.out.print("Enter amount of money ($): ");
        float money = sc.nextFloat();

        String insertSQL = "INSERT INTO coffee_machine (water, milk, coffee_beans, cups, money) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, water);
            pstmt.setInt(2, milk);
            pstmt.setInt(3, coffeeBeans);
            pstmt.setInt(4, cups);
            pstmt.setFloat(5, money);
            pstmt.executeUpdate();
            System.out.println("Coffee machine added successfully!");
        } catch (SQLException e) {
            System.out.println("Error inserting coffee machine: " + e.getMessage());
        }
    }

    // View all coffee machines in the database
    public void viewCoffeeMachines() {
        String selectSQL = "SELECT * FROM coffee_machine";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            System.out.println("List of coffee machines:");
            while (rs.next()) {
                int id = rs.getInt("id");
                int water = rs.getInt("water");
                int milk = rs.getInt("milk");
                int coffeeBeans = rs.getInt("coffee_beans");
                int cups = rs.getInt("cups");
                float money = rs.getFloat("money");

                System.out.printf("ID: %d, Water: %d ml, Milk: %d ml, Coffee Beans: %d g, Cups: %d, Money: $%.2f\n",
                        id, water, milk, coffeeBeans, cups, money);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching coffee machines: " + e.getMessage());
        }
    }

    // Update a coffee machine's details
    public void updateCoffeeMachine() {
        System.out.println("Enter the ID of the coffee machine to update:");
        int id = sc.nextInt();

        System.out.println("Enter new details (water, milk, coffeeBeans, cups, money):");
        int water = sc.nextInt();
        int milk = sc.nextInt();
        int coffeeBeans = sc.nextInt();
        int cups = sc.nextInt();
        float money = sc.nextFloat();

        String updateSQL = "UPDATE coffee_machine SET water = ?, milk = ?, coffee_beans = ?, cups = ?, money = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(1, water);
            pstmt.setInt(2, milk);
            pstmt.setInt(3, coffeeBeans);
            pstmt.setInt(4, cups);
            pstmt.setFloat(5, money);
            pstmt.setInt(6, id);
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Coffee machine updated successfully!");
            } else {
                System.out.println("No coffee machine found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error updating coffee machine: " + e.getMessage());
        }
    }

    // Delete a coffee machine from the database
    public void deleteCoffeeMachine() {
        System.out.println("Enter the ID of the coffee machine to delete:");
        int id = sc.nextInt();

        String deleteSQL = "DELETE FROM coffee_machine WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Coffee machine deleted successfully!");
            } else {
                System.out.println("No coffee machine found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting coffee machine: " + e.getMessage());
        }
    }

    // Activate a specific coffee machine
    public void activateCoffeeMachine() {
        // Display available coffee machines
        System.out.println("List of available coffee machines:");
        String selectSQL = "SELECT * FROM coffee_machine";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            boolean hasMachines = false;
            while (rs.next()) {
                hasMachines = true;
                int id = rs.getInt("id");
                int water = rs.getInt("water");
                int milk = rs.getInt("milk");
                int coffeeBeans = rs.getInt("coffee_beans");
                int cups = rs.getInt("cups");
                float money = rs.getFloat("money");

                System.out.printf("ID: %d, Water: %d ml, Milk: %d ml, Coffee Beans: %d g, Cups: %d, Money: $%.2f\n",
                        id, water, milk, coffeeBeans, cups, money);
            }

            if (!hasMachines) {
                System.out.println("No available coffee machines to activate.");
                return;
            }

            // Prompt user to select the coffee machine
            System.out.print("Enter the ID of the coffee machine you want to activate: ");
            int selectedMachineId = sc.nextInt();

            // Check if the selected coffee machine ID exists
            String checkMachineSQL = "SELECT * FROM coffee_machine WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(checkMachineSQL)) {
                pstmt.setInt(1, selectedMachineId);
                ResultSet machineResult = pstmt.executeQuery();

                if (machineResult.next()) {
                    // If machine exists, activate it
                    System.out.println("Activating Coffee Machine Console for machine ID " + selectedMachineId + "...");
                    CoffeeMachineConsole.main(new String[]{});
                } else {
                    System.out.println("No coffee machine found with ID " + selectedMachineId);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error fetching coffee machines: " + e.getMessage());
        }
    }
}
