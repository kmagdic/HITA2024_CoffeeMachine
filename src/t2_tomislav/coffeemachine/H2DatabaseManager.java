package t2_tomislav.coffeemachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2DatabaseManager {

    private final Connection connection;

    public H2DatabaseManager(String absolutePath) {
        this.connection = makeDBConnection(absolutePath);
        if (this.connection == null) {
            throw new RuntimeException("Failed to connect to the database");
        }
        createTable();
    }

    // Metoda za stvaranje konekcije s apsolutnom putanjom
    private Connection makeDBConnection(String absolutePath) {
        try {
            // Apsolutna putanja baze
            return DriverManager.getConnection("jdbc:h2:" + absolutePath);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating database connection: " + e.getMessage(), e);
        }
    }

    // Metoda za kreiranje tablice ako ne postoji
    private void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS transaction_log (" +
                "id BIGINT  PRIMARY KEY, " +
                "date_time TIMESTAMP NOT NULL, " +
                "coffee_type VARCHAR(50) NOT NULL, " +
                "success VARCHAR(10) NOT NULL, " +
                "ingredient VARCHAR(50)" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table: " + e.getMessage(), e);
        }
    }

    // Metoda za umetanje transakcijskog zapisa u bazu
    public void insertTransactionLog(TransactionLog log) {
        String insertSQL = "INSERT INTO transaction_log (date_time, coffee_type, success, ingredient) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            ps.setTimestamp(1, log.getDate_time());
            ps.setString(2, log.getCoffeeTypeName());
            ps.setString(3, log.getSuccess());
            ps.setString(4, log.getIngredient());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting transaction log: " + e.getMessage(), e);
        }
    }

    // Metoda za dohvaćanje svih zapisa iz baze
    public List<TransactionLog> getTransactionLogs() {
        List<TransactionLog> logs = new ArrayList<>();
        String selectSQL = "SELECT * FROM transaction_log";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                TransactionLog log = new TransactionLog(
                        resultSet.getString("coffee_type"),
                        resultSet.getString("success"),
                        resultSet.getString("ingredient")
                );
                log.setDate_time(resultSet.getTimestamp("date_time"));
                log.setId(resultSet.getInt("id"));
                logs.add(log);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving transaction logs: " + e.getMessage(), e);
        }
        return logs;
    }

    // Metoda za ispis svih zapisa iz baze u konzolu
    //public void printTransactionLogs() {
       // List<TransactionLog> logs = getTransactionLogs();
       // if (logs.isEmpty()) {
        //    System.out.println("No transaction logs available.");
       // } else {
        //    System.out.println("Transaction logs:");
          //  for (TransactionLog log : logs) {
          //      System.out.println(log);
           // }
       // }
   // }
}
