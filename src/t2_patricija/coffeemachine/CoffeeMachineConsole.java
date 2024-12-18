package t2_patricija.coffeemachine;

//import _karlo_dragan.coffeemachine.CoffeeMachine;
//import _karlo_dragan.coffeemachine.CoffeeType;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class CoffeeMachineConsole {

    Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {

        CoffeeMachineConsole console = new CoffeeMachineConsole();

        DbClient db = DbClient.getInstance();

        console.run();
    }

    CoffeeTypeDAO coffeeTypeDAO = new CoffeeTypeDAO(DbClient.getInstance().getDataSource());

    void run() {
        CoffeeMachine machine = new CoffeeMachineWithStatusInFile(400, 540, 120, 9, 550);
        System.out.println("Welcome to Coffee Machine 2.0 version by Patricija");
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
        List<CoffeeType> coffeeTypes = coffeeTypeDAO.findAll();

        for (int i = 0; i < coffeeTypes.size(); i++) {
            System.out.println((i + 1) + " - " + coffeeTypes.get(i).getName());
        }
        System.out.println("Enter your choice: ");

        int typeOfCoffeeChoice = sc.nextInt();
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
            System.out.println("Write action (fill, remaining, take, password, log, add_coffee, exit):");
            ch = sc.next();

            switch (ch) {
                case "fill":
                    System.out.println("Write how many ml of water you want to add:");
                    int water = sc.nextInt();
                    System.out.println("Write how many ml of milk you want to add:");
                    int milk = sc.nextInt();
                    System.out.println("Write how many grams of coffee beans you want to add:");
                    int coffeeBeans = sc.nextInt();
                    System.out.println("\"Write how many disposable cups you want to add: ");
                    int cup = sc.nextInt();
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
                    System.out.println(machine.getCoffeeBeans() + " g of coffee beans");
                    System.out.println(machine.getCups() + " cups");
                    System.out.println("$" + machine.getMoney() + " of money");
                    break;

                case "password":
                    System.out.println("Enter new admin password:");
                    String password = sc.next();
                    boolean containsNumber = password.matches(".*\\d.*");
                    if (password.length() < 7 && !(containsNumber)) {
                        System.out.println("Please enter stronger password! It has to be a least 7 characters and it needs has at least one number.");
                    } else {
                        machine.setAdminPassword(password);
                        System.out.println("Password is changed");
                    }
                    break;

                case "log":
                    for (TransactionLog h : machine.transactionLogDao.findAll()) {
                        System.out.println(h);
                    }
                    break;

                case "add_coffee":
                    System.out.println("Enter coffee name: ");
                    String name = sc.next();
                    System.out.println("Enter water amount: ");
                    int needed_water = sc.nextInt();
                    System.out.println("Enter milk amount: ");
                    int needed_milk = sc.nextInt();
                    System.out.println("Enter coffee beans amount: ");
                    int coffee_beans = sc.nextInt();
                    System.out.println("Enter price: ");
                    int price = sc.nextInt();

                    CoffeeType coffeeType = new CoffeeType(name, needed_water, needed_milk, coffee_beans, price);
                    if (coffeeTypeDAO.exists(coffeeType)) {
                        System.out.println("This coffee type already exists.");
                    } else {
                        coffeeTypeDAO.add(coffeeType);
                        System.out.println("Coffee type added successfully!");
                    }
                    break;

                case "exit":
                    break;

            }
        }
    }
}