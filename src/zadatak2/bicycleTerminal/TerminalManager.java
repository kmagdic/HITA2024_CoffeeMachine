package zadatak2.bicycleTerminal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TerminalManager {

    public static void main(String[] args) {

        // Db setup
        Connection connection = makeDBConnection("docs/tesbojan");

        BicycleRepository bicycleRepository = new BicycleRepository(connection);
        TerminalRepository terminalRepository = new TerminalRepository(connection);
        bicycleRepository.createTable();
        terminalRepository.createTable();

        // add a bicycle
        Bicycle bicycle = new Bicycle(101, "Mountain", "Red", "Full", 3);
        Bicycle bicycle2 = new Bicycle(102, "Road", "Blue", "Medium", 1);
        bicycleRepository.insert(bicycle);
        bicycleRepository.insert(bicycle2);

        // get bicycles
        List<Bicycle> bicycles = bicycleRepository.getList();
        for (Bicycle b : bicycles) {
            System.out.println(b);
        }

        // add terminal
        Terminal terminal = new Terminal(1, "Central Terminal", 10);
        Terminal terminal2 = new Terminal(2, "West Side Terminal", 8);
        terminalRepository.insert(terminal);
        terminalRepository.insert(terminal2);

        // get terminals
        List<Terminal> terminals = terminalRepository.getList();
        for (Terminal t : terminals) {
            System.out.println(t);
        }
    }

    private static Connection makeDBConnection(String DB) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + DB);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void oldStuff() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter admin name: ");
        String adminName = scanner.nextLine();

        System.out.println("Enter admin last name: ");
        String lastName = scanner.nextLine();

        System.out.println("Enter admin age: ");
        int adminAge = scanner.nextInt();

        // Create admin
        Admin admin = new Admin(adminName, lastName, adminAge);


        // Create and add two Terminal objects
        admin.addTerminal(1, "Central Terminal", 10);
        admin.addTerminal(2, "West Side Terminal", 8);

        admin.addAddress(1, "123 Main St.");
        admin.addAddress(2, "456 West Ave.");

        // Create five Bicycle objects and assign them to terminals
        admin.addBicycleToTerminal(1, new Bicycle(101, "Mountain", "Red", "Full", 3));
        admin.addBicycleToTerminal(1, new Bicycle(102, "Road", "Blue", "Medium", 1));
        admin.addBicycleToTerminal(2, new Bicycle(103, "Electric", "Green", "Low", 2));
        admin.addBicycleToTerminal(2, new Bicycle(104, "Hybrid", "Black", "Full", 4));
        admin.addBicycleToTerminal(2, new Bicycle(105, "Mountain", "Yellow", "High", 5));

        admin.addBicycle(new Bicycle(106, "Mountain", "Red", "Full", 3));
        admin.addBicycle(new Bicycle(107, "Road", "Blue", "Medium", 1));
        admin.addBicycle(new Bicycle(108, "Electric", "Green", "Low", 2));
        admin.addBicycle(new Bicycle(109, "Hybrid", "Black", "Full", 4));
        admin.addBicycle(new Bicycle(110, "Mountain", "Yellow", "High", 5));

        admin.addTerminal(3, "East Side Terminal", 6);

        admin.addBicycleByTypeToTerminal(3, "Mountain");
        admin.addBicycleByTypeToTerminal(1, "Road");
        admin.addBicycleByIdToTerminal(2, 109);
        admin.addBicycleByIdToTerminal(1, 110);

        admin.printBicycles();
        admin.printTerminals();

        admin.removeBicycleByType("Mountain");

        admin.printBicycles();

        admin.removeBicycleById(102);

        admin.printBicycles();

        admin.removeTerminalById(2);

        admin.printTerminals();
    }
}
