package zadatak2.houseRenting;
public class RentingObjects {
    public String name;
    public String type;
    public int yearOfProduction;
    public String category;
    public double priceForOneNight;
    public RentalAgency rentalAgency;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriceForOneNight(double priceForOneNight) {
        this.priceForOneNight = priceForOneNight;
    }

    public void setRentalAgency(RentalAgency rentalAgency) {
        this.rentalAgency = rentalAgency;
    }

    public RentingObjects(String name, String type, int yearOfProduction, String category, double priceForOneNight, RentalAgency rentalAgency) {
        this.name = name;
        this.type = type;
        this.yearOfProduction = yearOfProduction;
        this.category = category;
        this.priceForOneNight = priceForOneNight;
        this.rentalAgency = rentalAgency;
        rentalAgency.addRentingObject(this);  // Ensure the object is added to the agency list
    }

    @Override
    public String toString() {
        return "RentingObject{name='" + name + "', type='" + type + "', yearOfProduction=" + yearOfProduction +
                ", category='" + category + "', priceForOneNight=" + priceForOneNight +
                ", rentalAgency=" + rentalAgency.name + "}";
    }
}
