package t2_patricija.coffeemachine;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbClient {
    private static final String CONNECTION_URL = "jdbc:h2:./docs/coffee_machine_2"; // Hardkodiraj URL za bazu
    private static DbClient instance;
    private final JdbcDataSource dataSource;

    private DbClient() {
        System.out.println("DbClient instance created");
        dataSource = new JdbcDataSource();
        dataSource.setURL(CONNECTION_URL);
        initializeDatabase();
    }

    public static DbClient getInstance() {
        if (instance == null) {
            instance = new DbClient();
        }
        return instance;
    }

    private void initializeDatabase() {
        // SQL statements to create tables for CoffeeType and TransactionLog
        String createCoffeeTypeTable = "CREATE TABLE IF NOT EXISTS coffee_type (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "water INT NOT NULL, " +
                "milk INT NOT NULL, " +
                "coffee_beans INT NOT NULL, " +
                "price INT NOT NULL" +
                ")";

        String createTransactionLogTable = "CREATE TABLE IF NOT EXISTS transaction_log (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "dateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "coffee_name VARCHAR(100) NOT NULL, " +
                "coffee_price INT NOT NULL, " +
                "success VARCHAR(100) NOT NULL, " +
                "missing_ingredient VARCHAR(255), " +
                "coffee_id INT NOT NULL, " +
                "FOREIGN KEY (coffee_id) REFERENCES coffee_type(id)" +
                ")";

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement()) {
            // Execute the statements to create the tables
            statement.executeUpdate(createCoffeeTypeTable);
            statement.executeUpdate(createTransactionLogTable);
            System.out.println("Database initialized: coffee_type and transaction_log tables created.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JdbcDataSource getDataSource() {
        return dataSource; // Provide access to the configured data source
    }
}
