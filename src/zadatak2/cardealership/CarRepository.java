package zadatak2.cardealership;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    private static Connection connection;
    CarRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Car> getList() {
        String sqlPrint = "SELECT * FROM cars";
        List<Car> resultList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                Car c = new Car();
                c.setID(rs.getInt("id"));
                c.setCarName(rs.getString("car_name"));
                c.setCarType(rs.getString("car_type"));
                c.setYearOfProduction(rs.getInt("year"));

                resultList.add(c);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }


    public void createTable() {

        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS cars (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "car_name text  NOT NULL,\n " +
                    "car_type text  NOT NULL,\n" +
                    "year int  NOT NULL\n)";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void insertCar(Car c) {

        String insertSql = "INSERT INTO cars (car_name, car_type, year) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, c.getCarName());
            ps.setString(2, c.getCarType());
            ps.setInt(3, c.getYearOfProduction());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
