package t1_darko.coffeemachine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoffeeMachineRepository {


    private Connection connection;

    public CoffeeMachineRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS coffee_machine " +
                "(" +
                "id INT  AUTO_INCREMENT PRIMARY KEY, " +
                "water INT, " +
                "milk INT, " +
                "coffeeBeans INT, " +
                "cups INT, " +
                "money INT " +
                ")";
        String insertQuery = "INSERT INTO coffee_machine ( water, milk, coffeeBeans, cups, money) VALUES ( 400, 540, 120, 9, 550)";
        try {
            connection.prepareStatement(createTableQuery).execute();
            connection.prepareStatement(insertQuery).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(CoffeeMachine coffeeMachine) {
        String query = "UPDATE coffee_machine SET water = ?, milk = ?, coffeeBeans = ?, cups = ?, money = ? WHERE id = 1";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, coffeeMachine.getWater());
            statement.setInt(2, coffeeMachine.getMilk());
            statement.setInt(3, coffeeMachine.getCoffeeBeans());
            statement.setInt(4, coffeeMachine.getCups());
            statement.setInt(5, coffeeMachine.getMoney());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getStatus(CoffeeMachine coffeeMachine) {
        String query = "SELECT water, milk, coffeeBeans, cups, money FROM coffee_machine WHERE id = 1";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            coffeeMachine.setWater(resultSet.getInt("water"));
            coffeeMachine.setMilk(resultSet.getInt("milk"));
            coffeeMachine.setCoffeeBeans(resultSet.getInt("coffeeBeans"));
            coffeeMachine.setCups(resultSet.getInt("cups"));
            coffeeMachine.setMoney(resultSet.getInt("money"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
