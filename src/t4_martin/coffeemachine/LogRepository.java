package t4_martin.coffeemachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogRepository {

    private Connection connection;

    LogRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {

        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS transaction_log (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "datetime text  NOT NULL,\n " +
                    "coffee_type text  NOT NULL,\n" +
                    "success text  NOT NULL,\n" +
                    "amount integer NOT NULL)\n";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public void insertLog(TransactionLog t) {

        String insertSql = "INSERT INTO transaction_log (datetime, coffee_type, success, amount) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, t.getDatetime());
            ps.setString(2, t.getCoffee_type());
            ps.setString(3, t.getSuccess());
            ps.setInt(4,t.getAmount());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<TransactionLog> getList() {
        String sqlPrint = "SELECT * FROM transaction_log";
        List<TransactionLog> resultList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                TransactionLog t = new TransactionLog();
                t.setId(rs.getInt("id"));
                t.setDatetime(rs.getString("datetime"));
                t.setCoffee_type(rs.getString("coffee_type"));
                t.setSuccess(rs.getString("success"));
                t.setAmount(Integer.parseInt(rs.getString("amount")));

                resultList.add(t);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}

