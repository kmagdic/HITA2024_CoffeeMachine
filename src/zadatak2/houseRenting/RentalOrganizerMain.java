package zadatak2.houseRenting;

import java.util.Scanner;

public class RentalOrganizerMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();

        // Admin login
        System.out.print("Unesite korisničko ime: ");
        String username = scanner.nextLine();
        System.out.print("Unesite lozinku: ");
        String password = scanner.nextLine();

        if (!admin.login(username, password)) {
            System.out.println("Pogrešno korisničko ime ili lozinka. Program se zatvara.");
            return;
        }

        System.out.println("Uspješna prijava. Dobrodošli!");

        // Predefinisani objekti za najam
        admin.addRentingObject(new RentingObjects(1, "Draga", "Stan", 2020, "Standard", 100));
        admin.addRentingObject(new RentingObjects(2, "Marija", "Kuća", 2021, "Standard", 150));
        admin.addRentingObject(new RentingObjects(3, "Kemper", "Vozilo", 2021, "Električni", 80));
        admin.addRentingObject(new RentingObjects(4, "Vila Paradise", "Kuća", 2024, "Standard", 150));
        admin.addRentingObject(new RentingObjects(5, "Adrijana", "Stan", 2024, "Standard", 180));

        // Glavni meni za upravljanje objektima
        while (true) {
            System.out.println("\nOdaberite opciju:");
            System.out.println("1. Prikaz svih objekata za najam");
            System.out.println("2. Dodavanje novog objekta za najam");
            System.out.println("3. Brisanje objekta za najam");
            System.out.println("4. Izlaz");

            int izbor = scanner.nextInt();
            scanner.nextLine();

            switch (izbor) {
                case 1:
                    // Prikaz svih objekata za najam
                    admin.displayRentingObjects();
                    break;

                case 2:
                    // Dodavanje novog objekta za najam
                    System.out.print("Unesite naziv objekta: ");
                    String naziv = scanner.nextLine();
                    System.out.print("Unesite tip objekta: ");
                    String tip = scanner.nextLine();
                    System.out.print("Unesite godinu proizvodnje: ");
                    int godina = scanner.nextInt();
                    scanner.nextLine(); // Potroši novi red
                    System.out.print("Unesite kategoriju: ");
                    String kategorija = scanner.nextLine();
                    System.out.print("Unesite cijenu po noćenju: ");
                    double cijena = scanner.nextDouble();
                    scanner.nextLine(); // Potroši novi red

                    RentingObjects noviObjekt = new RentingObjects();
                    admin.addRentingObject(noviObjekt);
                    System.out.println("Novi objekt uspješno dodan.");
                    break;

                case 3:
                    // Brisanje objekta za najam
                    System.out.print("Unesite naziv objekta za brisanje: ");
                    String imeZaBrisanje = scanner.nextLine();
                    if (admin.removeRentingObject(imeZaBrisanje)) {
                        System.out.println("Objekt '" + imeZaBrisanje + "' uspješno obrisan.");
                    } else {
                        System.out.println("Objekt nije pronađen.");
                    }
                    break;

                case 4:
                    System.out.println("Izlaz iz programa.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Nepoznata opcija. Pokušajte ponovo.");
            }
        }
    }
}
