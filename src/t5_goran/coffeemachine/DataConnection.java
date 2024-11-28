package t5_goran.coffeemachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {

    private static final String DATABASE_URL = "jdbc:h2:./data/DatabaseGoran";
    private static DataConnection instance;
    private Connection connection;

    private DataConnection() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public static DataConnection getInstance() {
        if (instance == null) {
            instance = new DataConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
