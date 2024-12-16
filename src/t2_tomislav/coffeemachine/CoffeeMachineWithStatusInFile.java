package t2_tomislav.coffeemachine;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CoffeeMachineWithStatusInFile extends CoffeeMachine {

    // Naziv datoteke u kojoj se čuvaju podaci o statusu aparata
    private final String statusFileName;

    // Konstruktor koji inicijalizira CoffeeMachine i postavlja naziv datoteke
    public CoffeeMachineWithStatusInFile(int water, int milk, int coffeeBeans, int cups, float money, String statusFileName) {
        super(water, milk, coffeeBeans, cups, money); // Poziva konstruktor roditeljske klase CoffeeMachine
        this.statusFileName = statusFileName; // Sprema naziv datoteke
    }

    // Metoda za učitavanje statusa aparata iz datoteke
    public boolean loadFromFile() {
        try {
            File file = new File(statusFileName); // Kreira File objekt za zadanu datoteku
            if (!file.exists()) { // Provjerava postoji li datoteka
                System.out.println("File not found. Using default values.");
                saveToFile(); // Ako datoteka ne postoji, sprema trenutne vrijednosti u novu datoteku
                return true; // Vraća true jer koristi zadane vrijednosti
            }
            FileReader reader = new FileReader(file); // Otvara datoteku za čitanje
            Scanner fileScanner = new Scanner(reader); // Scanner za čitanje podataka iz datoteke
            fileScanner.useDelimiter("; |\\n"); // Postavlja delimiter za razdvajanje podataka u datoteci

            // Postavlja vrijednosti resursa aparata učitane iz datoteke
            setWater(fileScanner.nextInt());
            setMilk(fileScanner.nextInt());
            setCoffeeBeans(fileScanner.nextInt());
            setCups(fileScanner.nextInt());
            setMoney(Float.parseFloat(fileScanner.next()));
            reader.close(); // Zatvara čitač datoteke
            return true; // Učitavanje je uspješno
        } catch (IOException e) {
            // Ispisuje grešku ako dođe do problema s čitanjem datoteke
            System.out.println("Error while loading file: " + e.getMessage());
            return false; // Učitavanje nije uspjelo
        } catch (Exception e) {
            // Ispisuje grešku ako format podataka u datoteci nije ispravan
            System.out.println("Error in file format: " + e.getMessage());
            return false; // Učitavanje nije uspjelo
        }
    }

    // Metoda za spremanje trenutnog statusa aparata u datoteku
    public void saveToFile() {
        try {
            File file = new File(statusFileName); // Kreira File objekt za zadanu datoteku
            // Provjerava postoji li direktorij i pokušava ga kreirati ako ne postoji
            if (file.getParentFile() != null && !file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                System.out.println("Failed to create directories for file: " + statusFileName);
            }
            // Ako datoteka ne postoji, kreira novu datoteku
            if (!file.exists() && file.createNewFile()) {
                System.out.println("File successfully created: " + statusFileName);
            }
            FileWriter writer = new FileWriter(file); // Otvara datoteku za pisanje
            // Sprema vrijednosti resursa aparata u datoteku
            writer.write(getWater() + "; " + getMilk() + "; " + getCoffeeBeans() + "; " + getCups() + "; " + getMoney() + "\n");
            writer.close(); // Zatvara zapisivač
        } catch (IOException e) {
            // Ako dođe do greške pri spremanju, baca iznimku
            throw new RuntimeException("Error saving file: " + e.getMessage());
        }
    }
}
