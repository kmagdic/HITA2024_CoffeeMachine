package t1_darko.coffeemachine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoffeeTypeRepository {


    private Connection connection;

    public CoffeeTypeRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS coffee_type " +
                "(" +
                "id_coffee_type INT AUTO_INCREMENT PRIMARY KEY, " +
                "coffee_type_name VARCHAR(30) NOT NULL, " +
                "water INT NOT NULL, " +
                "milk INT NOT NULL, " +
                "coffee_beans INT NOT NULL, " +
                "price INT NOT NULL" +
                ")";

        try {
            connection.prepareStatement(createTableQuery).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveCoffeeType(CoffeeType coffeeType) {
        String insertQuery = "INSERT INTO coffee_type (coffee_type_name, water, milk, coffee_beans, price) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, coffeeType.getName());
            statement.setInt(2, coffeeType.getWaterNeeded());
            statement.setInt(3, coffeeType.getMilkNeeded());
            statement.setInt(4, coffeeType.getCoffeeBeansNeeded());
            statement.setInt(5, coffeeType.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS coffee_type";
        try {
            connection.prepareStatement(dropTableQuery).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public List<CoffeeType> getAllCoffeeTypes () {
        String selectQuery = "SELECT * FROM coffee_type";
        List<CoffeeType> coffeeTypes = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                coffeeTypes.add(new CoffeeType(resultSet.getInt("id_coffee_type"),
                        resultSet.getString("coffee_type_name"),
                        resultSet.getInt("water"),
                        resultSet.getInt("milk"),
                        resultSet.getInt("coffee_beans"),
                        resultSet.getInt("price")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coffeeTypes;
    }

}
