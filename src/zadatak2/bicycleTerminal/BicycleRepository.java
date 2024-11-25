package zadatak2.bicycleTerminal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BicycleRepository implements Repository<Bicycle> {

    Connection connection;

    public BicycleRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable() {
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS bicycle (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "type VARCHAR(255)  NOT NULL,\n " +
                    "color VARCHAR(255)  NOT NULL,\n" +
                    "battery_level VARCHAR(255)  NOT NULL,\n" +
                    "user_level integer  NOT NULL\n)";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Bicycle> getList() {

        String sqlPrint = "SELECT * FROM bicycle";
        List<Bicycle> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlPrint);

            while (resultSet.next()) {
                Bicycle bicycle = new Bicycle();
                bicycle.setId(resultSet.getInt("id"));
                bicycle.setType(resultSet.getString("type"));
                bicycle.setColor(resultSet.getString("color"));
                bicycle.setBatteryLevel(resultSet.getString("battery_level"));
                bicycle.setUserLevel(resultSet.getInt("user_level"));

                resultList.add(bicycle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultList;
    }

    @Override
    public void insert(Bicycle bicycle) {
        String insertSql = "INSERT INTO bicycle (type, color, battery_level, user_level) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, bicycle.getType());
            preparedStatement.setString(2, bicycle.getColor());
            preparedStatement.setString(3, bicycle.getBatteryLevel());
            preparedStatement.setInt(4, bicycle.getUserLevel());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
