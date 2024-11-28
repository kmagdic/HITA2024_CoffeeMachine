package t5_marin.coffeemachine;

import java.util.ArrayList;
import java.util.List;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private float money;

    public void setPassword(String password) {
        this.password = password;
    }

    private String password = "admin";  // Default password
    private List<String> transactionLog;

    public CoffeeMachine() {
        // Initial resources
        this.water = 1000;
        this.milk = 500;
        this.coffeeBeans = 200;
        this.cups = 10;
        this.money = 0.0f;
        this.transactionLog = new ArrayList<>();
    }

    // Fill resources
    public void fillResources(int water, int milk, int coffeeBeans, int cups) {
        this.water += water;
        this.milk += milk;
        this.coffeeBeans += coffeeBeans;
        this.cups += cups;
        transactionLog.add("Filled resources: " + water + " ml water, " + milk + " ml milk, " + coffeeBeans + "g coffee beans, " + cups + " cups.");
    }

    // Take money
    public float takeMoney() {
        float tempMoney = this.money;
        this.money = 0;
        return tempMoney;
    }

    // Getters for remaining resources
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

    public float getMoney() {
        return money;
    }

    // Check password
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    // Change password
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // Show transaction log
    public void showTransactionLog() {
        if (transactionLog.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (String log : transactionLog) {
                System.out.println(log);
            }
        }
    }
}
