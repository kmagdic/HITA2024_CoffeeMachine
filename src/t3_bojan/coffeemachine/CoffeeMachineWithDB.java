package t3_bojan.coffeemachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class CoffeeMachineWithDB extends CoffeeMachine{

    private Connection connection;
    private MachineStateRepository machineStateRepository;
    private TransactionLogRepository transactionLogRepository;
    private CoffeeTypeRepository coffeeTypeRepository;
    private static final String DB_URL_AND_NAME = "jdbc:h2:./docs/transaction_log_bojan";

    private final String TRANSACTION_SUCCESS_ACTION = "Bought";
    private final String TRANSACTION_FAIL_ACTION = "Not Bought";
    private final String I_HAVE_ENOUGH_RESOURCES = "I have enough resources, making you ";
    private final String I_DONT_HAVE_ENOUGH_RESOURCES = "Sorry, not enough ";

    public CoffeeMachineWithDB(int water, int milk, int coffeeBeans, int cups, float money) {
        super(water, milk, coffeeBeans, cups, money);
        makeDBConnection();

        machineStateRepository = new MachineStateRepository(connection);
        machineStateRepository.createTable();

        coffeeTypeRepository = new CoffeeTypeRepository(connection);
        coffeeTypeRepository.createTable();

        transactionLogRepository = new TransactionLogRepository(connection);
        transactionLogRepository.createTable();
    }

    public boolean start() {
        isLoadedFromDB = loadFromDB();
        int rows = coffeeTypeRepository.getCoffeeTypeRowCount();

        if (rows < 3) {
            for (CoffeeType coffeeType : coffeeTypes) {
                coffeeTypeRepository.insert(coffeeType);
            }
        }
        return isLoadedFromDB;
    }

    private boolean loadFromDB() {
        List<MachineState> machineStateList = machineStateRepository.getList();

        if (machineStateList.size() > 0) {
            MachineState machineState = machineStateList.get(0);
            this.water = machineState.getWater();
            this.milk = machineState.getMilk();
            this.coffeeBeans = machineState.getCoffeeBeans();
            this.cups = machineState.getCups();
            this.money = machineState.getMoney();
            return true;
        } else {
            return false;
        }
    }

    public void stop() {
        if (machineStateRepository.isTableIsEmpty()) {
            machineStateRepository.insert(new MachineState(this.water, this.milk, this.coffeeBeans, this.cups, this.money));
        }
        else {
            machineStateRepository.update(new MachineState(this.water, this.milk, this.coffeeBeans, this.cups, this.money));
        }
    }

    private void makeDBConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL_AND_NAME);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String buyCoffee(CoffeeType coffeeType) {
        if (hasEnoughResources(coffeeType)) {
            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.cups -= 1;
            this.money += coffeeType.getPrice();
            TransactionLog transactionLog = new TransactionLog(LocalDateTime.now(), coffeeType, TRANSACTION_SUCCESS_ACTION);
            transactionLogRepository.insert(transactionLog);

            return I_HAVE_ENOUGH_RESOURCES + coffeeType.getName() + "\n";
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);
            TransactionLog transactionLog = new TransactionLog(LocalDateTime.now(), coffeeType, TRANSACTION_FAIL_ACTION, missing);
            transactionLogRepository.insert(transactionLog);

            return I_DONT_HAVE_ENOUGH_RESOURCES + missing + "\n";
        }
    }

    public List<TransactionLog> getTransactionLog() {
        return transactionLogRepository.getList();
    }
}
