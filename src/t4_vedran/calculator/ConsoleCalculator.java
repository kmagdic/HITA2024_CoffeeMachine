package t4_vedran.calculator;

import java.util.Scanner;

public class ConsoleCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Prikaz opcija za korisnika
            System.out.println("1 - Basic");
            System.out.println("2 - Advanced");
            System.out.println("3 - Exit");
            System.out.print("Enter: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                BasicCalculator basicCalculator = new BasicCalculator();
                boolean continueBasic = true;
                while (continueBasic) {
                    System.out.println("Operations!!!");
                    basicCalculator.printOperations();

                    System.out.print("\nA: ");
                    double a = scanner.nextDouble();
                    System.out.print("Op: ");
                    String op = scanner.next();
                    System.out.print("B: ");
                    double b = scanner.nextDouble();

                    basicCalculator.setA(a);
                    basicCalculator.setB(b);
                    basicCalculator.setOperation(op);

                    double result = basicCalculator.calculate();
                    System.out.println("Result: " + result);

                    // Provjera da li korisnik želi nastaviti
                    continueBasic = basicCalculator.moreCalculations(scanner);
                }
            } else if (choice == 2) {
                AdvancedCalculator advancedCalculator = new AdvancedCalculator();
                boolean continueAdvanced = true;
                while (continueAdvanced) {
                    advancedCalculator.printOperations();

                    System.out.print("\nA: ");
                    double a = scanner.nextDouble();
                    System.out.print("Op: ");
                    String op = scanner.next();
                    System.out.print("B: ");
                    double b = scanner.nextDouble();

                    advancedCalculator.setA(a);
                    advancedCalculator.setB(b);
                    advancedCalculator.setOperation(op);

                    double result = advancedCalculator.calculate();
                    System.out.println(result);

                    // Ispis povijesti
                    System.out.print("History | Yes/No: ");
                    String showHistory = scanner.next();
                    if (showHistory.equalsIgnoreCase("Yes")) {
                        advancedCalculator.printHistory();
                    }

                    // Provjera da li korisnik želi nastaviti
                    continueAdvanced = advancedCalculator.moreCalculations(scanner);
                }
            } else if (choice == 3) {
                running = false;  // Izlazak iz petlje i završetak programa
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }
}
