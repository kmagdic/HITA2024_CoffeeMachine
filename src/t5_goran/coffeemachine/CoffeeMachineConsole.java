package t5_goran.coffeemachine;

import java.util.List;
import java.util.Scanner;

public class CoffeeMachineConsole {

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    // Main method to handle user interactions
    void run() {
        DataConnection dataConnection = DataConnection.getInstance();
        CoffeeMachineWithDatabase machine = new CoffeeMachineWithDatabase(400, 540, 120, 9, 550, dataConnection.getConnection());
        machine.start();    // Start the coffee machine with database

        System.out.println("Welcome to Coffee Machine 1.0 version by Goran");

        String action = "";
        while (!action.equals("exit")) {
            System.out.println("Write action (buy, login, exit): ");
            action = scanner.next();

            switch (action) {
                case "buy":
                    buyAction(machine);
                    break;
                case "login":
                    adminMenu(machine);
                    break;
                case "exit":
                    machine.stop();         // Stop the coffee machine and save data to the database
                    System.out.println("Shutting down the machine. Bye!");
                    break;
                default:
                    System.out.println("No such option");
            }
        }
    }

    private void buyAction(CoffeeMachine machine) {
        System.out.println("Choice: ");
        List<CoffeeType> coffeeTypes = machine.getCoffeeTypes();
        // Display available coffee types
        for (int i = 0; i < coffeeTypes.size(); i++) {
            System.out.println((i + 1) + " - " + coffeeTypes.get(i).getName());
        }
        System.out.println("Enter your choice: ");

        int choice = scanner.nextInt();
        if (choice > 0 && choice <= coffeeTypes.size()) {
            String message = machine.buyCoffee(coffeeTypes.get(choice - 1));
            System.out.println(message);
        } else {
            System.out.println("Invalid choice");
        }
    }

    private void adminMenu(CoffeeMachine machine) {
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter password:");
        String password = scanner.next();

        if (!machine.login(username, password)) {
            System.out.println("Invalid login credentials!");
            return;
        }

        String adminAction = "";
        while (!adminAction.equals("exit")) {
            System.out.println("Write action (fill, remaining, take, password, log, exit):");
            adminAction = scanner.next();
            switch (adminAction) {
                case "fill":
                    fillMachine(machine);
                    break;
                case "remaining":
                    remaining(machine);
                    break;
                case "take":
                    System.out.println("I gave you $" + machine.takeMoney());
                    break;
                case "log":
                    viewTransactionLogs();
                    break;
                case "password":
                    changePassword(machine);
                    break;
                case "exit":
                    System.out.println("Exiting admin menu.");
                    break;
                default:
                    System.out.println("Invalid admin action!");
            }
        }
    }

    private void fillMachine(CoffeeMachine machine) {
        System.out.println("Write how many ml of water you want to add:");
        int water = scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        int milk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        int coffeeBeans = scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        int cups = scanner.nextInt();

        machine.fill(water, milk, coffeeBeans, cups);
    }

    private void remaining(CoffeeMachine machine) {
        System.out.println("The coffee machine has:");
        System.out.println(machine.getWater() + " ml of water");
        System.out.println(machine.getMilk() + " ml of milk");
        System.out.println(machine.getCoffeeBeans() + " g of coffee beans");
        System.out.println(machine.getCups() + " disposable cups");
        System.out.println("$" + machine.getMoney() + " of money");
    }

    private void viewTransactionLogs() {
        TransactionLogRepository logRepo = new TransactionLogRepository(DataConnection.getInstance().getConnection());
        List<TransactionLog> logs = logRepo.getAllTransactions();

        System.out.println("Transaction Logs:");
        for (TransactionLog log : logs) {
            System.out.println(log.toFormattedString());
        }
    }

    private void changePassword(CoffeeMachine machine) {
        boolean isPasswordChanged = false;

        while (!isPasswordChanged) {
            System.out.println("Enter new admin password:");
            String newPassword = scanner.next();
            if (machine.changePassword(newPassword)) {
                isPasswordChanged = true;
            } else {
                System.out.println("Please enter a stronger password! It must be at least 7 characters long and include at least one number.");
            }
        }
    }
}
