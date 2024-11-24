package zadatak2.houseRenting;

import java.util.ArrayList;

public class Admin {
    private final String username = "admin";
    private final String password = "admin";
    private ArrayList<RentingObjects> rentingObjects = new ArrayList<>(); // Lista objekata za najam

    // Metoda za prijavu
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    // Dodavanje novog objekta za najam
    public void addRentingObject(RentingObjects rentingObject) {
        rentingObjects.add(rentingObject);
        System.out.println("Objekt '" + rentingObject.getName() + "' uspješno dodan.");
    }

    // Brisanje objekta za najam prema nazivu
    public boolean removeRentingObject(String name) {
        for (RentingObjects object : rentingObjects) {
            if (object.getName().equalsIgnoreCase(name)) {
                rentingObjects.remove(object);
                return true;
            }
        }
        return false;
    }

    // Prikaz svih objekata za najam
    public void displayRentingObjects() {
        if (rentingObjects.isEmpty()) {
            System.out.println("Nema dostupnih objekata za najam.");
        } else {
            System.out.println("Lista svih objekata za najam:");
            for (RentingObjects object : rentingObjects) {
                System.out.println(object);
            }
        }
    }

    // Getter za listu objekata (ako je potrebno eksterno koristiti)
    public ArrayList<RentingObjects> getRentingObjects() {
        return rentingObjects;
    }
}
