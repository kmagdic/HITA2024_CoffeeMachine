package zadatak2.cardealership;

public class Car {
    String carName;
    String carType;
    int yearOfProduction;

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
