package zadatak2.cardealership;

import java.util.Scanner;

public class CarMain {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CarDealer tomic = new CarDealer("Tomic", "Zagreb", 1990);




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
                tomic.addCar(carName, carType, yearOfProduction);
            } else if (choice == 2) {
                System.out.println("Koji auto želiš izbrisati");
                String carToDelete = scanner.next();
                tomic.deleteCar(carToDelete);

            } else if (choice == 3) {
                tomic.printCars();

            } else if (choice == 4) {
                break;
            }
        }

    }
}