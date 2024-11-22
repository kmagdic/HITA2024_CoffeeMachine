package t4_vedran.coffeemachine;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static t4_vedran.coffeemachine.CoffeeMachineConsole.sc;

public class CoffeeMachine {

    protected int water;
    protected int milk;
    protected int coffeeBeans;
    protected int cups;
    protected float money;
    private final List<CoffeeType> coffeeTypes = new ArrayList<>();

    protected String adminUsername = "admin";
    protected static String adminPassword = "admin12345";
    protected String statusFileName = "docs/coffee_machine_status.txt";

    // Lista za transakcije
    private final List<Transaction> transactionLog = new ArrayList<>();

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        coffeeTypes.add(new CoffeeType("Espresso", 350, 0,16,4));
        coffeeTypes.add(new CoffeeType("Latte",350, 75,20,7));
        coffeeTypes.add(new CoffeeType("Capuccino",200, 100,12,6));
    }

    public List<CoffeeType> getCoffeeTypes() {
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

    public boolean hasEnoughResources(CoffeeType coffeeType) {
        return water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1;
    }

    public String buyCoffee(CoffeeType coffeeType) {
        String message;
        if (hasEnoughResources(coffeeType)) {
            // Oduzmi resurse
            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.cups -= 1;
            this.money += coffeeType.getPrice();

            // Spremi transakciju
            recordTransaction(coffeeType, "Bought");

            message = "I have enough resources, making you " + coffeeType.getName() + "\n";
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);

            // Spremi transakciju kao neuspješnu
            recordTransaction(coffeeType, "Not bought, no enough ingredients: " + missing);

            message = "Sorry, not enough " + missing + "\n";
        }
        return message;
    }

    public float takeMoney() {
        float moneyReturn = money;
        money = 0;
        return moneyReturn;
    }

    public String calculateWhichIngredientIsMissing(CoffeeType coffeeType) {
        String ingredientMissing = null;
        if (water < coffeeType.getWaterNeeded()) {
            ingredientMissing = "water";
        } else if (milk < coffeeType.getMilkNeeded()) {
            ingredientMissing = "milk";
        } else if (coffeeBeans < coffeeType.getCoffeeBeansNeeded()) {
            ingredientMissing = "coffee beans";
        } else if (cups < 1) {
            ingredientMissing = "cups";
        }
        return ingredientMissing;
    }

    public void fill(int water, int milk, int coffeeBeans, int cups) {
        this.water += water;
        this.milk += milk;
        this.coffeeBeans += coffeeBeans;
        this.cups += cups;
    }

    public boolean login(String username, String password) {
        return adminUsername.equals(username) && adminPassword.equals(password);
    }


    public void stop() {
    }

    public boolean start() {
        return true;
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

    // Funkcija za promjenu lozinke
    static void changePassword() {
        sc.nextLine();
        System.out.println("Enter new admin password: ");
        String newPassword = sc.nextLine();

        while (!isValidPassword(newPassword)) {
            System.out.println("Please enter stronger password! It has to be at least 7 characters and it needs to have at least one number.");
            System.out.print("Enter new admin password:\n");
            newPassword = sc.nextLine();
        }

        adminPassword = newPassword;
        System.out.println("Password is changed.");
    }

    private static boolean isValidPassword(String password) {
        if (password.length() <= 7) {
            return false;
        }

        boolean hasNumber = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
                break;
            }
        }

        return hasNumber;
    }

    // Funkcija za spremanje transakcija u log
    private void recordTransaction(CoffeeType coffeeType, String action) {
        String currentDateTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
        transactionLog.add(new Transaction(currentDateTime, coffeeType.getName(), action));
    }

    // Funkcija za ispis transakcija
    public void printTransactionLog() {
        System.out.println("Transaction log:");
        for (Transaction transaction : transactionLog) {
            System.out.println(transaction);
        }
    }
}
