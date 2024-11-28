package t5_marin.coffeemachine;

import java.sql.*;
import java.util.*;

public class CoffeeMachineConsole {

    private final Scanner sc = new Scanner(System.in);
    private static Connection connection;
    private CoffeeMachine machine;
    private int machineId;
    private CoffeeTypeDAO coffeeTypeDAO = new CoffeeTypeDAO();
    private TransactionLogDAO transactionLogDAO;

    public static void main(String[] args) {
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    public CoffeeMachineConsole() {
        try {
            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:h2:./src/t5_marin/coffeemachine/coffee_machine_db.mv.db", "marin", "");

            // Initialize TransactionLogDAO
            transactionLogDAO = new TransactionLogDAO(connection);

            // Check if the coffee_type table exists, if not, create it and add default coffee types
            checkAndCreateCoffeeTypeTable();

        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            return;
        }
    }

    void run() {
        System.out.println("Choose a coffee machine by ID to activate: ");
        int machineId = sc.nextInt();
        this.machineId = machineId;

        this.machine = getCoffeeMachineFromDB(machineId);

        if (this.machine == null) {
            System.out.println("Coffee machine with ID " + machineId + " not found.");
            return;
        }

        System.out.println("Welcome to Coffee Machine " + machineId + " Console!");

        String action = "";

        while (!action.equals("exit")) {
            System.out.println("Write action (buy, login, exit): ");
            action = sc.next();
            switch (action) {
                case "buy":
                    buyAction();
                    break;

                case "login":
                    System.out.println("Enter username: ");
                    String username = sc.next();
                    System.out.println("Enter password: ");
                    String password = sc.next();

                    if (machine.checkPassword(password)) {
                        adminMenu();
                    } else {
                        System.out.println("Wrong password\n");
                    }
                    break;

                case "exit":
                    System.out.println("Shutting down the machine. Bye!");
                    break;

                default:
                    System.out.println("No such option");
            }
        }
    }

    private CoffeeMachine getCoffeeMachineFromDB(int machineId) {
        String query = "SELECT * FROM coffee_machine WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, machineId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int water = rs.getInt("water");
                int milk = rs.getInt("milk");
                int coffeeBeans = rs.getInt("coffee_beans");
                int cups = rs.getInt("cups");
                float money = rs.getFloat("money");

                CoffeeMachine machine = new CoffeeMachine();
                machine.fillResources(water, milk, coffeeBeans, cups);

                return machine;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching coffee machine details: " + e.getMessage());
        }
        return null;
    }

    private void buyAction() {
        System.out.println("Buying coffee... Choose coffee type:");
        // Here you can provide a list of available coffee types to the user
        List<CoffeeType> coffeeTypes = coffeeTypeDAO.getAllCoffeeTypes(connection);
        for (CoffeeType coffeeType : coffeeTypes) {
            System.out.println(coffeeType);
        }
        System.out.println("Choose coffee type ID to buy:");
        int coffeeId = sc.nextInt();

        CoffeeType coffeeType = coffeeTypeDAO.getCoffeeTypeById(coffeeId, connection);
        if (coffeeType != null) {
            System.out.println("You bought: " + coffeeType.getName());

            // Log the transaction after a successful purchase
            try {
                // Here we assume the purchase is always successful for simplicity
                // You can modify the success flag and the amount accordingly
                boolean success = true;
                float amount = coffeeType.getPrice();

                // Log the transaction
                transactionLogDAO.logTransaction(coffeeId, success, amount);
            } catch (SQLException e) {
                System.out.println("Error logging transaction: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid coffee type selected.");
        }

        updateMachineInDB();
    }


    private void adminMenu() {
        String ch = "";
        while (!ch.equals("exit")) {
            System.out.println("\nWrite action (fill, remaining, take, password, log, coffee, exit):");
            ch = sc.next();

            switch (ch) {
                case "fill":
                    handleFill();
                    break;

                case "take":
                    handleTakeMoney();
                    break;

                case "remaining":
                    handleRemaining();
                    break;

                case "password":
                    handleChangePassword();
                    break;

                case "log":
                    handleTransactionLog();
                    break;

                case "coffee":
                    coffeeMenu();  // New menu for managing coffee types
                    break;

                case "exit":
                    break;

                default:
                    System.out.println("No such option");
            }

            // Update machine in DB after every admin action
            updateMachineInDB();
        }
    }

    private void coffeeMenu() {
        String option = "";
        while (!option.equals("exit")) {
            System.out.println("\nCoffee Menu: (add, remove, list, update, exit):");
            option = sc.next();

            switch (option) {
                case "add":
                    handleAddCoffee();
                    break;

                case "remove":
                    handleRemoveCoffee();
                    break;

                case "list":
                    handleListCoffee();
                    break;

                case "update":
                    handleUpdateCoffee();
                    break;

                case "exit":
                    break;

                default:
                    System.out.println("No such option");
            }
        }
    }

    private void handleAddCoffee() {
        System.out.println("Enter coffee name:");
        String coffeeName = sc.next();
        System.out.println("Enter amount of water needed:");
        int waterNeeded = sc.nextInt();
        System.out.println("Enter amount of milk needed:");
        int milkNeeded = sc.nextInt();
        System.out.println("Enter amount of coffee beans needed:");
        int coffeeBeansNeeded = sc.nextInt();
        System.out.println("Enter price of the coffee:");
        float price = sc.nextFloat();

        CoffeeType newCoffee = new CoffeeType(0, coffeeName, waterNeeded, milkNeeded, coffeeBeansNeeded, price);
        try {
            coffeeTypeDAO.insertCoffeeType(newCoffee, connection);
            System.out.println("Coffee added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding coffee: " + e.getMessage());
        }
    }

    private void handleRemoveCoffee() {
        System.out.println("Enter coffee ID to remove:");
        int coffeeId = sc.nextInt();

        CoffeeType coffeeType = coffeeTypeDAO.getCoffeeTypeById(coffeeId, connection);
        if (coffeeType != null) {
            String deleteSQL = "DELETE FROM coffee_type WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
                pstmt.setInt(1, coffeeId);
                pstmt.executeUpdate();
                System.out.println("Coffee removed successfully.");
            } catch (SQLException e) {
                System.out.println("Error removing coffee: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid coffee ID.");
        }
    }

    private void handleListCoffee() {
        List<CoffeeType> coffeeTypes = coffeeTypeDAO.getAllCoffeeTypes(connection);
        System.out.println("Available coffee types:");
        for (CoffeeType coffeeType : coffeeTypes) {
            System.out.println(coffeeType);
        }
    }

    private void handleUpdateCoffee() {
        System.out.println("Enter coffee ID to update:");
        int coffeeId = sc.nextInt();

        CoffeeType coffeeType = coffeeTypeDAO.getCoffeeTypeById(coffeeId, connection);
        if (coffeeType != null) {
            System.out.println("Enter new name for coffee:");
            String coffeeName = sc.next();
            System.out.println("Enter new amount of water needed:");
            int waterNeeded = sc.nextInt();
            System.out.println("Enter new amount of milk needed:");
            int milkNeeded = sc.nextInt();
            System.out.println("Enter new amount of coffee beans needed:");
            int coffeeBeansNeeded = sc.nextInt();
            System.out.println("Enter new price of coffee:");
            float price = sc.nextFloat();

            CoffeeType updatedCoffee = new CoffeeType(coffeeId, coffeeName, waterNeeded, milkNeeded, coffeeBeansNeeded, price);
            String updateSQL = "UPDATE coffee_type SET name = ?, water = ?, milk = ?, coffee_beans = ?, price = ? WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
                pstmt.setString(1, updatedCoffee.getName());
                pstmt.setInt(2, updatedCoffee.getWaterNeeded());
                pstmt.setInt(3, updatedCoffee.getMilkNeeded());
                pstmt.setInt(4, updatedCoffee.getCoffeeBeansNeeded());
                pstmt.setFloat(5, updatedCoffee.getPrice());
                pstmt.setInt(6, coffeeId);
                pstmt.executeUpdate();
                System.out.println("Coffee updated successfully.");
            } catch (SQLException e) {
                System.out.println("Error updating coffee: " + e.getMessage());
            }
        } else {
            System.out.println("Coffee not found.");
        }
    }

