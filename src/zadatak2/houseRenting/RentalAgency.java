package zadatak2.houseRenting;


import java.util.ArrayList;

public class RentalAgency {
    public String name;
    public String address;
    public String owner;
    public ArrayList<RentingObjects> rentingObjectsList;

    public RentalAgency(String name, String address, String owner) {
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.rentingObjectsList = new ArrayList<>();
    }

    // Getter metode za name, address i owner
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getOwner() {
        return owner;
    }

    public void addRentingObject(RentingObjects obj) {
        rentingObjectsList.add(obj);
    }

    @Override
    public String toString() {
        return "RentalAgency{name='" + name + "', address='" + address + "', owner='" + owner + "', numberOfObjectsToRent=" +
                rentingObjectsList.size() + "}";
    }

    // Prikaz svih objekata za iznajmljivanje unutar agencije
    public void printRentingObjects() {
        for (RentingObjects obj : rentingObjectsList) {
            System.out.println(obj);
        }
    }
}