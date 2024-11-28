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

    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        this.coffeeTypes.addAll(CoffeeTypeDB.getAllCoffeeTypes());

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

    private void recordTransaction(CoffeeType coffeeType, String action) {
        String currentDateTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());

        // Spremi transakciju u bazu podataka
        TransactionDB.saveTransaction(currentDateTime, coffeeType.getName(), action);
    }
    public void addCoffeeType(String name, int waterNeeded, int milkNeeded, int coffeeBeansNeeded, int price) {
        // Provjerava da li kava sa upisanim imenom već postoji
        boolean coffeeExists = CoffeeTypeDB.getAllCoffeeTypes()
                .stream()
                .anyMatch(coffee -> coffee.getName().equalsIgnoreCase(name));

        if (coffeeExists) {
            System.out.println("Coffee type with the name '" + name + "' already exists in the database.");
            return;
        }

        // Ako kava ne postoji, dodaje novu kavu
        CoffeeTypeDB.addCoffeeType(name, waterNeeded, milkNeeded, coffeeBeansNeeded, price);
        this.coffeeTypes.clear();
        this.coffeeTypes.addAll(CoffeeTypeDB.getAllCoffeeTypes());
        System.out.println("Coffee type added successfully.");
    }

    public void deleteCoffeeType(String name) {
        if (CoffeeTypeDB.deleteCoffeeType(name)) {
            this.coffeeTypes.clear();
            this.coffeeTypes.addAll(CoffeeTypeDB.getAllCoffeeTypes());
            System.out.println("Coffee type deleted successfully.");
        } else {
            System.out.println("No coffee type with the name '" + name + "' found.");
        }
    }

    public void updateCoffeeType(String name, int waterNeeded, int milkNeeded, int coffeeBeansNeeded, int price) {
        if (CoffeeTypeDB.updateCoffeeType(name, waterNeeded, milkNeeded, coffeeBeansNeeded, price)) {
            this.coffeeTypes.clear();
            this.coffeeTypes.addAll(CoffeeTypeDB.getAllCoffeeTypes());
            System.out.println("Coffee type updated successfully.");
        } else {
            System.out.println("No coffee type with the name '" + name + "' found.");
        }
    }
}
