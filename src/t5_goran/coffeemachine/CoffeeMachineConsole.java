package t5_goran.coffeemachine;

import java.util.ArrayList;
import java.util.Scanner;

public class CoffeeMachineConsole {

    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DataManager.getInstance().createTable();
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    void run() {
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
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
                    System.out.println("Shutting down the machine. Bye!");
                    break;
                default:
                    System.out.println("No such option");
            }
        }
    }

    private void buyAction(CoffeeMachine machine) {
        System.out.println("Choice: ");
        ArrayList<CoffeeType> coffeeTypes = machine.getCoffeeTypes();
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
            System.out.println("Write action (fill, remaining, take, log, password, exit):");
            adminAction = scanner.next();

            switch (adminAction) {
                case "fill":
                    fillMachine(machine);
                    break;
                case "remaining":
                    System.out.println(machine.remaining());
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

    private void viewTransactionLogs() {
        System.out.println("Transaction Logs:");
        DataManager.getInstance().getTransactions().forEach(log -> System.out.println(log.toFormattedString()));
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

