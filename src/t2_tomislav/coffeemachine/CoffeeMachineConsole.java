package t2_tomislav.coffeemachine;

import java.util.List;
import java.util.Scanner;

public class CoffeeMachineConsole {

    // Scanner za unos korisnika
    private final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicijalizacija baze podataka pri pokretanju programa
        H2DatabaseManager dbManager = new H2DatabaseManager("C:/Tomislav/coffee_machine");
        // Kreira instancu CoffeeMachineConsole i pokreće glavni program
        CoffeeMachineConsole console = new CoffeeMachineConsole();
        console.run();
    }

    void run() {
        // Inicijalizacija aparata s početnim resursima i putanjom do datoteke statusa
        CoffeeMachineWithStatusInFile machine = new CoffeeMachineWithStatusInFile(
                400, 540, 120, 9, 550, "docs/coffee_machine_status.txt"
        );

        // Prikazuje uvodnu poruku
        System.out.println("Welcome to Coffee Machine 2.1 version by Tomislav");

        // Pokušava učitati status iz datoteke
        boolean startedSuccessfully = machine.loadFromFile();
        if (!startedSuccessfully) {
            // Ako datoteka nije pronađena, koristi zadane vrijednosti
            System.out.println("Coffee machine started but without file. Using default values.");
        }

        String action = "";

        // Glavna petlja programa koja omogućava interakciju s korisnikom
        while (!action.equals("exit")) {
            // Prikazuje dostupne akcije korisniku
            System.out.println("Write action (buy, login, log, exit): ");
            action = sc.next();

            // Odabir korisničke akcije prema unesenoj opciji
            switch (action) {
                case "buy":
                    // Kupovina kave
                    buyAction(machine);
                    break;

                case "login":
                    // Prijava kao admin korisnik
                    loginAction(machine);
                    break;

                case "log":
                    // Ispis logova transakcija
                    printTransactionLogs(machine);
                    break;

                case "exit":
                    // Spremanje trenutnog statusa u datoteku i izlazak
                    machine.saveToFile();
                    System.out.println("Shutting down the machine. Bye!");
                    break;

                default:
                    // Poruka za nevažeći unos
                    System.out.println("Invalid action. Please try again.");
            }
        }
    }

    private void buyAction(CoffeeMachine machine) {
        // Prikazuje dostupne vrste kave
        System.out.println("Choose a coffee:");
        CoffeeType[] coffeeTypes = machine.getCoffeeTypes();
        for (int i = 0; i < coffeeTypes.length; i++) {
            System.out.println((i + 1) + " - " + coffeeTypes[i].getName());
        }
        System.out.println("Enter your choice:");

        // Provjerava je li korisnikov unos valjan broj
        int typeOfCoffeeChoice = getValidIntegerInput();
        if (typeOfCoffeeChoice >= 1 && typeOfCoffeeChoice <= coffeeTypes.length) {
            // Kupuje odabranu kavu i ispisuje rezultat
            String msg = machine.buyCoffee(coffeeTypes[typeOfCoffeeChoice - 1]);
            System.out.println(msg);
        } else {
            // Poruka za nevažeći unos
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private void loginAction(CoffeeMachine machine) {
        // Traži unos korisničkog imena
        System.out.println("Enter username: ");
        String username = sc.next();
        // Traži unos lozinke
        System.out.println("Enter password: ");
        String password = sc.next();

        // Provjera ispravnosti prijave
        if (machine.login(username, password)) {
            // Ako je prijava uspješna, pokreće admin izbornik
            System.out.println("Login successful. Welcome, admin!");
            adminMenu(machine);
        } else {
            // Poruka za pogrešnu prijavu
            System.out.println("Wrong username or password.");
        }
    }

    private void adminMenu(CoffeeMachine machine) {
        String action = "";
        // Petlja za admin funkcionalnosti
        while (!action.equals("exit")) {
            // Prikazuje dostupne admin akcije
            System.out.println("Admin actions: (fill, remaining, take, password, log, exit):");
            action = sc.next();

            // Obrada odabrane akcije
            switch (action) {
                case "fill":
                    // Punjenje resursa aparata
                    fillMachine(machine);
                    break;

                case "remaining":
                    // Ispis trenutnog stanja aparata
                    System.out.println(machine.toString());
                    break;

                case "take":
                    // Uzimanje novca iz aparata
                    float amount = machine.takeMoney();
                    System.out.println("I gave you $" + amount);
                    break;

                case "password":
                    // Promjena administratorske lozinke
                    changePassword(machine);
                    break;

                case "log":
                    // Ispis logova transakcija
                    printTransactionLogs(machine);
                    break;

                case "exit":
                    // Izlazak iz admin izbornika
                    System.out.println("Exiting admin menu.");
                    break;

                default:
                    // Poruka za nevažeći unos
                    System.out.println("Invalid action. Please try again.");
            }
        }
    }

    private void fillMachine(CoffeeMachine machine) {
        // Traži unos količine vode za punjenje
        System.out.println("Enter how many ml of water you want to add:");
        int water = getValidIntegerInput();
        // Traži unos količine mlijeka za punjenje
        System.out.println("Enter how many ml of milk you want to add:");
        int milk = getValidIntegerInput();
        // Traži unos količine zrna kave za punjenje
        System.out.println("Enter how many grams of coffee beans you want to add:");
        int coffeeBeans = getValidIntegerInput();
        // Traži unos broja čaša za punjenje
        System.out.println("Enter how many disposable cups you want to add:");
        int cups = getValidIntegerInput();
        // Punjenje aparata s unesenim resursima
        machine.fill(water, milk, coffeeBeans, cups);
        System.out.println("Machine refilled successfully.");
    }

    private void changePassword(CoffeeMachine machine) {
        // Traži unos nove administratorske lozinke
        System.out.println("Enter new admin password:");
        String newPassword = sc.next();
        // Pokušava promijeniti lozinku i ispisuje rezultat
        if (machine.changeAdminPassword(newPassword)) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Password must be at least 7 characters long and contain at least one number.");
        }
    }

    private void printTransactionLogs(CoffeeMachine machine) {
        // Traži izbor tipa logova (lokalni ili baza)
        System.out.println("Choose log type: (1 - Local, 2 - Database)");
        int choice = getValidIntegerInput();

        if (choice == 1) {
            // Prikaz lokalnih logova
            List<TransactionLog> logs = machine.getHistoryLogList();
            if (logs.isEmpty()) {
                System.out.println("No local logs available.");
            } else {
                System.out.println("Local Transaction Logs:");
                for (TransactionLog log : logs) {
                    System.out.println(log);
                }
            }
        } else if (choice == 2) {
            // Prikaz logova iz baze podataka
            H2DatabaseManager dbManager = new H2DatabaseManager("C:/Tomislav/coffee_machine");
            List<TransactionLog> logs = dbManager.getTransactionLogs();
            if (logs.isEmpty()) {
                System.out.println("No database logs available.");
            } else {
                System.out.println("Database Transaction Logs:");
                for (TransactionLog log : logs) {
                    System.out.println(log);
                }
            }
        } else {
            // Poruka za nevažeći izbor
            System.out.println("Invalid choice.");
        }
    }

    private int getValidIntegerInput() {
        // Ponavlja unos dok ne dobije valjani cijeli broj
        while (true) {
            try {
                return sc.nextInt();
            } catch (Exception e) {
                // Poruka za nevažeći unos
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // Čišćenje unosa
            }
        }
    }
}
