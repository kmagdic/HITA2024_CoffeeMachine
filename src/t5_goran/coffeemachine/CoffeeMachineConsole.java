package t5_goran.coffeemachine;


//import _karlo_dragan.coffeemachine.CoffeeMachine;
//import _karlo_dragan.coffeemachine.CoffeeType;

import java.util.ArrayList;
import java.util.Scanner;

public class CoffeeMachineConsole {

    Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    void run() {
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        System.out.println("Welcome to Coffee Machine 1.0 version by Karlo");
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
                    login(machine);
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

    private void login(CoffeeMachine machine) {
        System.out.println("Enter username: ");
        String username = sc.next();
        System.out.println("Enter password: ");
        String password = sc.next();

        if (machine.login(username, password)) {
            adminMenu(machine);
        } else {
            System.out.println("Wrong username or password\n");
        }
    }

    private void buyAction(CoffeeMachine machine) {
        System.out.println("Choice: ");
        ArrayList<CoffeeType> coffeeTypes = machine.getCoffeeTypes();
        for (int i = 0; i < coffeeTypes.size(); i++) {
            System.out.println((i + 1) + " - " + coffeeTypes.get(i).getName());
        }
        System.out.println("Enter your choice: ");

        int typeOfCoffeeChoice = sc.nextInt();
        if (typeOfCoffeeChoice > 0 && typeOfCoffeeChoice <= coffeeTypes.size()) {
            String msg = machine.buyCoffee(coffeeTypes.get(typeOfCoffeeChoice - 1));
            System.out.println(msg);
        } else {
            System.out.println("Wrong enter\n");
        }
    }


    private void adminMenu(CoffeeMachine machine) {
        String ch = "";
        while (!ch.equals("exit")) {
            System.out.println(" ");
            System.out.println("Write action (fill, remaining, take, password, exit):");
            ch = sc.next();

            switch (ch) {
                case "fill":
                    fill(machine);
                    break;

                case "take":
                    take(machine);
                    break;

                case "remaining":
                    remaining(machine);
                    break;

                case "password":
                    changeAdminPassword(machine);
                    break;

                case "exit":
                    System.out.println("Exiting admin menu.");
                    break;

                default:
                    System.out.println("No such option");
            }
        }
    }

    private void fill(CoffeeMachine machine) {
        System.out.println("Write how many ml of water you want to add:");
        int water = sc.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        int milk = sc.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        int coffeeBeans = sc.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        int cups = sc.nextInt();
        machine.fill(water, milk, coffeeBeans, cups);
    }

    private static void take(CoffeeMachine machine) {
        float amount = machine.takeMoney();
        System.out.println("I gave you $" + amount + "\n");
    }

    private static void remaining(CoffeeMachine machine) {
        System.out.println("The coffee machine has:");
        System.out.println(machine.getWater() + " ml of water");
        System.out.println(machine.getMilk() + " ml of milk");
        System.out.println(machine.getCoffeeBeans() + " g of coffee beans");
        System.out.println(machine.getCups() + " cups");
        System.out.println("$" + machine.getMoney() + " of money");
    }

    private void changeAdminPassword(CoffeeMachine machine) {
        System.out.println("Enter new admin password:");
        String newPassword = sc.next();
        while (!machine.changePassword(newPassword)) {
            System.out.println("Please enter stronger password! It has to be at least 7 characters and it needs to have at least one number.");
            newPassword = sc.next();
        }
        System.out.println("Password is changed\n");
    }

}