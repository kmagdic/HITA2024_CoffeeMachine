package t2_tomislav.coffeemachine;

import java.util.ArrayList;
import java.util.List;

public class CoffeeMachine {

    // Privatni atributi koji predstavljaju resurse kafe aparata
    private int water; // Voda u mililitrima
    private int milk; // Mleko u mililitrima
    private int coffeeBeans; // Količina zrna kafe u gramima
    private int cups; // Broj dostupnih čaša
    private float money; // Novac u aparatu

    // Dostupne vrste kafe
    private final CoffeeType[] coffeeTypes = new CoffeeType[3];

    // Korisničko ime i lozinka za admin korisnika
    private String adminUsername = "admin";
    private String adminPassword = "admin12345";

    // Instanca za upravljanje bazom podataka
    private final H2DatabaseManager databaseManager = new H2DatabaseManager("C:/Tomislav/coffee_machine");

    // Lokalna lista transakcija za praćenje povijesti
    private final List<TransactionLog> transactionLogList = new ArrayList<>();

    // Konstruktor koji inicijalizira resurse aparata i vrste kafe
    public CoffeeMachine(int water, int milk, int coffeeBeans, int cups, float money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;

        // Definicija dostupnih vrsta kafe
        coffeeTypes[0] = new CoffeeType("Espresso", 250, 0, 16, 4);
        coffeeTypes[1] = new CoffeeType("Latte", 350, 75, 20, 7);
        coffeeTypes[2] = new CoffeeType("Cappuccino", 200, 100, 12, 6);
    }

    // GETTER i SETTER metode za pristup privatnim atributima
    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public int getCoffeeBeans() {
        return coffeeBeans;
    }

    public void setCoffeeBeans(int coffeeBeans) {
        this.coffeeBeans = coffeeBeans;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public CoffeeType[] getCoffeeTypes() {
        return coffeeTypes;
    }

    public List<TransactionLog> getHistoryLogList() {
        return transactionLogList;
    }

    // Metoda za prijavu admin korisnika
    public boolean login(String username, String password) {
        // Provjerava da li se uneseni podaci podudaraju sa admin podacima
        return adminUsername.equals(username) && adminPassword.equals(password);
    }

    // Metoda za promjenu admin lozinke
    public boolean changeAdminPassword(String newPassword) {
        // Provjerava da li nova lozinka ispunjava sigurnosne zahtjeve
        if (newPassword.length() >= 7 && newPassword.matches(".*\\d.*")) {
            adminPassword = newPassword;
            return true;
        } else {
            return false;
        }
    }

    // Metoda za kupovinu kafe
    public String buyCoffee(CoffeeType coffeeType) {
        boolean success = hasEnoughResources(coffeeType); // Provjera resursa
        float amount = success ? coffeeType.getPrice() : 0; // Cena kafe ako je uspješna kupovina
        String ingredient = success ? "" : calculateWhichIngredientIsMissing(coffeeType); // Sastojak koji nedostaje

        if (success) {
            // Ažuriranje resursa nakon uspješne kupovine
            water -= coffeeType.getWaterNeeded();
            milk -= coffeeType.getMilkNeeded();
            coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            cups--;
            money += coffeeType.getPrice();
        }

        // Dodavanje zapisa u lokalnu povijest
        addRecordToHistoryList(coffeeType.getName(), success ? "Bought" : "Failed", ingredient);

        // Spremanje transakcije u bazu podataka
        databaseManager.insertTransactionLog(new TransactionLog(coffeeType.getName(), success ? "Bought" : "Failed", ingredient));

        // Povratna poruka korisniku
        return success ? "I have enough resources, making you " + coffeeType.getName()
                : "Sorry, not enough " + ingredient;
    }

    // Metoda za dodavanje zapisa u lokalnu povijest
    public void addRecordToHistoryList(String coffeeType, String success, String ingredient) {
        TransactionLog log = new TransactionLog(coffeeType, success, ingredient);
        transactionLogList.add(log);
    }

    // Metoda za provjeru da li ima dovoljno resursa za odabranu kafu
    public boolean hasEnoughResources(CoffeeType coffeeType) {
        return water >= coffeeType.getWaterNeeded() &&
                milk >= coffeeType.getMilkNeeded() &&
                coffeeBeans >= coffeeType.getCoffeeBeansNeeded() &&
                cups >= 1;
    }

    // Metoda za izračunavanje sastojka koji nedostaje
    public String calculateWhichIngredientIsMissing(CoffeeType coffeeType) {
        if (water < coffeeType.getWaterNeeded()) return "water";
        if (milk < coffeeType.getMilkNeeded()) return "milk";
        if (coffeeBeans < coffeeType.getCoffeeBeansNeeded()) return "coffee beans";
        if (cups < 1) return "cups";
        return null;
    }

    // Metoda za dodavanje resursa u aparat
    public void fill(int water, int milk, int coffeeBeans, int cups) {
        this.water += water;
        this.milk += milk;
        this.coffeeBeans += coffeeBeans;
        this.cups += cups;
    }

    // Metoda za uzimanje novca iz aparata
    public float takeMoney() {
        float moneyReturn = money; // Trenutni iznos u aparatu
        money = 0; // Resetovanje novca nakon što se uzme
        return moneyReturn;
    }

    // Metoda koja vraća stanje aparata kao string
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
}
