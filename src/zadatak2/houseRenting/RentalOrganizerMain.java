package zadatak2.houseRenting;




import java.util.ArrayList;
import java.util.Scanner;

public class RentalOrganizerMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();

        // Admin autentifikacija pri pokretanju programa
        System.out.println("Unesite korisničko ime: ");
        String username = scanner.nextLine();
        System.out.println("Unesite lozinku: ");
        String password = scanner.nextLine();

        if (!admin.login(username, password)) {
            System.out.println("Pogrešno korisničko ime ili lozinka. Program se završava.");
            return; // Izlazak iz programa
        }

        System.out.println("Prijava uspešna. Dobrodošli!");

        // Dodajemo početne agencije u admin listu koristeći objekat RentalAgency
        RentalAgency marinAgencija = new RentalAgency("Marin Agencija", "Ludbreg 4220", "Marin");
        RentalAgency goranTurizam = new RentalAgency("Goran Turizam", "Sisak 3600", "Goran");
        admin.addAgency(marinAgencija);
        admin.addAgency(goranTurizam);

        // Interaktivni deo programa za dodavanje i prikaz agencija
        while (true) {
            System.out.println("Odaberite opciju:");
            System.out.println("1. Dodaj novu agenciju");
            System.out.println("2. Prikaži sve agencije");
            System.out.println("3. Prikaži objekte za iznajmljivanje u svim agencijama");
            System.out.println("4. Izlaz");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Očisti liniju za unos

            if (choice == 1) {
                System.out.println("Unesite ime agencije: ");
                String name = scanner.nextLine();
                System.out.println("Unesite adresu agencije: ");
                String address = scanner.nextLine();
                System.out.println("Unesite vlasnika agencije: ");
                String owner = scanner.nextLine();

                admin.addAgency(name, address, owner);
                System.out.println("Nova agencija uspešno dodana.");
            } else if (choice == 2) {
                ArrayList<RentalAgency> agencies = admin.getAgencies();
                if (agencies.isEmpty()) {
                    System.out.println("Nema dostupnih agencija.");
                } else {
                    System.out.println("Prikaz svih agencija:");
                    for (RentalAgency agency : agencies) {
                        System.out.println(agency);
                    }
                }
            } else if (choice == 3) {
                ArrayList<RentalAgency> agencies = admin.getAgencies();
                for (RentalAgency agency : agencies) {
                    System.out.println("Agencija: " + agency.getName());
                    agency.printRentingObjects();
                }
            } else if (choice == 4) {
                System.out.println("Izlazak iz programa.");
                break;
            } else {
                System.out.println("Nepoznata opcija. Pokušajte ponovo.");
            }
        }

        scanner.close();
    }
}
