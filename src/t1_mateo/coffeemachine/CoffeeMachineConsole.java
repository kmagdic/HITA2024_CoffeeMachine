package t1_mateo.coffeemachine;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachineConsole {

    Scanner sc = new Scanner(System.in);

    public static void main(String[] args)  {

        Connection connection = makeDBConnection("docs/CoffeeMachineMateo");
        TransactionLogRepository transactionLogRepository = new TransactionLogRepository(connection);
        CoffeeTypeRepository coffeeTypeRepository = new CoffeeTypeRepository(connection);
        coffeeTypeRepository.createTable();
        transactionLogRepository.createTableTransactionLog();

        List<CoffeeType> coffeeTypes = coffeeTypeRepository.getAllCoffeeTypes();

        CoffeeMachineConsole console = new CoffeeMachineConsole(transactionLogRepository, coffeeTypes);
        console.run();
    }

    public static Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:c:/" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private TransactionLogRepository transactionLogRepository;
    CoffeeMachine machine = new CoffeeMachineWithStatusInFile(400, 540, 120, 9, 550);

    public CoffeeMachineConsole(TransactionLogRepository transactionLogRepository, List<CoffeeType> coffeeTypes) {
        this.transactionLogRepository = transactionLogRepository;
        machine.setCoffeeTypes(coffeeTypes);
    }

    void run() {

        System.out.println("Welcome to Coffee Machine 2.0 version by Mateo");
        boolean startedSuccessfully = machine.start();

        if(!startedSuccessfully) {
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
        for (int i = 0; i < machine.getCoffeeTypes().size(); i++) {
            System.out.println((i + 1) + " - " + machine.getCoffeeTypes().get(i).getName());
        }
        System.out.println("Enter your choice: ");

        int choice = sc.nextInt();
        if (choice <= machine.getCoffeeTypes().size()) {
            CoffeeType chosenCoffeeType = machine.getCoffeeTypes().get(choice - 1);
            String msg = machine.buyCoffee(chosenCoffeeType);
            System.out.println(msg);

            String action = msg.contains("not enough") ? "Not bought" : "Bought";
            String missingIngredients = msg.contains("not enough") ? machine.calculateWhichIngredientIsMissing(chosenCoffeeType) : null;

            TransactionLog log = new TransactionLog(chosenCoffeeType.getName(), action, missingIngredients);

            transactionLogRepository.insertLog(log, chosenCoffeeType);

        } else {
            System.out.println("Wrong enter\n");
        }
    }

    private void adminMenu(CoffeeMachine machine) {
        String ch = "";
        while (!ch.equals("exit")) {
            System.out.println(" ");
            System.out.println("Write action (fill, remaining, take, password, log, exit):");
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
                    int cup = sc.nextInt();
                    machine.fill(water, milk, coffeeBeans, cup);
                    break;

                case "take":
                    double amount = machine.takeMoney();
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
                    while (true) {
                        System.out.println("Enter new admin password: ");
                        String password = sc.next();
                        if (machine.checkPassword(password)) {
                            machine.changePassword(password);
                            System.out.println("Password is changed!");
                            break;
                        } else {
                            System.out.println("Please enter stronger password! It has to be a least 7 characters and it needs has at least one number.");
                        }
                    }
                    break;

                case "log":
                    System.out.println("Transaction log:");
                    for (TransactionLog log : transactionLogRepository.getLog()) {
                        System.out.println(log);
                    }
                    break;

                case "exit":
                    break;

            }
        }
    }



}