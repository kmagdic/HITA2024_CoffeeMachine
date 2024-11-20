package zadatak2.cardealership;

import java.util.ArrayList;
import java.util.List;

public class CarDealer {
    String name;
    String adress;
    int yearOfFoundation;

    List<Car> cars = new ArrayList<>();

    public CarDealer(String name, String adress, int yearOfFoundation) {
        this.name = name;
        this.adress = adress;
        this.yearOfFoundation = yearOfFoundation;
    }

    public void addCar(String carName, String carType, int yearOfProduction){
        Car car = new Car(carName, carType, yearOfProduction);
        cars.add(car);

    }

    public void printCars(){
        for(Car c: cars){
            System.out.print(c.getCarName() + ", ");
            System.out.print(c.getCarType() + ", ");
            System.out.print(c.getYearOfProduction());
            System.out.println(" ");
        }
    }

    public void deleteCar(String carToDelete){
        boolean carDeleted = false;
        for(Car c: cars){
            if (c.getCarName().equals(carToDelete)) {
                cars.remove(c);
                System.out.println("Removed: " + c.getCarName());
                carDeleted = true;
                break;
            }
        }
        if(!carDeleted){
            System.out.println("Auto nije nađen");
        }
    }
}
