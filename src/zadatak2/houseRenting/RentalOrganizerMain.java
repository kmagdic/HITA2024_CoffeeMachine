package zadatak2.houseRenting;

import java.util.Scanner;

public class RentalOrganizerMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();

        // Admin login
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (!admin.login(username, password)) {
            System.out.println("Incorrect username or password. Exiting program.");
            return;
        }

        System.out.println("Login successful. Welcome!");

        // Create initial rental agencies and add them to the admin
        RentalAgency marinAgency = new RentalAgency("Marin Agency", "Ludbreg 4220", "Marin");
        RentalAgency goranTourism = new RentalAgency("Goran Tourism", "Sisak 3600", "Goran");

        admin.addAgency(marinAgency.getName(), marinAgency.getAddress(), marinAgency.getOwner());
        admin.addAgency(goranTourism.getName(), goranTourism.getAddress(), goranTourism.getOwner());

        // Create rental objects and add them to the agencies
        RentingObjects draga = new RentingObjects("Draga", "Apartment", 2020, "standard", 100, marinAgency);
        RentingObjects marija = new RentingObjects("Marija", "House", 2021, "standard", 150, marinAgency);
        RentingObjects camper = new RentingObjects("Camper", "Vehicle", 2021, "electric", 80, marinAgency);
        RentingObjects vilaParadise = new RentingObjects("Villa Paradise", "House", 2024, "standard", 150, goranTourism);
        RentingObjects adrijana = new RentingObjects("Adrijana", "Apartment", 2024, "standard", 180, goranTourism);

        // Main menu for agency management
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View all agencies");
            System.out.println("2. Add a new agency");
            System.out.println("3. Delete an agency");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Display all agencies
                    admin.displayAgencies();
                    break;

                case 2:
                    // Add a new agency
                    System.out.print("Enter agency name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter agency address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter agency owner: ");
                    String owner = scanner.nextLine();
                    admin.addAgency(name, address, owner);
                    System.out.println("New agency added successfully.");
                    break;

                case 3:
                    // Delete an agency
                    System.out.print("Enter the name of the agency to delete: ");
                    String agencyName = scanner.nextLine();
                    if (admin.removeAgency(agencyName)) {
                        System.out.println("Agency '" + agencyName + "' deleted successfully.");
                    } else {
                        System.out.println("Agency not found.");
                    }
                    break;

                case 4:
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Unknown option. Please try again.");
            }
        }
    }
}

