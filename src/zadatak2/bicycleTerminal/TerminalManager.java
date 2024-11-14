package zadatak2.bicycleTerminal;

import javax.management.modelmbean.ModelMBeanNotificationBroadcaster;
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
