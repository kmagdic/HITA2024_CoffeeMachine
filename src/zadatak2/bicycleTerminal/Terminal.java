package zadatak2.bicycleTerminal;
import java.util.ArrayList;
import java.util.List;

public class Terminal {
    private int id;
    private String name;
    private List<Bicycle> bicycles;
    private String address;
    private int maxCapacity;
    private boolean isOpen;

    public Terminal(int id, String name, int maxCapacity) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.isOpen = true;
        this.bicycles = new ArrayList<>();
    }

    public void addAddress(String newAddress) {
        this.address = newAddress;
        System.out.println("Address added to Terminal " + id + ": " + newAddress);
    }

    public void removeAddress(String addressToRemove) {
        if (this.address != null && this.address.equals(addressToRemove)) {
            this.address = null;
            System.out.println("Address removed from Terminal " + id);
        } else {
            System.out.println("Address does not match or not set, cannot remove.");
        }
    }

    public boolean addBicycle(Bicycle bicycle) {
        if (bicycles.size() < maxCapacity) {
            bicycles.add(bicycle);
            System.out.println("Bicycle " + bicycle + " added to Terminal " + id);
            return true;
        } else {
            System.out.println("Terminal " + id + " is at max capacity.");
            return false;
        }
    }

    public boolean removeBicycle(Bicycle bicycle) {
        if (bicycles.remove(bicycle)) {
            System.out.println("Bicycle " + bicycle + " removed from Terminal " + id);
            return true;
        } else {
            System.out.println("Bicycle not found at Terminal " + id);
            return false;
        }
    }

    @Override
    public String toString() {
        return "Terminal [ID=" + id + ", Name=" + name + ", Bicycles=" + bicycles.size() +
                ", Address=" + address + ", MaxCapacity=" + maxCapacity + ", IsOpen=" + isOpen + "]";
    }
}
