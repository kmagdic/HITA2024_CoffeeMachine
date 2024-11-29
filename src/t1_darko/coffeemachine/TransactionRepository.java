package t1_darko.coffeemachine;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {


    private Connection connection;

    public TransactionRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS transaction_log " +
                "(" +
                "id_transaction_log INT AUTO_INCREMENT PRIMARY KEY, " +
                "transaction_date TIMESTAMP NOT NULL, " +
                "coffee_type_id INT, " +
                "action VARCHAR(30) NOT NULL, " +
                "ingredients VARCHAR(30), " +
                "FOREIGN KEY (coffee_type_id) REFERENCES coffee_type(id_coffee_type) " +
                ")";
        try {
            connection.prepareStatement(createTableQuery).execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS transaction_log";
        try {
            connection.prepareStatement(dropTableQuery).executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveTransaction(Transaction transaction) {
        String saveQuery = "INSERT INTO transaction_log " +
                "(transaction_date, coffee_type_id, action, ingredients) " +
                "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(saveQuery);
            statement.setTimestamp(1, Timestamp.valueOf(transaction.getLocalDateTime()));
            statement.setInt(2, transaction.getCoffeeType().getId());
            statement.setString(3, transaction.getAction());
            if (transaction.getIngredients() != null) {
                statement.setString(4, transaction.getIngredients());
            } else {
                statement.setString(4,null);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> getAllTransactions() {
        String selectQuery = "SELECT * FROM transaction_log" +
                " INNER JOIN coffee_type ON transaction_log.coffee_type_id = coffee_type.id_coffee_type";
        List<Transaction> transactions = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                int id = resultSet.getInt("id_transaction_log");
                LocalDateTime localDateTime = resultSet.getTimestamp("transaction_date").toLocalDateTime();
                CoffeeType coffeeType = new CoffeeType(
                        resultSet.getInt("id_coffee_type"),
                        resultSet.getString("coffee_type_name"),
                        resultSet.getInt("water"),
                        resultSet.getInt("milk"),
                        resultSet.getInt("coffee_beans"),
                        resultSet.getInt("price")
                );
                String action = resultSet.getString("action");
                String ingredients = resultSet.getString("ingredients");
                Transaction transaction = new Transaction(localDateTime, coffeeType, action, ingredients);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }
}
