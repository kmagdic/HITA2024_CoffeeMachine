package zadatak2.cardealership;

import java.util.Scanner;

public class CarMain {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        CarDealer tomic = new CarDealer("Tomic", "Zagreb", 1990);




        while(true) {
            System.out.println("1 - Dodaj auto\n2 - Ispiši aute");
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
                tomic.printCars();

            } else if (choice == 3) {
                break;
            }
        }

    }
}