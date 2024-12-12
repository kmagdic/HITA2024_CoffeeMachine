package t2_patricija.coffeemachine;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import org.h2.jdbcx.JdbcDataSource;

public class TransactionLogDAO implements GenericDao<TransactionLog> {
    private final JdbcDataSource dataSource;
    private CoffeeTypeDAO coffeeTypeDAO;

    // Initialize coffeeTypeDAO via constructor
    public TransactionLogDAO(JdbcDataSource dataSource, CoffeeTypeDAO coffeeTypeDAO) {
        this.dataSource = dataSource;
        this.coffeeTypeDAO = coffeeTypeDAO;
    }

    @Override
    public List<TransactionLog> findAll() {
        List<TransactionLog> transactionLogs = new ArrayList<>();
        String query = "SELECT * FROM transaction_log";

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                TransactionLog transactionLog = new TransactionLog();
                transactionLog.setId(resultSet.getInt("id"));
                transactionLog.setDateTime(resultSet.getObject("dateTime", LocalDateTime.class));
                transactionLog.setSuccess(resultSet.getString("success"));
                transactionLog.setIngredient(resultSet.getString("missing_ingredient"));
                transactionLog.setCoffeeId(resultSet.getInt("coffee_id"));

                transactionLogs.add(transactionLog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionLogs;
    }

    @Override
    public TransactionLog findById(int id) {
        TransactionLog transactionLog = null;
        String query = "SELECT * FROM transaction_log WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                transactionLog.setId(resultSet.getInt("id"));
                transactionLog.setDateTime(resultSet.getObject("dateTime", LocalDateTime.class));
                transactionLog.setSuccess(resultSet.getString("success"));
                transactionLog.setIngredient(resultSet.getString("missing_ingredient"));
                transactionLog.setCoffeeId(resultSet.getInt("coffee_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactionLog;
    }

    @Override
    public void add(TransactionLog entity) {
        // Ensure CoffeeType ID is valid
        if (entity.getCoffeeType().getId() == 0) {
            System.out.println("Error: Invalid CoffeeType ID.");
            return; // Terminate method if ID is invalid
        }

        String query = "INSERT INTO transaction_log (dateTime, coffee_name, coffee_price, success, missing_ingredient, coffee_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setObject(1, entity.getDateTime());
            statement.setString(2, entity.getCoffeeType().getName());
            statement.setInt(3, entity.getCoffeeType().getPrice());
            statement.setString(4, entity.getSuccess());
            statement.setString(5, entity.getIngredient());
            statement.setInt(6, entity.getCoffeeType().getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(TransactionLog entity) {
        String query = "UPDATE transaction_log SET dateTime = ?, coffee_name = ?, coffee_price = ?, success = ?, missing_ingredient = ?, coffee_id = ? WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setObject(1, entity.getDateTime());
            statement.setString(2, entity.getCoffeeType().getName());
            statement.setInt(3, entity.getCoffeeType().getPrice());
            statement.setString(4, entity.getSuccess());
            statement.setString(5, entity.getIngredient());
            statement.setInt(6, entity.getCoffeeType().getId()); // Save coffeeType ID
            statement.setInt(7, entity.getId()); // Set the ID for updating

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM transaction_log WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
