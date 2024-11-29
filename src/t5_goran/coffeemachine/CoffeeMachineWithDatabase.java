package t5_goran.coffeemachine;

import java.sql.Connection;

public class CoffeeMachineWithDatabase extends CoffeeMachine {

    private final CoffeeMachineRepository coffeeMachineRepository;

    public CoffeeMachineWithDatabase(int water, int milk, int coffeeBeans, int cups, float money, Connection connection) {
        super(water, milk, coffeeBeans, cups, money);
        this.coffeeMachineRepository = new CoffeeMachineRepository(connection);
    }

    @Override
    public boolean start() {
        // Ensure the CoffeeMachineState table exists
        coffeeMachineRepository.createTable();
        // Ensure the CoffeeType table exists and is populated
        CoffeeTypeRepository coffeeTypeRepo = new CoffeeTypeRepository(DataConnection.getInstance().getConnection());
        coffeeTypeRepo.createTable();

        if (coffeeTypeRepo.getAllCoffeeTypes().isEmpty()) {
            coffeeTypeRepo.addCoffeeType(new CoffeeType(1, "Espresso", 350, 0, 16, 4));
            coffeeTypeRepo.addCoffeeType(new CoffeeType(2, "Latte", 350, 75, 20, 7));
            coffeeTypeRepo.addCoffeeType(new CoffeeType(3, "Cappuccino", 200, 100, 12, 6));
        }

        // Ensure the TransactionLog table exists
        TransactionLogRepository logRepo = new TransactionLogRepository(DataConnection.getInstance().getConnection());
        logRepo.createTable();

        System.out.println("Database initialization complete.");

        // Attempt to load state from the database
        CoffeeMachineState state = coffeeMachineRepository.loadState();
        if (state != null) {
            setState(state);
            System.out.println("State loaded successfully from database.");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void stop() {
        CoffeeMachineState state = getState();
        coffeeMachineRepository.saveState(state);
        System.out.println("State saved successfully to database.");
    }

    private CoffeeMachineState getState() {
        return new CoffeeMachineState(
                getWater(),
                getMilk(),
                getCoffeeBeans(),
                getCups(),
                getMoney(),
                getAdminUsername(),
                getAdminPassword()
        );
    }

    private void setState(CoffeeMachineState state) {
        this.water = state.getWater();
        this.milk = state.getMilk();
        this.coffeeBeans = state.getCoffeeBeans();
        this.cups = state.getCups();
        this.money = state.getMoney();
        setAdminUsername(state.getAdminUsername());
        setAdminPassword(state.getAdminPassword());
    }
}
