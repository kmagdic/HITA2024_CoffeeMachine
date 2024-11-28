package t3_bojan.coffeemachine;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    public static final String TABLE_NAME = "transaction_log";
    private static final String DB_URL_AND_NAME = "jdbc:h2:./docs/transaction_log_bojan";
    private static DBManager instance;
    private Connection connection;

    private DBManager() {
        try {
            connection = DriverManager.getConnection(DB_URL_AND_NAME);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static DBManager newInstance() {
        return new DBManager();
    }

    public void createTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
                "id integer PRIMARY KEY auto_increment, \n" +
                "date_time TIMESTAMP NOT NULL,\n" +
                "coffee_name VARCHAR(255) NOT NULL,\n" +
                "transaction_action VARCHAR(255) NOT NULL,\n" +
                "missing_ingredient VARCHAR(255)\n" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TransactionLog> getTransactionLogs() {
        String sqlSelect = "SELECT * FROM " + TABLE_NAME;

        List<TransactionLog> transactionLogs = new ArrayList<>();
        try {
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

    public void insertTransactionLogToDB(LocalDateTime dateTime, String coffeeTypeName, String transactionAction, String missingIngredient) {

        String insertSqlWithoutMissingIngredient = "INSERT INTO " + DBManager.TABLE_NAME + " (date_time, coffee_name, transaction_action) VALUES (?, ?, ?)";
        String insertSqlWithMissingIngredient = "INSERT INTO " + DBManager.TABLE_NAME + " (date_time, coffee_name, transaction_action, missing_ingredient) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(missingIngredient == null ? insertSqlWithoutMissingIngredient : insertSqlWithMissingIngredient)) {
            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(dateTime));
            stmt.setString(2, coffeeTypeName);
            stmt.setString(3, transactionAction);
            if (missingIngredient != null) {
                stmt.setString(4, missingIngredient);
            }
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}