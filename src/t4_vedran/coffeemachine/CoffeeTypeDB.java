package t4_vedran.coffeemachine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeTypeDB {
    private static final String DB_URL = "jdbc:h2:./src/t4_vedran/coffeemachine/coffeemachine_db/coffee_machine_db;AUTO_SERVER=TRUE";

    // Kreiranje tablice ako ne postoji
    public static void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS coffee_type (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) UNIQUE, " +
                "water_needed INT, " +
                "milk_needed INT, " +
                "coffee_beans_needed INT, " +
                "price INT);";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Provjera broja redaka u tablici
    public static int getCoffeeTypeCount() {
        String countSQL = "SELECT COUNT(*) AS count FROM coffee_type";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Dodavanje vrste kave u tablicu
    public static void addCoffeeType(String name, int waterNeeded, int milkNeeded, int coffeeBeansNeeded, int price) {
        String insertSQL = "INSERT INTO coffee_type (name, water_needed, milk_needed, coffee_beans_needed, price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, waterNeeded);
            pstmt.setInt(3, milkNeeded);
            pstmt.setInt(4, coffeeBeansNeeded);
            pstmt.setInt(5, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Dohvaćanje svih vrsta kave u bazi podataka
    public static List<CoffeeType> getAllCoffeeTypes() {
        List<CoffeeType> coffeeTypes = new ArrayList<>();
        String selectSQL = "SELECT * FROM coffee_type";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                CoffeeType coffeeType = new CoffeeType(
                        rs.getString("name"),
                        rs.getInt("water_needed"),
                        rs.getInt("milk_needed"),
                        rs.getInt("coffee_beans_needed"),
                        rs.getInt("price")
                );
                coffeeTypes.add(coffeeType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coffeeTypes;
    }
    // Ažuriranje vrsta kave
    public static boolean updateCoffeeType(String name, int waterNeeded, int milkNeeded, int coffeeBeansNeeded, int price) {
        String updateSQL = "UPDATE coffee_type SET water_needed = ?, milk_needed = ?, coffee_beans_needed = ?, price = ? WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setInt(1, waterNeeded);
            pstmt.setInt(2, milkNeeded);
            pstmt.setInt(3, coffeeBeansNeeded);
            pstmt.setInt(4, price);
            pstmt.setString(5, name);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Brisanje vrste kave iz baze
    public static boolean deleteCoffeeType(String name) {
        String deleteSQL = "DELETE FROM coffee_type WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
