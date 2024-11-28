package t1_mateo.coffeemachine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoffeeTypeRepository {

    private Connection connection;

    CoffeeTypeRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS coffee_type (" +
                    "id integer PRIMARY KEY auto_increment, " +
                    "coffee_name text NOT NULL, " +
                    "water integer NOT NULL, " +
                    "milk integer NOT NULL, " +
                    "coffee_beans integer  NOT NULL, " +
                    "price double NOT NULL)";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);

            checkBasicTypesOfCoffee();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private void checkBasicTypesOfCoffee() {
        try {
            String sqlCount = "SELECT COUNT(*) FROM coffee_type";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlCount);

            if (rs.next() && rs.getInt(1) < 3) {
                insertBasicCoffeeTypes();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertBasicCoffeeTypes() {
        try {

            String sqlDelete = "DELETE FROM coffee_type";
            Statement stDelete = connection.createStatement();
            stDelete.executeUpdate(sqlDelete);

            String sqlInsert = "INSERT INTO coffee_type (coffee_name, water, milk, coffee_beans, price) VALUES " +
                    "('Espresso', 350, 0, 16, 4), " +
                    "('Latte', 350, 75, 20, 7), " +
                    "('Cappuccino', 200, 100, 12, 6)";

            Statement st = connection.createStatement();
            st.executeUpdate(sqlInsert);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CoffeeType> getAllCoffeeTypes() {
        List<CoffeeType> coffeeTypes = new ArrayList<>();
        try {
            String sqlSelect = "SELECT * FROM coffee_type";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlSelect);

            while (rs.next()) {
                CoffeeType coffeeType = new CoffeeType(
                        rs.getInt("id"),
                        rs.getString("coffee_name"),
                        rs.getInt("water"),
                        rs.getInt("milk"),
                        rs.getInt("coffee_beans"),
                        rs.getDouble("price")
                );
                coffeeTypes.add(coffeeType);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return coffeeTypes;
    }

}

