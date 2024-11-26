package t2_patricija.coffeemachine;


import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class CoffeeMachineDB {

    Connection connection;

    private static CoffeeMachineDB instance;

    public static CoffeeMachineDB getInstance(String fileName) {
        if (instance == null) {
            instance = new CoffeeMachineDB(fileName);
        }
        return instance;
    }

    //Constructor with calls the makeDBConnection method
    public CoffeeMachineDB(String fileName) {
        this.connection = makeDBConnection(fileName);
        if (this.connection == null) {
            throw new RuntimeException("Failed to connect to the database");
        }
        System.out.println("Database connection initialized with file: " + fileName);
        createTable(); // Ensure table is created when the instance is initialized
    }

    //Make Connection
    public Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Create table
    public void createTable() {
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS transaction_log (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "date_time DATE NOT NULL,\n" +
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

    // Insert into table

    //attributes are datetime, coffe_type, success, ingredient

    public void insertTransactionLog(TransactionLog l) {
        String insertSql = "INSERT INTO transaction_log (date_time, coffee_type, success, ingredient) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setDate(1, l.getDate());
            ps.setString(2, l.getCoffeeTypeName());
            ps.setString(3, l.getSuccess());
            ps.setString(4, l.getIngredient());

            ps.executeUpdate();

            System.out.println("Transaction log inserted successfully.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // List

    public List<TransactionLog> getList() {
        String sqlPrint = "SELECT * FROM transaction_log";
        List<TransactionLog> resultList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                TransactionLog l = new TransactionLog();
                l.setId(rs.getInt("id"));
                l.setDate(rs.getDate("date_time"));
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
