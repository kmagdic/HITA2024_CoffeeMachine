package t5_marin.coffeemachine;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CoffeeMachineDatabaseCheck {

    private Connection connection;

    public CoffeeMachineDatabaseCheck(Connection connection) {
        this.connection = connection;
    }

    public void checkAndCreateTables() {
        try {
            // Check and create each table
            checkAndCreateTable("CoffeeMachine", getCoffeeMachineTableSQL());
            checkAndCreateTable("CoffeeType", getCoffeeTypeTableSQL());
            checkAndCreateTable("Location", getLocationTableSQL());
            checkAndCreateTable("CoffeeMachineManagerEntry", getCoffeeMachineManagerEntryTableSQL());
            checkAndCreateTable("TransactionLog", getTransactionLogTableSQL());
            checkAndCreateTable("CoffeeMachineCoffeeType", getCoffeeMachineCoffeeTypeTableSQL());  // New table
        } catch (SQLException e) {
            System.out.println("Error checking or creating tables: " + e.getMessage());
        }
    }

    private void checkAndCreateTable(String tableName, String createSQL) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, tableName.toUpperCase(), null);

        if (!tables.next()) {
            System.out.println("Table '" + tableName + "' does not exist. Creating the table...");
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(createSQL);
                System.out.println("Table '" + tableName + "' created successfully.");
            }
        } else {
            System.out.println("Table '" + tableName + "' already exists.");
        }
    }

    private String getCoffeeMachineTableSQL() {
        return "CREATE TABLE CoffeeMachine (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "water INT, " +
                "milk INT, " +
                "coffeeBeans INT, " +
                "cups INT, " +
                "money FLOAT, " +
                "password VARCHAR(50))";
    }

    private String getCoffeeTypeTableSQL() {
        return "CREATE TABLE CoffeeType (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "waterNeeded INT, " +
                "milkNeeded INT, " +
                "coffeeBeansNeeded INT, " +
                "price FLOAT)";
    }

    private String getLocationTableSQL() {
        return "CREATE TABLE Location (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "drzava VARCHAR(50), " +
                "zupanija VARCHAR(50), " +
                "postanskiBroj INT, " +
                "adresa VARCHAR(100))";
    }

    private String getCoffeeMachineManagerEntryTableSQL() {
        return "CREATE TABLE CoffeeMachineManagerEntry (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "coffeeMachineId INT, " +
                "locationId INT, " +
                "timeOfInstalment TIMESTAMP, " +
                "FOREIGN KEY (coffeeMachineId) REFERENCES CoffeeMachine(id), " +
                "FOREIGN KEY (locationId) REFERENCES Location(id))";
    }

    private String getTransactionLogTableSQL() {
        return "CREATE TABLE TransactionLog (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "coffeeMachineId INT, " +
                "log VARCHAR(255), " +
                "FOREIGN KEY (coffeeMachineId) REFERENCES CoffeeMachine(id))";
    }

    // New SQL statement for the CoffeeMachineCoffeeType table
    private String getCoffeeMachineCoffeeTypeTableSQL() {
        return "CREATE TABLE CoffeeMachineCoffeeType (" +
                "coffeeMachineId INT NOT NULL, " +
                "coffeeTypeId INT NOT NULL, " +
                "PRIMARY KEY (coffeeMachineId, coffeeTypeId), " +
                "FOREIGN KEY (coffeeMachineId) REFERENCES CoffeeMachine(id), " +
                "FOREIGN KEY (coffeeTypeId) REFERENCES CoffeeType(id))";
    }
}