    private void handleFill() {
        System.out.println("Fill the resources...");
        int water = sc.nextInt();
        int milk = sc.nextInt();
        int coffeeBeans = sc.nextInt();
        int cups = sc.nextInt();
        machine.fillResources(water, milk, coffeeBeans, cups);
        updateMachineInDB();
    }

    private void handleTakeMoney() {
        float amount = machine.takeMoney();
        System.out.println("I gave you $" + amount + "\n");
        updateMachineInDB();
    }

    private void handleRemaining() {
        System.out.println("Remaining resources in machine:");
        System.out.println(machine);
    }

    private void handleChangePassword() {
        System.out.println("Enter new password:");
        String newPassword = sc.next();
        machine.setPassword(newPassword);
        System.out.println("Password updated successfully.");
    }

    private void handleTransactionLog() {
        String query = "SELECT * FROM transaction_log ORDER BY datetime DESC";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Transaction Log:");
            while (rs.next()) {
                int id = rs.getInt("id");
                Timestamp timestamp = rs.getTimestamp("datetime");
                int coffeeTypeId = rs.getInt("coffee_type_id");
                boolean success = rs.getBoolean("success");
                float amount = rs.getFloat("amount");
                System.out.println("ID: " + id + ", Date: " + timestamp + ", Coffee Type ID: " + coffeeTypeId +
                        ", Success: " + success + ", Amount: " + amount);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving transaction logs: " + e.getMessage());
        }
    }


    private void updateMachineInDB() {
        String updateQuery = "UPDATE coffee_machine SET water = ?, milk = ?, coffee_beans = ?, cups = ?, money = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setInt(1, machine.getWater());
            pstmt.setInt(2, machine.getMilk());
            pstmt.setInt(3, machine.getCoffeeBeans());
            pstmt.setInt(4, machine.getCups());
            pstmt.setFloat(5, machine.getMoney());
            pstmt.setInt(6, machineId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating coffee machine: " + e.getMessage());
        }
    }

    private void checkAndCreateCoffeeTypeTable() {
        String checkTableQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'coffee_type'";

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(checkTableQuery);

            if (rs.next() && rs.getInt(1) == 0) {
                // Create the coffee_type table if it doesn't exist
                String createTableQuery = "CREATE TABLE coffee_type (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255), " +
                        "water INT, " +
                        "milk INT, " +
                        "coffee_beans INT, " +
                        "price FLOAT)";
                stmt.executeUpdate(createTableQuery);
                System.out.println("Created 'coffee_type' table.");

                // Add default coffee types to the table
                addDefaultCoffeeTypes();
            }
        } catch (SQLException e) {
            System.out.println("Error checking or creating 'coffee_type' table: " + e.getMessage());
        }
    }

    private void addDefaultCoffeeTypes() throws SQLException {
        coffeeTypeDAO.insertCoffeeType(new CoffeeType(1,"Espresso", 350, 0, 16, 4), connection);
        coffeeTypeDAO.insertCoffeeType(new CoffeeType(2,"Latte", 350, 75, 20, 7), connection);
        coffeeTypeDAO.insertCoffeeType(new CoffeeType(3,"Cappuccino", 200, 100, 12, 6), connection);
        System.out.println("Default coffee types added.");
    }
}
