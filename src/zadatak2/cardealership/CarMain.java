package zadatak2.cardealership;



import java.util.Scanner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarMain {

    static Connection connection;

    public static Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Car> getList() {
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


    public static void createTable() {

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

    public static void insertCar(Car c) {

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


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CarDealer tomic = new CarDealer("Tomic", "Zagreb", 1990);
        connection = makeDBConnection("src/zadatak2/cardealership/cars");
        createTable();



        while(true) {
            System.out.println("1 - Dodaj auto\n2 - Izbriši auto\n3 - Ispiši aute\n4 - Izlaz");
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("Unesi ime auta:");
                String carName = scanner.next();
                System.out.println("Unesi tip auta:");
                String carType = scanner.next();
                System.out.println("Unesi godinu proizvodnje: ");
                int yearOfProduction = scanner.nextInt();

                Car newCar = new Car(carName,carType,yearOfProduction);



                insertCar(newCar);

            } else if (choice == 2) {
                System.out.println("Koji auto želiš izbrisati");
                String carToDelete = scanner.next();
                tomic.deleteCar(carToDelete);

            } else if (choice == 3) {
                int counter = 1;
                for(Car c: getList()){
                    System.out.println("Car " + counter);
                    System.out.println("Name: " + c.getCarName());
                    System.out.println("Type: " + c.getCarType());
                    System.out.println("Year: " + c.getYearOfProduction());
                    System.out.println("*****************");
                    counter ++;
                }

            } else if (choice == 4) {
                break;
            }
        }

    }
}