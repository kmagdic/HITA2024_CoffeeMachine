package t4_martin.coffeemachine;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CoffeeMachine {

    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private float money;
    private CoffeeType[] coffeeTypes = new CoffeeType[3];

    private String adminUsername = "admin";
    private String adminPassword = "admin12345";
    private String statusFileName = "src/t4_martin/docs/coffee_machine_status.txt";

    public CoffeeMachine(){}


    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        coffeeTypes[0] = new CoffeeType("Espresso", 350, 0,16,4);
        coffeeTypes[1] = new CoffeeType("Latte",350, 75,20,7);
        coffeeTypes[2] = new CoffeeType("Capuccino",200, 100,12,6);

        logRepository.createTable();
    }

    public CoffeeType[] getCoffeeTypes() {
        return coffeeTypes;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public int getCups() {
        return cups;
    }

    public double getMoney() {
        return money;
    }




    Connection connection = makeDBConnection("src/t4_martin/docs/log");
    LogRepository logRepository = new LogRepository(connection);


    public static Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






    public boolean hasEnoughResources(CoffeeType coffeeType){
        if (water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1) {
            return true;
        } else
            return false;
    }

    public String buyCoffee(CoffeeType coffeeType){
        if (hasEnoughResources(coffeeType)) {
            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.cups -= 1;
            this.money += coffeeType.getPrice();
            TransactionLog transactionLog = new TransactionLog(formattedNow, coffeeType.getName(),"Bought",coffeeType.getPrice());
            logRepository.insertLog(transactionLog);
            return "I have enough resources, making you " + coffeeType.getName() + "\n";
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);
            TransactionLog transactionLog = new TransactionLog(formattedNow, coffeeType.getName(),"Not bought, missing" + missing,0);
            logRepository.insertLog(transactionLog);
            return "Sorry, not enough " + missing + "\n";
        }
    }

    public float takeMoney(){
        float moneyReturn = money;
        money = 0;
        return moneyReturn;
    }

    public String calculateWhichIngredientIsMissing(CoffeeType coffeeType){
        String ingredientMissing = null;
        if (water < coffeeType.getWaterNeeded()) {
            ingredientMissing = "water";
        }
        else if (milk < coffeeType.getMilkNeeded()) {
            ingredientMissing = "milk" ;
        }
        else if (coffeeBeans < coffeeType.getCoffeeBeansNeeded()) {
            ingredientMissing = "coffee beans" ;
        }
        else if (cups < 1) {
            ingredientMissing = "cups" ;
        }
        return ingredientMissing;
    }

    public void fill(int water, int milk, int coffeeBeans, int cups){
        this.water += water;
        this.milk += milk;
        this.coffeeBeans += coffeeBeans;
        this.cups += cups;
    }

    public boolean login(String username, String password) {
        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            return true;
        } else
            return false;
    }


    public boolean loadFromFile(String fileName)  {
        FileReader reader = null;

        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            return false;
        }

        Scanner fileScanner = new Scanner(reader);

        // FILE format:
        // <water_status>; <milk_status>; <coffee_beans_status>; <cups_status>; <money_status>
        // <admin_username>; <admin_password>

        fileScanner.useDelimiter("; |\n"); // delimiter is "; " or "\n" (for the last value)

        water = fileScanner.nextInt();
        milk = fileScanner.nextInt();
        coffeeBeans = fileScanner.nextInt();
        cups = fileScanner.nextInt();
        money = Float.parseFloat(fileScanner.next());

        adminUsername = fileScanner.next();
        adminPassword = (fileScanner.next()).trim();

        return true;


    }

    public void saveToFile(String fileName){
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write(water + "; " +  milk + "; " + coffeeBeans + "; " + cups + "; " + money);
            writer.write("\n");
            writer.write(adminUsername + "; " + adminPassword);
            writer.write("\n");

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean start() {
        return loadFromFile(statusFileName);
    }

    public void stop() {
        saveToFile(statusFileName);
    }

    @Override
    public String toString() {
        return "CoffeeMachine{" +
                "water=" + water +
                ", milk=" + milk +
                ", coffeeBeans=" + coffeeBeans +
                ", cups=" + cups +
                ", money=" + money +
                '}';
    }

    public void changePassword(String password) {
        if (isGoodPassword(password)) {
            adminPassword = password;
            System.out.println("Password is changed");

        } else
            System.out.println("Please enter stronger password! It has to be a least 7 characters and it needs has at least one number.");

    }


    private boolean isGoodPassword(String password){
        if (password.length() > 7 && password.matches(".*\\d.*")){
            return true;
        }else return false;
    }

    private LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedNow = now.format(formatter);


    public void printLog(){

        for(TransactionLog t: logRepository.getList()){
            System.out.println(t.toString());
        }
    }





}