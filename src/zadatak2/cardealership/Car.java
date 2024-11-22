package zadatak2.cardealership;

public class Car {
    String carName;
    String carType;
    int yearOfProduction;
    int ID;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public Car() {
    }

    public Car(String carName, String carType, int yearOfProduction) {
        this.carName = carName;
        this.carType = carType;
        this.yearOfProduction = yearOfProduction;
    }

    public void startCar(){
        System.out.println("Car has started... brm...brm");
    }

    public String getCarName() {
        return carName;
    }

    public String getCarType() {
        return carType;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }
}
