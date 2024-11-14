package zadatak2.houseRenting;

import java.util.ArrayList;

public class Admin {
    private final String username = "admin";
    private final String password = "admin";
    private ArrayList<RentalAgency> agencies = new ArrayList<>();

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void addAgency(String name, String address, String owner) {
        RentalAgency agency = new RentalAgency(name, address, owner);
        agencies.add(agency);
    }

    public  boolean removeAgency(String name) {
        for (RentalAgency agency : agencies) {
            if (agency.getName().equalsIgnoreCase(name)) {
                agencies.remove(agency);
                return true;
            }
        }
        return false;
    }
    public ArrayList<RentalAgency> getAgencies() {
        return agencies;
    }
    public void displayAgencies() {
        if (agencies.isEmpty()) {
            System.out.println("No rental agencies available.");
        } else {
            System.out.println("List of all rental agencies: ");
            for (RentalAgency agency : agencies) {
                System.out.println(agency);
            }
        }
    }
}
