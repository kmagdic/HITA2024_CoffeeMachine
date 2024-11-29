package t2_tomislav.coffeemachine;

import java.util.List;
import java.util.Scanner;

public class CoffeeMachineConsole {

    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    void run() {
        CoffeeMachineWithStatusInFile machine = new CoffeeMachineWithStatusInFile(
                400, 540, 120, 9, 550, "docs/coffee_machine_status.txt"
        );

        System.out.println("Welcome to Coffee Machine 2.1 version by Tomislav");

        boolean startedSuccessfully = machine.loadFromFile();
        if (!startedSuccessfully) {
            System.out.println("Coffee machine started but without file. Using default values.");
        }

        String action = "";

        while (!action.equals("exit")) {
            System.out.println("Write action (buy, login, log, exit): ");
            action = sc.next();

            switch (action) {
                case "buy":
                    buyAction(machine);
                    break;

                case "login":
                    loginAction(machine);
                    break;

                case "log":
                    printTransactionLogs(machine);
                    break;

                case "exit":
                    machine.saveToFile();
                    System.out.println("Shutting down the machine. Bye!");
                    break;

                default:
                    System.out.println("Invalid action. Please try again.");
            }
        }
    }

    private void buyAction(CoffeeMachine machine) {
        System.out.println("Choose a coffee:");
        CoffeeType[] coffeeTypes = machine.getCoffeeTypes();
        for (int i = 0; i < coffeeTypes.length; i++) {
            System.out.println((i + 1) + " - " + coffeeTypes[i].getName());
        }
        System.out.println("Enter your choice:");

        int typeOfCoffeeChoice = getValidIntegerInput();
        if (typeOfCoffeeChoice >= 1 && typeOfCoffeeChoice <= coffeeTypes.length) {
            String msg = machine.buyCoffee(coffeeTypes[typeOfCoffeeChoice - 1]);
            System.out.println(msg);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private void loginAction(CoffeeMachine machine) {
        System.out.println("Enter username: ");
        String username = sc.next();
        System.out.println("Enter password: ");
        String password = sc.next();

        if (machine.login(username, password)) {
            System.out.println("Login successful. Welcome, admin!");
            adminMenu(machine);
        } else {
            System.out.println("Wrong username or password.");
        }
    }

    private void adminMenu(CoffeeMachine machine) {
        String action = "";
        while (!action.equals("exit")) {
            System.out.println("Admin actions: (fill, remaining, take, password, log, exit):");
            action = sc.next();

            switch (action) {
                case "fill":
                    fillMachine(machine);
                    break;

                case "remaining":
                    System.out.println(machine.toString());
                    break;

                case "take":
                    float amount = machine.takeMoney();
                    System.out.println("I gave you $" + amount);
                    break;

                case "password":
                    changePassword(machine);
                    break;

                case "log":
                    printTransactionLogs(machine);
                    break;

                case "exit":
                    System.out.println("Exiting admin menu.");
                    break;

                default:
                    System.out.println("Invalid action. Please try again.");
            }
        }
    }

    private void fillMachine(CoffeeMachine machine) {
        System.out.println("Enter how many ml of water you want to add:");
        int water = getValidIntegerInput();
        System.out.println("Enter how many ml of milk you want to add:");
        int milk = getValidIntegerInput();
        System.out.println("Enter how many grams of coffee beans you want to add:");
        int coffeeBeans = getValidIntegerInput();
        System.out.println("Enter how many disposable cups you want to add:");
        int cups = getValidIntegerInput();
        machine.fill(water, milk, coffeeBeans, cups);
        System.out.println("Machine refilled successfully.");
    }

    private void changePassword(CoffeeMachine machine) {
        System.out.println("Enter new admin password:");
        String newPassword = sc.next();
        if (machine.changeAdminPassword(newPassword)) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Password must be at least 7 characters long and contain at least one number.");
        }
    }

    private void printTransactionLogs(CoffeeMachine machine) {
        System.out.println("Choose log type: (1 - Local, 2 - Database)");
        int choice = getValidIntegerInput();

        if (choice == 1) {
            List<TransactionLog> logs = machine.getHistoryLogList();
            if (logs.isEmpty()) {
                System.out.println("No local logs available.");
            } else {
                System.out.println("Local Transaction Logs:");
                for (TransactionLog log : logs) {
                    System.out.println(log);
                }
            }
        } else if (choice == 2) {
            H2DatabaseManager dbManager = new H2DatabaseManager("C:/Tomislav/coffee_machine");
            List<TransactionLog> logs = dbManager.getTransactionLogs();
            if (logs.isEmpty()) {
                System.out.println("No database logs available.");
            } else {
                System.out.println("Database Transaction Logs:");
                for (TransactionLog log : logs) {
                    System.out.println(log);
                }
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private int getValidIntegerInput() {
        while (true) {
            try {
                return sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
            }
        }
    }
}
