package t4_vedran.coffeemachine;

import java.sql.*;

class TransactionDB {
    private final String dateTime;
    private final CoffeeType coffeeType;
    private final String action;

    public TransactionDB(String dateTime, CoffeeType coffeeType, String action) {
        this.dateTime = dateTime;
        this.coffeeType = coffeeType;
        this.action = action;
    }

    @Override
    public String toString() {
        return "Date/time: " + dateTime + ", coffee type: " + coffeeType + ", action: " + action;
    }

    private static final String DB_URL = "jdbc:h2:./src/t4_vedran/coffeemachine/coffeemachine_db/coffee_machine_db;AUTO_SERVER=TRUE";


    // Kreira se tablica u bazi ako ne postoji
    public static void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS transaction_log (" +
                "datetime VARCHAR(30), " +
                "coffee_type VARCHAR(255), " +
                "action VARCHAR(255));";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Spremi transakciju u bazu podataka
    public static void saveTransaction(String dateTime, String coffeeType, String action) {
        String insertSQL = "INSERT INTO transaction_log (datetime, coffee_type, action) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, dateTime);  // Spremanje datuma
            pstmt.setString(2, coffeeType);  // Spremanje vrste kave
            pstmt.setString(3, action);     // Spremanje akcije
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ispis transakcija iz baze
    public static void printTransaction() {
        String querySQL = "SELECT * FROM transaction_log";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(querySQL)) {

            while (rs.next()) {
                String datetime = rs.getString("datetime");
                String coffeeType = rs.getString("coffee_type");
                String action = rs.getString("action");
                System.out.println("Date/time: " + datetime + ", coffee type: " + coffeeType + ", action: " + action);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void initializeCoffeeTypes() {
        CoffeeTypeDB.createTableIfNotExists(); // Kreiraj tablicu ako ne postoji

        // Provjera broja vrsti kave
        if (CoffeeTypeDB.getCoffeeTypeCount() < 3) {
            // Dodaj osnovne vrste kave
            CoffeeTypeDB.addCoffeeType("Espresso", 350, 0, 16, 4);
            CoffeeTypeDB.addCoffeeType("Latte", 350, 75, 20, 7);
            CoffeeTypeDB.addCoffeeType("Cappuccino", 200, 100, 12, 6);
            System.out.println("Initialized basic coffee types in the database.");
        } else {
            System.out.println("Basic coffee types already exist in the database.");
        }
    }
}
