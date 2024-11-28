package t5_goran.coffeemachine;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogRepository {

    private final Connection connection;

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

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create transaction_log table", e);
        }
    }

    public void logTransaction(LocalDateTime timestamp, int coffeeTypeId, String status, String missingIngredient) {
        String insertSQL = """
            INSERT INTO transaction_log (datetime, coffee_type_id, status, missing_ingredient)
            VALUES (?, ?, ?, ?);
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(timestamp));
            pstmt.setInt(2, coffeeTypeId);
            pstmt.setString(3, status);
            if (missingIngredient != null) {
                pstmt.setString(4, missingIngredient);
            } else {
                pstmt.setNull(4, Types.VARCHAR);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to log the transaction", e);
        }
    }

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

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDateTime dateTime = rs.getTimestamp("datetime").toLocalDateTime();
                String coffeeType = rs.getString("coffee_type");
                String status = rs.getString("status");
                String missingIngredient = rs.getString("missing_ingredient");

                transactions.add(new TransactionLog(id, dateTime, coffeeType, status, missingIngredient));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve transactions", e);
        }

        return transactions;
    }
}
