package zadatak2.houseRenting;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBMenager {
    static Connection connection;

    public static void main(String[] args) {
        RentingObjects rentingObjects = new RentingObjects(1, "Draga", "Apartment", 2020, "standard", 100);
        System.out.println(rentingObjects);

        connection = makeDBConnection("docs/houseRentingDB");
        createTable();

        insertRentingObjects(rentingObjects);

        List<RentingObjects> rentingObjectsList = getList();
        System.out.println("Renting Objects in DB: " + rentingObjectsList);

        System.out.println("Saved!");

        // Close connection
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTable() {
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS RentingObjects (" +
                    "id integer PRIMARY KEY auto_increment, " +
                    "name text NOT NULL, " +
                    "type text NOT NULL, " +
                    "yearOfProduction integer NOT NULL, " +
                    "category text NOT NULL, " +
                    "price double NOT NULL)";
            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertRentingObjects(RentingObjects s) {
        String insertSql = "INSERT INTO RentingObjects (name, type, yearOfProduction, category, price) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, s.getName());
            ps.setString(2, s.getType());
            ps.setInt(3, s.getYearOfProduction());
            ps.setString(4, s.getCategory());
            ps.setDouble(5, s.getPriceForOneNight());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<RentingObjects> getList() {
        String sqlPrint = "SELECT * FROM RentingObjects";
        List<RentingObjects> resultList = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);
            while (rs.next()) {
                RentingObjects s = new RentingObjects();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setType(rs.getString("type"));
                s.setYearOfProduction(rs.getInt("yearOfProduction"));
                s.setCategory(rs.getString("category"));
                s.setPriceForOneNight(rs.getDouble("price"));
                resultList.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
