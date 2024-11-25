package t1_darko.coffeemachine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String FILE_NAME = "docs/test_darko";
    private static final String URL = "jdbc:h2:./" + FILE_NAME;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
