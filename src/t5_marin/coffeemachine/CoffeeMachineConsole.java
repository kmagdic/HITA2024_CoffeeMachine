package t5_marin.coffeemachine;

import t5_marin.coffeemachine.CoffeeMachine;
import t5_marin.coffeemachine.CoffeeType;

import java.util.Scanner;

public class CoffeeMachineConsole {

    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    void run() {
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        System.out.println("Welcome to Coffee Machine 2.0");
        boolean startedSuccessfully = machine.start();

        if (!startedSuccessfully) {
            System.out.println("Coffee machine started but without file. Using default values.");
        }

        String action = "";

        while (!action.equals("exit")) {
            System.out.println("Write action (buy, login, exit): ");
            action = sc.next();
            switch (action) {
                case "buy":
                    buyAction(machine);
                    break;

                case "login":
                    System.out.println("Enter username: ");
                    String username = sc.next();
                    System.out.println("Enter password: ");
                    String password = sc.next();

                    if (machine.login(username, password)) {
                        adminMenu(machine);
                    } else {
                        System.out.println("Wrong username or password\n");
                    }
                    break;

                case "exit":
                    machine.stop();
                    System.out.println("Shutting down the machine. Bye!");
                    break;

                default:
                    System.out.println("No such option");
            }
        }
    }

    private void buyAction(CoffeeMachine machine) {
        System.out.println("Choice: ");
        CoffeeType[] coffeeTypes = machine.getCoffeeTypes();
        for (int i = 0; i < coffeeTypes.length; i++) {
            System.out.println((i + 1) + " - " + coffeeTypes[i].getName());
        }
        System.out.println("Enter your choice: ");

        int typeOfCoffeeChoice = sc.nextInt();
        if (typeOfCoffeeChoice <= coffeeTypes.length) {
            String msg = machine.buyCoffee(coffeeTypes[typeOfCoffeeChoice - 1]);
            System.out.println(msg);
        } else {
            System.out.println("Wrong choice\n");
        }
    }

    private void adminMenu(CoffeeMachine machine) {
        String ch = "";
        while (!ch.equals("exit")) {
            System.out.println("\nWrite action (fill, remaining, take, password, exit):");
            ch = sc.next();

            switch (ch) {
                case "fill":
                    System.out.println("Write how many ml of water you want to add:");
                    int water = sc.nextInt();
                    System.out.println("Write how many ml of milk you want to add:");
                    int milk = sc.nextInt();
                    System.out.println("Write how many grams of coffee beans you want to add:");
                    int coffeeBeans = sc.nextInt();
                    System.out.println("Write how many disposable cups you want to add: ");
                    int cups = sc.nextInt();
                    machine.fill(water, milk, coffeeBeans, cups);
                    break;

                case "take":
                    float amount = machine.takeMoney();
                    System.out.println("I gave you $" + amount + "\n");
                    break;

                case "remaining":
                    System.out.println("The coffee machine has:");
                    System.out.println(machine.getWater() + " ml of water");
                    System.out.println(machine.getMilk() + " ml of milk");
                    System.out.println(machine.getCoffeeBeans() + " g of coffee beans");
                    System.out.println(machine.getCups() + " cups");
                    System.out.println("$" + machine.getMoney() + " of money");
                    break;

                case "password":
                    String newPassword = "";
                    boolean validPassword = false;
                    while (!validPassword) {
                        System.out.println("Enter new admin password:");
                        newPassword = sc.next();
                        if (newPassword.length() >= 7 && newPassword.matches(".*\\d.*")) {
                            machine.changePassword(newPassword);
                            System.out.println("Password is changed");
                            validPassword = true;
                        } else {
                            System.out.println("Please enter stronger password! It has to be at least 7 characters and it needs to have at least one number.");
                        }
                    }
                    break;

                case "exit":
                    break;

                default:
                    System.out.println("No such option");
            }
        }
    }
}
