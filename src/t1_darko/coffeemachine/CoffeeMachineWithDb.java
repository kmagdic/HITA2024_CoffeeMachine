package t1_darko.coffeemachine;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class CoffeeMachineWithDb extends CoffeeMachine{

    private TransactionRepository transactionRepository;

    public CoffeeMachineWithDb(int water, int milk, int coffeeBeans, int cups, float money) throws SQLException {
        super(water, milk, coffeeBeans, cups, money);
        createTable();
        this.transactionRepository = new TransactionRepository();
    }

    // create database table if not exists
    public void createTable() {
        String createTableQuery =   "DROP TABLE IF EXISTS transaction_log; \n" +
                "CREATE TABLE IF NOT EXISTS transaction_log \n" +
                "(\n" +
                "id_transaction integer PRIMARY KEY auto_increment, \n" +
                "transaction_date datetime NOT NULL, \n" +
                "coffee_type varchar(20) NOT NULL, \n" +
                "transaction_success varchar(20), \n" +
                "ingredient varchar(20) \n" +
                ");\n";

        try {
            Statement statement = DbConnection.getConnection().createStatement();
            statement.execute(createTableQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String buyCoffee(CoffeeType coffeeType){
        if (hasEnoughResources(coffeeType)) {
            this.water -= coffeeType.getWaterNeeded();
            this.milk -= coffeeType.getMilkNeeded();
            this.coffeeBeans -= coffeeType.getCoffeeBeansNeeded();
            this.cups -= 1;
            this.money += coffeeType.getPrice();
            Transaction buyTransaction = new Transaction(LocalDateTime.now(), coffeeType, "Bought");
            transactionRepository.saveTransaction(buyTransaction);
            return "I have enough resources, making you " + coffeeType.getName() + "\n";
        } else {
            String missing = calculateWhichIngredientIsMissing(coffeeType);
            Transaction notBuyTransaction = new Transaction(LocalDateTime.now(), coffeeType, "Not Bought", missing);
            transactionRepository.saveTransaction(notBuyTransaction);
            return "Sorry, not enough " + missing + "\n";
        }
    }

    public boolean start(){
        return true;
    }

    public void stop() {

    }
}
