package t3_dinko.coffeemachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeTypeRepository {
    private Connection connection;
    private static final String TABLE_NAME = "coffee_type";

    public CoffeeTypeRepository(Connection connection) {
        this.connection = connection;
    }


    public void createTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
                "id integer PRIMARY KEY auto_increment, \n" +
                "name VARCHAR(255) NOT NULL,\n" +
                "milkNeeded integer NOT NULL,\n" +
                "waterNeeded integer NOT NULL,\n" +
                "coffeeBeansNeeded integer NOT NULL,\n" +
                "price integer NOT NULL\n" +
                ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CoffeeType> getList() {
        String sqlSelect = "SELECT * FROM " + TABLE_NAME;

        List<CoffeeType> coffeeTypes = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelect);

            while (resultSet.next()) {
                CoffeeType coffeeType = new CoffeeType();
                coffeeType.setId(resultSet.getInt("id"));
                coffeeType.setName(resultSet.getString("name"));
                coffeeType.setMilkNeeded(resultSet.getInt("milkNeeded"));
                coffeeType.setWaterNeeded(resultSet.getInt("waterNeeded"));
                coffeeType.setCoffeeBeansNeeded(resultSet.getInt("coffeeBeansNeeded"));
                coffeeType.setPrice(resultSet.getInt("price"));

                coffeeTypes.add(coffeeType);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return coffeeTypes;
    }

    public void insert(CoffeeType coffeeType) {
        String sqlInsert = "INSERT INTO " + TABLE_NAME + " (name, milkNeeded, waterNeeded, coffeeBeansNeeded, price) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlInsert);
            statement.setString(1, coffeeType.getName());
            statement.setInt(2, coffeeType.getMilkNeeded());
            statement.setInt(3, coffeeType.getWaterNeeded());
            statement.setInt(4, coffeeType.getCoffeeBeansNeeded());
            statement.setInt(5, coffeeType.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getCoffeeTypeRowCount() {
        int rowCount = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM (SELECT * FROM coffee_type)");
            if (rs.next()) {
                rowCount = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowCount;
    }
}
