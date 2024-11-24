package t3_bojan.coffeemachine;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    public static final String TABLE_NAME = "transaction_log";
    private static final String DB_URL_AND_NAME = "jdbc:h2:./docs/transaction_log_bojan";

    public DBManager() {
        createTable();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL_AND_NAME);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
                "id integer PRIMARY KEY auto_increment, \n" +
                "date_time TIMESTAMP NOT NULL,\n" +
                "coffee_name VARCHAR(255) NOT NULL,\n" +
                "transaction_action VARCHAR(255) NOT NULL,\n" +
                "missing_ingredient VARCHAR(255)\n" +
                ");";

        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
            stmt.execute(sqlCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TransactionLog> getTransactionLogs() {
        String sqlSelect = "SELECT * FROM " + TABLE_NAME;

        List<TransactionLog> transactionLogs = new ArrayList<>();
        Connection connection = null;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelect);

            while (resultSet.next()) {
                LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
                CoffeeType coffeeType = new CoffeeType();
                coffeeType.setName(resultSet.getString("coffee_name"));
                String transactionAction = resultSet.getString("transaction_action");
                String missingIngredient = resultSet.getString("missing_ingredient");

                TransactionLog transactionLog = new TransactionLog(dateTime, coffeeType, transactionAction, missingIngredient);
                transactionLogs.add(transactionLog);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Can't close connection");
                }
            }
        }

        return transactionLogs;
    }
}