/*
package t2_patricija.coffeemachine;


import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CoffeeMachineDB {

    Connection connection;

    private static CoffeeMachineDB instance;

    //Explanation: An instance represents a specific object created from a class,
    // enabling its methods and data to be used.
    // This method ensures a single instance (singleton) of CoffeeMachineDB is created and reused.
    // If the instance does not already exist, it is initialized with the provided file name.

    public static CoffeeMachineDB getInstance(String fileName) {
        if (instance == null) {
            instance = new CoffeeMachineDB(fileName);
        }
        return instance;
    }

    // Explanation: This constructor initializes the database connection using the makeDBConnection method.
    // If the connection is not successfully established (null), a RuntimeException is thrown.

    public CoffeeMachineDB(String fileName) {
        this.connection = makeDBConnection(fileName);
        if (this.connection == null) {
            throw new RuntimeException("Failed to connect to the database");
        }
        System.out.println("Database connection initialized with file: " + fileName);
        createTable(); // Ensure table is created when the instance is initialized
    }

    //Make connection method
    public Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Create table method
    public void createTable() {
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS transaction_log (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "date_time DATETIME NOT NULL,\n" +
                    "coffee_type text NOT NULL,\n" +
                    "success text NOT NULL,\n" +
                    "ingredient text NULL\n" +
                    ");";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Insert into table method

    public void insertTransactionLog(TransactionLog l) {
        String insertSql = "INSERT INTO transaction_log (date_time, coffee_type, success, ingredient) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setTimestamp(1, l.getDate_time());
            ps.setString(2, l.getCoffeeTypeName());
            ps.setString(3, l.getSuccess());
            ps.setString(4, l.getIngredient());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Retrieves all entries from the transaction_log table in the database
    // and returns them as a list of TransactionLog objects.

    public List<TransactionLog> getList() {
        String sqlPrint = "SELECT * FROM transaction_log";
        List<TransactionLog> resultList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                TransactionLog l = new TransactionLog();
                l.setId(rs.getInt("id"));
                l.setDate_time(rs.getTimestamp("date_time"));
                l.setCoffeeTypeName(rs.getString("coffe_type"));
                l.setSuccess(rs.getString("success"));
                l.setIngredient(rs.getString("ingredient"));

                resultList.add(l);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

}

 */
