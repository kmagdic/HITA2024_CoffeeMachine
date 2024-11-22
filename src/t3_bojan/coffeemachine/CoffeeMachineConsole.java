package t3_bojan.coffeemachine;

import java.util.List;
import java.util.Scanner;

public class CoffeeMachineConsole {

    private Scanner scanner = new Scanner(System.in);
    private final String  ADMIN_PASSWORD_CHANGE_MESSAGE = "Enter new admin password:";
    private final String  AdMIN_PASSWORD_CHANGED = "Password is changed";
    private final String  ADMIN_PASSWORD_CHANGE_MESSAGE_ERROR = "Please enter stronger password! It has to be a least 7 characters and it needs has at least one number.";


    public static void main(String[] args)  {
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    void run() {
        CoffeeMachine machine = new CoffeeMachineWithStatusInFile(400, 540, 120, 9, 550);
        System.out.println("Welcome to Coffee Machine 1.0 version by Bojan");
        boolean startedSuccessfully = machine.start();

        if(!startedSuccessfully) {
            System.out.println("Coffee machine started but without file. Using default values.");
        }

        String action = "";

        while (!action.equals("exit")) {
            System.out.println("Write action (buy, login, exit): ");
            action = scanner.next();
            switch (action) {
                case "buy":
                    buyAction(machine);
                    break;

                case "login":
                    System.out.println("Enter username: ");
                    String username = scanner.next();
                    System.out.println("Enter password: ");
                    String password = scanner.next();

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

        List<CoffeeType> coffeeTypes = machine.getCoffeeTypes();

        for (CoffeeType coffeeType : coffeeTypes) {
            System.out.println(coffeeType.getName());
        }

        System.out.println("Enter your choice: ");

        int typeOfCoffeeChoice = scanner.nextInt();
        if (typeOfCoffeeChoice <= coffeeTypes.size()) {
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
            System.out.println("Write action (fill, remaining, take, password, log, exit):");
            ch = scanner.next();

            switch (ch) {
                case "fill":
                    System.out.println("Write how many ml of water you want to add:");
                    int water = scanner.nextInt();
                    System.out.println("Write how many ml of milk you want to add:");
                    int milk = scanner.nextInt();
                    System.out.println("Write how many grams of coffee beans you want to add:");
                    int coffeeBeans = scanner.nextInt();
                    System.out.println("\"Write how many disposable cups you want to add: ");
                    int cup = scanner.nextInt();
                    machine.fill(water, milk, coffeeBeans, cup);
                    break;

                case "take":
                    float amount = machine.takeMoney();
                    System.out.println("I gave you $" + amount + "\n");
                    break;

                case "remaining":
                    System.out.println("The coffee machine has:");
                    System.out.println(machine.getWater() + " ml of water");
                    System.out.println(machine.getMilk() + " ml of milk");
                    System.out.println(machine.getCoffeeBeans() + " g of water");
                    System.out.println(machine.getCups() + " cups");
                    System.out.println("$" + machine.getMoney() + " of money");
                    break;

                    case "password":
                        do {
                            System.out.println(ADMIN_PASSWORD_CHANGE_MESSAGE);
                            String newPassword = scanner.next();
                            if (newPassword.length() >= 7 && newPassword.matches(".*\\d.*")) {
                                machine.changePassword(newPassword);
                                System.out.println(AdMIN_PASSWORD_CHANGED);
                                break;
                            } else {
                                System.out.println(ADMIN_PASSWORD_CHANGE_MESSAGE_ERROR);
                            }
                        } while (true);
                        break;

                        case "log":
                            machine.showLog();
                            break;

                case "exit":
                    break;

            }
        }
    }



}