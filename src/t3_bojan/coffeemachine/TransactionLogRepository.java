package t3_bojan.coffeemachine;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogRepository implements Repository<TransactionLog> {

    private static final String TABLE_NAME = "transaction_log";
    private Connection connection;

    public TransactionLogRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
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

    @Override
    public List<TransactionLog> getList() {
        String sqlSelect = "SELECT * FROM " + TABLE_NAME;

        List<TransactionLog> transactionLogs = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelect);

            while (resultSet.next()) {

                TransactionLog transactionLog = new TransactionLog();
                transactionLog.setId(resultSet.getInt("id"));
                transactionLog.setDateTime(resultSet.getTimestamp("date_time").toLocalDateTime());
                CoffeeType coffeeType = new CoffeeType();
                coffeeType.setName(resultSet.getString("coffee_name"));
                transactionLog.setCoffeeType(coffeeType);
                transactionLog.setTransactionAction(resultSet.getString("transaction_action"));
                transactionLog.setMissingIngredient(resultSet.getString("missing_ingredient"));

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

    @Override
    public void insert(TransactionLog transactionLog) {

        String insertSqlWithoutMissingIngredient = "INSERT INTO " + DBManager.TABLE_NAME + " (date_time, coffee_name, transaction_action) VALUES (?, ?, ?)";
        String insertSqlWithMissingIngredient = "INSERT INTO " + DBManager.TABLE_NAME + " (date_time, coffee_name, transaction_action, missing_ingredient) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(transactionLog.getMissingIngredient() == null ? insertSqlWithoutMissingIngredient : insertSqlWithMissingIngredient)) {
            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(transactionLog.getDateTime()));
            stmt.setString(2, transactionLog.getCoffeeType().getName());
            stmt.setString(3, transactionLog.getTransactionAction());
            if (transactionLog.getMissingIngredient() != null) {
                stmt.setString(4, transactionLog.getMissingIngredient());
            }
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    }
