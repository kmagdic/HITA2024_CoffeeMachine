package zadatak2.ticketmachine;

import java.util.List;
import java.util.Scanner;

public class ConsoleMain {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        // Kreiramo administratora s korisničkim imenom i lozinkom
        Admin admin = new Admin("Adam", "Admin", "admin", "admin");

        int choice;
        String userName, password;

        while (true) {
            System.out.println("**");
            System.out.println("1 - Login as Admin");
            System.out.println("2 - Exit");
            System.out.println("**");
            choice = s.nextInt();

            if (choice == 1) {
                System.out.print("User name: ");
                userName = s.next();
                System.out.print("Password: ");
                password = s.next();

                // Provjera prijave
                if (admin.checkUsernameAndPassword(userName, password)) {
                    System.out.println("Login successful!");

                    // Admin izbornik
                    while (true) {
                        System.out.println("**");
                        System.out.println("Admin Menu:");
                        System.out.println("1 - Add new Terminal");
                        System.out.println("2 - View all Terminals");
                        System.out.println("3 - Logout");
                        System.out.println("**");

                        int adminChoice = s.nextInt();

                        if (adminChoice == 1) {
                            // Dodavanje novog terminala
                            System.out.print("Enter terminal name: ");
                            String terminalName = s.next();
                            System.out.print("Enter terminal address: ");
                            String terminalAddress = s.next();
                            Terminal newTerminal = new Terminal();
                            newTerminal.name = terminalName;
                            newTerminal.address = terminalAddress;

                            admin.addTerminal(newTerminal); // Dodavanje terminala u listu
                            System.out.println("Terminal " + terminalName + " added successfully.");
                        } else if (adminChoice == 2) {
                            // Ispis svih terminala
                            List<Terminal> terminals = admin.getTerminals();
                            if (terminals.isEmpty()) {
                                System.out.println("No terminals available.");
                            } else {
                                System.out.println("All Terminals:");
                                for (Terminal terminal : terminals) {
                                    System.out.println(terminal);
                                }
                            }
                        } else if (adminChoice == 3) {
                            // Logout
                            System.out.println("Logging out...");
                            break;
                        } else {
                            System.out.println("Invalid option. Please try again.");
                        }
                    }
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else if (choice == 2) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        s.close();
    }
}
