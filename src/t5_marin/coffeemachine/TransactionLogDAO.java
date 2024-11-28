package t5_marin.coffeemachine;

import java.sql.*;

public class TransactionLogDAO {
    private Connection connection;

    public TransactionLogDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS transaction_log (
                    id IDENTITY PRIMARY KEY,
                    datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    coffee_type_id INT,
                    success BOOLEAN,
                    amount DECIMAL(10, 2),
                    FOREIGN KEY (coffee_type_id) REFERENCES coffee_type(id)
                );
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    public void logTransaction(int coffeeTypeId, boolean success, float amount) throws SQLException {
        String insertSQL = "INSERT INTO transaction_log (coffee_type_id, success, amount) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, coffeeTypeId);
            pstmt.setBoolean(2, success);
            pstmt.setFloat(3, amount);
            pstmt.executeUpdate();
        }
    }
}
