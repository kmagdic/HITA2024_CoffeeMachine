package zadatak2.houseRenting;



import java.util.ArrayList;

public class RentalAgency {
    public String name;
    public String address;
    public String owner;
    public ArrayList<RentingObjects> rentingObjectsList;

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setRentingObjectsList(ArrayList<RentingObjects> rentingObjectsList) {
        this.rentingObjectsList = rentingObjectsList;
    }

    public RentalAgency(String name, String address, String owner) {
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.rentingObjectsList = new ArrayList<>();
    }

    public void addRentingObject(RentingObjects obj) {
        rentingObjectsList.add(obj);
    }

    @Override
    public String toString() {
        return "RentalAgency{name='" + name + "', address='" + address + "', owner='" + owner + "', numberOfObjectsToRent=" +
                rentingObjectsList.size() + "}";
    }

    // Optionally, print all objects managed by this agency
    public void printRentingObjects() {
        for (RentingObjects obj : rentingObjectsList) {
            System.out.println(obj);
        }
    }

    public String getName() {
        return name;
    }
}
