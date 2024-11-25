package t1_darko.coffeemachine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TransactionRepository {

    // get all transaction

    // get transaction

    // save log
    public void saveTransaction (Transaction transaction) {
        String saveQuery = "INSERT INTO transaction_log (transaction_date, coffee_type, transaction_success, ingredient) VALUES (?, ?, ?, ?)";
        Connection connection = DbConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(saveQuery);
            statement.setTimestamp(1, Timestamp.valueOf(transaction.getLocalDateTime()));
            statement.setString(2, transaction.getCoffeeType().getName());
            statement.setString(3, transaction.getAction());
            if (transaction.getIngredients() == null) {
                statement.setString(4,null);
            } else {
                statement.setString(4, transaction.getIngredients());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // delete transaction

    // delete all transactions
}
