package t5_marin.coffeemachine;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachineManager {
    private static final Scanner sc = new Scanner(System.in);
    private static Connection connection;
    private static CoffeeMachineManagerDB dbManager;

    public static void main(String[] args) {
        try {
            // Establishing connection to the database
            connection = DriverManager.getConnection("jdbc:h2:./src/t5_marin/coffeemachine/coffee_machine_db.mv.db", "marin", "");

            // Initialize dbManager with the connection
            dbManager = new CoffeeMachineManagerDB(connection);

            // Check and create necessary tables
            dbManager.checkAndCreateTable();

            // Start the Coffee Machine Manager functionality
            run();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    // Run the Coffee Machine Manager's main functionality
    public static void run() {
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
    public static void addCoffeeMachine() {
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

        dbManager.addCoffeeMachine(water, milk, coffeeBeans, cups, money);
    }

    // View all coffee machines in the database
    public static void viewCoffeeMachines() {
        List<CoffeeMachine> machines = dbManager.viewCoffeeMachines();
        System.out.println("List of coffee machines:");
        for (CoffeeMachine machine : machines) {
            System.out.printf("ID: %d, Water: %d ml, Milk: %d ml, Coffee Beans: %d g, Cups: %d, Money: $%.2f\n",
                    machine.getId(), machine.getWater(), machine.getMilk(), machine.getCoffeeBeans(), machine.getCups(), machine.getMoney());
        }
    }

    // Update a coffee machine's details
    public static void updateCoffeeMachine() {
        System.out.println("Enter the ID of the coffee machine to update:");
        int id = sc.nextInt();

        System.out.println("Enter new details (water, milk, coffeeBeans, cups, money):");
        int water = sc.nextInt();
        int milk = sc.nextInt();
        int coffeeBeans = sc.nextInt();
        int cups = sc.nextInt();
        float money = sc.nextFloat();

        dbManager.updateCoffeeMachine(id, water, milk, coffeeBeans, cups, money);
    }

    // Delete a coffee machine from the database
    public static void deleteCoffeeMachine() {
        System.out.println("Enter the ID of the coffee machine to delete:");
        int id = sc.nextInt();
        dbManager.deleteCoffeeMachine(id);
    }

    // Activate a specific coffee machine (Placeholder for actual activation)
    public static void activateCoffeeMachine() {
        System.out.print("Enter the ID of the coffee machine to activate: ");
        int id = sc.nextInt();

        // Check if the machine exists in the database
        CoffeeMachine machine = dbManager.getCoffeeMachineById(id);  // Fetch the coffee machine by ID.

        if (machine == null) {
            System.out.println("Coffee machine with ID " + id + " does not exist.");
            return;
        }

        // Activate the coffee machine and open the console interface for that machine
        System.out.println("Activating Coffee Machine ID: " + id);
        CoffeeMachineConsole console = new CoffeeMachineConsole(id);  // Pass the selected machine ID to the console
        console.run();  // Run the console interface for the selected machine
    }


}
