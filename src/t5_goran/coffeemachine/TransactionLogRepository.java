package t5_goran.coffeemachine;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogRepository {

    private final Connection connection;

    // Constructor to initialize the repository with a database connection
    public TransactionLogRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS transaction_log (
                id INT AUTO_INCREMENT PRIMARY KEY,
                datetime TIMESTAMP NOT NULL,
                coffee_type_id INT NOT NULL,
                status VARCHAR(50) NOT NULL,
                missing_ingredient VARCHAR(50),
                FOREIGN KEY (coffee_type_id) REFERENCES coffee_type(id)
            );
        """;

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create transaction_log table", e);
        }
    }

    // Method to log a transaction in the database
    public void logTransaction(LocalDateTime timestamp, int coffeeTypeId, String status, String missingIngredient) {
        String insertSQL = """
            INSERT INTO transaction_log (datetime, coffee_type_id, status, missing_ingredient)
            VALUES (?, ?, ?, ?);
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(timestamp));
            preparedStatement.setInt(2, coffeeTypeId);
            preparedStatement.setString(3, status);
            if (missingIngredient != null) {
                preparedStatement.setString(4, missingIngredient);
            } else {
                preparedStatement.setNull(4, Types.VARCHAR);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to log the transaction", e);
        }
    }

    // Method to retrieve all transaction logs from the database
    public List<TransactionLog> getAllTransactions() {
        String selectSQL = """
        SELECT
            transaction_log.id,
            transaction_log.datetime,
            coffee_type.name AS coffee_type,
            transaction_log.status,
            transaction_log.missing_ingredient
        FROM
            transaction_log
        INNER JOIN
            coffee_type
        ON
            transaction_log.coffee_type_id = coffee_type.id
        ORDER BY
            transaction_log.datetime DESC;
        """;

        List<TransactionLog> transactions = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDateTime dateTime = resultSet.getTimestamp("datetime").toLocalDateTime();
                String coffeeType = resultSet.getString("coffee_type");
                String status = resultSet.getString("status");
                String missingIngredient = resultSet.getString("missing_ingredient");

                // Add each transaction log to the list
                transactions.add(new TransactionLog(id, dateTime, coffeeType, status, missingIngredient));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transactions", e);
        }

        return transactions;
    }
}
