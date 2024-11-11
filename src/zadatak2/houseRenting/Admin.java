package zadatak2.houseRenting;

import java.util.ArrayList;

public class Admin {
    private ArrayList<RentalAgency> agencies = new ArrayList<>();

    // Metoda za dodavanje agencije koristeći njene parametre
    public void addAgency(String name, String address, String owner) {
        RentalAgency newAgency = new RentalAgency(name, address, owner);
        agencies.add(newAgency);
    }

    // Metoda za dodavanje agencije koristeći objekat RentalAgency
    public void addAgency(RentalAgency agency) {
        agencies.add(agency);
    }

    public ArrayList<RentalAgency> getAgencies() {
        return agencies;
    }

    // Logika za prijavu (provera korisničkog imena i lozinke)
    public boolean login(String username, String password) {
        return "admin".equals(username) && "admin".equals(password);
    }
}
