package t5_goran.coffeemachine;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final String DATABASE_URL = "jdbc:h2:./data/transaction_logGoran";
    private static DataManager instance;
    private Connection connection;

    private DataManager() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void createTable() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS transaction_log (
                id INT AUTO_INCREMENT PRIMARY KEY,
                datetime TIMESTAMP NOT NULL,
                coffee_type VARCHAR(50) NOT NULL,
                status VARCHAR(50) NOT NULL,
                missing_ingredient VARCHAR(50)
            );
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create the table", e);
        }
    }

    public void logTransaction(LocalDateTime timestamp, String coffeeType, String status, String shortage) {
        String insertSQL = """
            INSERT INTO transaction_log (datetime, coffee_type, status, missing_ingredient)
            VALUES (?, ?, ?, ?);
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(timestamp));
            pstmt.setString(2, coffeeType);
            pstmt.setString(3, status);
            if (shortage != null) {
                pstmt.setString(4, shortage);
            } else {
                pstmt.setNull(4, Types.VARCHAR);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to log the transaction", e);
        }
    }

    public List<TransactionLog> getTransactions() {
        String selectSQL = "SELECT * FROM transaction_log";
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
