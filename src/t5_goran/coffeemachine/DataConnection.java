package t5_goran.coffeemachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton class to manage database connection
public class DataConnection {

    private static final String DATABASE_URL = "jdbc:h2:./data/DatabaseGoran";
    private static DataConnection instance; // Singleton instance
    private Connection connection;      // Connection instance to the database

    // Private constructor to establish a database connection
    private DataConnection() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    // Method to get the singleton instance of DataConnection
    public static DataConnection getInstance() {
        if (instance == null) {
            instance = new DataConnection();
        }
        return instance;
    }

    // Method to get the database connection
    public Connection getConnection() {
        return connection;
    }
}
