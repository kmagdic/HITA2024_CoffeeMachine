package zadatak2.cardealership;



import _karlo_dragan.studentmanager.EventRepository;
import _karlo_dragan.studentmanager.StudentRepository;

import java.util.Scanner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static _karlo_dragan.studentmanager.StudentManagerSimpleMain.makeDBConnection;
import static zadatak2.cardealership.CarRepository.*;

public class CarMain {



    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Connection connection = makeDBConnection("src/zadatak2/cardealership/cars");
        CarRepository carRepository = new CarRepository(connection);
        carRepository.createTable();



        while(true) {
            System.out.println("1 - Dodaj auto\n2 - Izbriši auto\n3 - Ispiši aute\n4 - Izlaz");
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("Unesi ime auta:");
                String carName = scanner.next();
                System.out.println("Unesi tip auta:");
                String carType = scanner.next();
                System.out.println("Unesi godinu proizvodnje: ");
                int yearOfProduction = scanner.nextInt();

                Car newCar = new Car(carName,carType,yearOfProduction);



                carRepository.insertCar(newCar);

            } else if (choice == 2) {
                System.out.println("Koji auto želiš izbrisati");
                String carToDelete = scanner.next();
                //tomic.deleteCar(carToDelete);

            } else if (choice == 3) {
                int counter = 1;
                for(Car c: getList()){
                    System.out.println("Car " + counter);
                    System.out.println("Name: " + c.getCarName());
                    System.out.println("Type: " + c.getCarType());
                    System.out.println("Year: " + c.getYearOfProduction());
                    System.out.println("*****************");
                    counter ++;
                }

            } else if (choice == 4) {
                break;
            }
        }

    }
}