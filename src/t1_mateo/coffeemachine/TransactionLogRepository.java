package t1_mateo.coffeemachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogRepository {

    private Connection connection;

    TransactionLogRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTableTransactionLog() {
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS transaction_log (" +
                    "id integer PRIMARY KEY auto_increment, " +
                    "coffee_type_id integer NOT NULL, " +
                    "date datetime NOT NULL, " +
                    "ingredients text NULL, " +
                    "action text NOT NULL, " +
                    "FOREIGN KEY (coffee_type_id) REFERENCES coffee_type(id))";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertLog(TransactionLog log, CoffeeType coffeeType) {

        String insertSql = "INSERT INTO transaction_log (coffee_type_id, date, ingredients, action) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setTimestamp(2, log.getDate());
            ps.setInt(1, coffeeType.getId());
            ps.setString(4, log.getAction());
            ps.setString(3, log.getIngredients());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TransactionLog> getLog() {
        String sqlPrint = "SELECT * FROM transaction_log";
        List<TransactionLog> resultList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);
            while (rs.next()) {
                TransactionLog log = new TransactionLog();
                log.setDate(rs.getTimestamp("date").toLocalDateTime());
                log.setCoffeeTypeId(rs.getInt("coffee_type_id"));
                log.setAction(rs.getString("action"));
                log.setIngredients(rs.getString("ingredients"));

                resultList.add(log);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

}
