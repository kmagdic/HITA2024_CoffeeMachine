package zadatak2.bicycleTerminal;

import java.util.Scanner;

public class TerminalManager {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter admin name: ");
        String adminName = scanner.nextLine();

        System.out.println("Enter admin last name: ");
        String lastName = scanner.nextLine();

        System.out.println("Enter admin age: ");
        int adminAge = scanner.nextInt();

//        Admin admin = new Admin(adminName, lastName, adminAge);
//
//        admin.addTerminal();

        // Create two Terminal objects
        Terminal terminal1 = new Terminal(1, "Central Terminal", 10);
        Terminal terminal2 = new Terminal(2, "West Side Terminal", 8);

        terminal1.addAddress("123 Main St.");
        terminal2.addAddress("456 West Ave.");

        // Create five Bicycle objects
        Bicycle bike1 = new Bicycle(101, "Mountain", "Red", "Full", 3);
        Bicycle bike2 = new Bicycle(102, "Road", "Blue", "Medium", 1);
        Bicycle bike3 = new Bicycle(103, "Electric", "Green", "Low", 2);
        Bicycle bike4 = new Bicycle(104, "Hybrid", "Black", "Full", 4);
        Bicycle bike5 = new Bicycle(105, "Mountain", "Yellow", "High", 5);

        // Assign bicycles to terminals
        terminal1.addBicycle(bike1);
        terminal1.addBicycle(bike2);
        terminal2.addBicycle(bike3);
        terminal2.addBicycle(bike4);
        terminal2.addBicycle(bike5);

        // Output information using toString() methods
        System.out.println(terminal1);
        System.out.println(terminal2);
    }
}
