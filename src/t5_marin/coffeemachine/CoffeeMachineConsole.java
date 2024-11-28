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
        // Pass the machine ID to the constructor
        int machineId = 1; // Or any other ID you want to pass
        CoffeeMachineConsole console = new CoffeeMachineConsole(machineId);
        console.run();
    }

    public CoffeeMachineConsole(int id) {
        try {
            // Establish connection to the database
            connection = DriverManager.getConnection("jdbc:h2:./src/t5_marin/coffeemachine/coffee_machine_db.mv.db", "marin", "");

            // Initialize TransactionLogDAO
            transactionLogDAO = new TransactionLogDAO(connection);

            // Check if the coffee_type table exists, if not, create it and add default coffee types
            checkAndCreateCoffeeTypeTable();

            // Set machineId to the passed ID
            this.machineId = id;
            this.machine = getCoffeeMachineFromDB(machineId);

        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
        }
    }

    void run() {
        checkAndCreateMachineTables();

        if (this.machine == null) {
            System.out.println("Coffee machine with ID " + machineId + " not found.");
            return;
        }

        System.out.println("Welcome to Coffee Machine " + machineId + " Console!");

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Buy coffee");
            System.out.println("2. Login as admin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    buyAction();
                    break;
                case 2:
                    loginAction();
                    break;
                case 3:
                    System.out.println("Shutting down the machine. Bye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private void checkAndCreateMachineTables() {
        // Check and create coffee_type_<machineId> table
        String checkCoffeeTypeTableQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'coffee_type_" + machineId + "'";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(checkCoffeeTypeTableQuery);

            if (rs.next() && rs.getInt(1) == 0) {
                // Create the coffee_type_<machineId> table
                String createCoffeeTypeTableQuery = "CREATE TABLE coffee_type_" + machineId + " (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255), " +
                        "water INT, " +
                        "milk INT, " +
                        "coffee_beans INT, " +
                        "price FLOAT)";
                stmt.executeUpdate(createCoffeeTypeTableQuery);

                // Retrieve default coffee types from the global coffee_type table
                String fetchDefaultsQuery = "SELECT name, water, milk, coffee_beans, price FROM coffee_type";
                ResultSet coffeeTypesResultSet = stmt.executeQuery(fetchDefaultsQuery);

                // Insert the default coffee types into the coffee_type_<machineId> table
                while (coffeeTypesResultSet.next()) {
                    String name = coffeeTypesResultSet.getString("name");
                    int water = coffeeTypesResultSet.getInt("water");
                    int milk = coffeeTypesResultSet.getInt("milk");
                    int coffeeBeans = coffeeTypesResultSet.getInt("coffee_beans");
                    float price = coffeeTypesResultSet.getFloat("price");

                    // Insert each coffee type into the machine-specific table
                    String insertCoffeeTypeQuery = "INSERT INTO coffee_type_" + machineId + " (name, water, milk, coffee_beans, price) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmt = connection.prepareStatement(insertCoffeeTypeQuery)) {
                        pstmt.setString(1, name);
                        pstmt.setInt(2, water);
                        pstmt.setInt(3, milk);
                        pstmt.setInt(4, coffeeBeans);
                        pstmt.setFloat(5, price);
                        pstmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating or initializing coffee_type_" + machineId + " table: " + e.getMessage());
        }

        // Check and create transaction_log_<machineId> table
        String checkTransactionLogTableQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'transaction_log_" + machineId + "'";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(checkTransactionLogTableQuery);

            if (rs.next() && rs.getInt(1) == 0) {
                // Create the transaction_log_<machineId> table
                String createTransactionLogTableQuery = "CREATE TABLE transaction_log_" + machineId + " (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        "coffee_type_id INT, " +
                        "success BOOLEAN, " +
                        "amount FLOAT)";
                stmt.executeUpdate(createTransactionLogTableQuery);
            }
        } catch (SQLException e) {
            System.out.println("Error creating or initializing transaction_log_" + machineId + " table: " + e.getMessage());
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
        List<CoffeeType> coffeeTypes = coffeeTypeDAO.getAllCoffeeTypes(connection);
        for (CoffeeType coffeeType : coffeeTypes) {
            System.out.println(coffeeType);
        }
        System.out.print("Enter coffee type ID to buy: ");
        int coffeeId = sc.nextInt();

        CoffeeType coffeeType = coffeeTypeDAO.getCoffeeTypeById(coffeeId, connection);
        if (coffeeType != null) {
            System.out.println("You bought: " + coffeeType.getName());

            try {
                boolean success = true; // For simplicity, assume the purchase is successful
                float amount = coffeeType.getPrice();

                transactionLogDAO.logTransaction(coffeeId, success, amount);
            } catch (SQLException e) {
                System.out.println("Error logging transaction: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid coffee type selected.");
        }

        updateMachineInDB();
    }

    private void loginAction() {
        System.out.print("Enter admin password: ");
        String password = sc.next();

        if (machine.checkPassword(password)) {
            adminMenu();
        } else {
            System.out.println("Wrong password.");
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Fill resources");
            System.out.println("2. View remaining resources");
            System.out.println("3. Take money");
            System.out.println("4. Change password");
            System.out.println("5. View transaction log");
            System.out.println("6. Manage coffee types");
            System.out.println("7. Exit to main menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    handleFill();
                    break;
                case 2:
                    handleRemaining();
                    break;
                case 3:
                    handleTakeMoney();
                    break;
                case 4:
                    handleChangePassword();
                    break;
                case 5:
                    handleTransactionLog();
                    break;
                case 6:
                    coffeeMenu();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void coffeeMenu() {
        while (true) {
            System.out.println("\nCoffee Management Menu:");
            System.out.println("1. Add coffee");
            System.out.println("2. Remove coffee");
            System.out.println("3. List coffee types");
            System.out.println("4. Update coffee details");
            System.out.println("5. Exit to admin menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    handleAddCoffee();
                    break;
                case 2:
                    handleRemoveCoffee();
                    break;
                case 3:
                    handleListCoffee();
                    break;
                case 4:
                    handleUpdateCoffee();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleFill() {
        System.out.print("Enter amount of water to add: ");
        int water = sc.nextInt();
        System.out.print("Enter amount of milk to add: ");
        int milk = sc.nextInt();
        System.out.print("Enter amount of coffee beans to add: ");
        int coffeeBeans = sc.nextInt();
        System.out.print("Enter number of cups to add: ");
        int cups = sc.nextInt();
        machine.fillResources(water, milk, coffeeBeans, cups);
        updateMachineInDB();
    }

    private void handleRemaining() {
        System.out.println("Remaining resources in machine:");
        System.out.println(machine);
    }

    private void handleTakeMoney() {
        float amount = machine.takeMoney();
        System.out.println("I gave you $" + amount + ".");
        updateMachineInDB();
    }

    private void handleChangePassword() {
        System.out.print("Enter new password: ");
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
                String createTableQuery = "CREATE TABLE coffee_type (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "name VARCHAR(255), " +
                        "water INT, " +
                        "milk INT, " +
                        "coffee_beans INT, " +
                        "price FLOAT)";
                stmt.executeUpdate(createTableQuery);

                // Add default coffee types
                String insertDefaults = "INSERT INTO coffee_type (name, water, milk, coffee_beans, price) VALUES " +
                        "('Espresso', 50, 0, 18, 1.50), " +
                        "('Latte', 200, 150, 24, 2.50), " +
                        "('Cappuccino', 200, 100, 24, 3.00)";
                stmt.executeUpdate(insertDefaults);
            }
        } catch (SQLException e) {
            System.out.println("Error creating or initializing coffee_type table: " + e.getMessage());
        }
    }

    private void handleAddCoffee() {
        System.out.print("Enter coffee name: ");
        String name = sc.next();
        System.out.print("Enter water amount: ");
        int water = sc.nextInt();
        System.out.print("Enter milk amount: ");
        int milk = sc.nextInt();
        System.out.print("Enter coffee beans amount: ");
        int coffeeBeans = sc.nextInt();
        System.out.print("Enter coffee price: ");
        float price = sc.nextFloat();

        String insertQuery = "INSERT INTO coffee_type (name, water, milk, coffee_beans, price) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, water);
            pstmt.setInt(3, milk);
            pstmt.setInt(4, coffeeBeans);
            pstmt.setFloat(5, price);
            pstmt.executeUpdate();
            System.out.println("Coffee type added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding coffee type: " + e.getMessage());
        }
    }

    private void handleRemoveCoffee() {
        System.out.print("Enter coffee type ID to remove: ");
        int coffeeId = sc.nextInt();

        String deleteQuery = "DELETE FROM coffee_type WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(deleteQuery)) {
            pstmt.setInt(1, coffeeId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Coffee type removed successfully.");
            } else {
                System.out.println("No coffee type found with that ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing coffee type: " + e.getMessage());
        }
    }

    private void handleListCoffee() {
        List<CoffeeType> coffeeTypes = coffeeTypeDAO.getAllCoffeeTypes(connection);
        System.out.println("Coffee Types:");
        for (CoffeeType coffeeType : coffeeTypes) {
            System.out.println(coffeeType);
        }
    }

    private void handleUpdateCoffee() {
        System.out.print("Enter coffee type ID to update: ");
        int coffeeId = sc.nextInt();

        CoffeeType coffeeType = coffeeTypeDAO.getCoffeeTypeById(coffeeId, connection);
        if (coffeeType == null) {
            System.out.println("Coffee type not found.");
            return;
        }

        System.out.print("Enter new name (current: " + coffeeType.getName() + "): ");
        String name = sc.next();
        System.out.print("Enter new water amount (current: " + coffeeType.getWaterNeeded() + "): ");
        int water = sc.nextInt();
        System.out.print("Enter new milk amount (current: " + coffeeType.getMilkNeeded() + "): ");
        int milk = sc.nextInt();
        System.out.print("Enter new coffee beans amount (current: " + coffeeType.getCoffeeBeansNeeded() + "): ");
        int coffeeBeans = sc.nextInt();
        System.out.print("Enter new price (current: " + coffeeType.getPrice() + "): ");
        float price = sc.nextFloat();

        String updateQuery = "UPDATE coffee_type SET name = ?, water = ?, milk = ?, coffee_beans = ?, price = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, water);
            pstmt.setInt(3, milk);
            pstmt.setInt(4, coffeeBeans);
            pstmt.setFloat(5, price);
            pstmt.setInt(6, coffeeId);
            pstmt.executeUpdate();
            System.out.println("Coffee type updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating coffee type: " + e.getMessage());
        }
    }
}
