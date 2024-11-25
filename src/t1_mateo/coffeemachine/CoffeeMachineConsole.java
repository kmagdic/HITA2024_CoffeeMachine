package t1_mateo.coffeemachine;

import zadatak2.booklibrary.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoffeeMachineConsole {

    Scanner sc = new Scanner(System.in);
    static Connection connection;

    public static void main(String[] args)  {
        connection = makeDBConnection("docs/CoffeeMachineMateo");
        createTable();
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    public static Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:c:/" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTable() {
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS transaction_log (" +
                    "date datetime  NOT NULL, " +
                    "coffee_type text  NOT NULL, " +
                    "action text NOT NULL, " +
                    "ingredients text NULL)";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void insertLog(TransactionLog log) {

        String insertSql = "INSERT INTO transaction_log (date, coffee_type, action, ingredients) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setTimestamp(1, log.getDate());
            ps.setString(2, log.getCoffeeType());
            ps.setString(3, log.getAction());
            ps.setString(4, log.getIngredients());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TransactionLog> getLog() {
        String sqlPrint = "SELECT * FROM transaction_log";
        List<TransactionLog> resultList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);
            while (rs.next()) {
                TransactionLog log = new TransactionLog();
                log.setDate(rs.getTimestamp("date").toLocalDateTime());
                log.setCoffeeType(rs.getString("coffee_type"));
                log.setAction(rs.getString("action"));
                log.setIngredients(rs.getString("ingredients"));

                resultList.add(log);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    void run() {
        CoffeeMachine machine = new CoffeeMachineWithStatusInFile(400, 540, 120, 9, 550);
        System.out.println("Welcome to Coffee Machine 1.0 version by Mateo");
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
                    for (TransactionLog log : machine.getTransactionLogList()) {
                        insertLog(log);
                    }
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

                case "log":
                    System.out.println("Transaction log:");

                    for (TransactionLog log: getLog()) {
                        System.out.println(log);
                    }
                    break;

                case "exit":
                    break;

            }
        }
    }



}