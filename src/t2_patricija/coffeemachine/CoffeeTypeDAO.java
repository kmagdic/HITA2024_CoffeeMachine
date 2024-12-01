package t2_patricija.coffeemachine;

import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeTypeDAO implements GenericDao<CoffeeType> {
    private final JdbcDataSource dataSource;

    public CoffeeTypeDAO(JdbcDataSource dataSource) {
        this.dataSource = dataSource; // Use the existing data source
    }

    @Override
    public List<CoffeeType> findAll() {
        List<CoffeeType> coffeeTypes = new ArrayList<>();
        String query = "SELECT * FROM coffee_type";

        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int water = resultSet.getInt("water");
                int milk = resultSet.getInt("milk");
                int coffee_beans = resultSet.getInt("coffee_beans");
                int price = resultSet.getInt("price");

                coffeeTypes.add(new CoffeeType(id, name, water, milk, coffee_beans, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coffeeTypes;
    }

    @Override
    public CoffeeType findById(int id) {
        CoffeeType coffeeType = null;
        String query = "SELECT * FROM coffee_type WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int coffeeTypeId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int water = resultSet.getInt("water");
                int milk = resultSet.getInt("milk");
                int coffeeBeans = resultSet.getInt("coffee_beans");
                int price = resultSet.getInt("price");

                coffeeType = new CoffeeType(coffeeTypeId, name, water, milk, coffeeBeans, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return coffeeType;
    }

    @Override
    public void add(CoffeeType entity) {
        String query = "INSERT INTO coffee_type (name, milk, water, coffee_beans, price) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getWaterNeeded());
            statement.setInt(3, entity.getMilkNeeded());
            statement.setInt(4, entity.getCoffeeBeansNeeded());
            statement.setInt(5, entity.getPrice());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(CoffeeType entity) {
        String query = "UPDATE coffee_type SET name = ?, milk = ?, water = ?, coffee_beans = ?, price = ? WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getMilkNeeded());
            statement.setInt(3, entity.getWaterNeeded());
            statement.setInt(4, entity.getCoffeeBeansNeeded());
            statement.setInt(5, entity.getPrice());
            statement.setInt(6, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM coffee_type WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(CoffeeType coffeeType) {
        String query = "SELECT EXISTS (SELECT 1 FROM coffee_type WHERE name = ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, coffeeType.getName());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}