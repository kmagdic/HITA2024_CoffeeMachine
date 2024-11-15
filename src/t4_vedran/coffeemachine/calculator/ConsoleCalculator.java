package t4_vedran.coffeemachine.calculator;

import java.util.Scanner;

public class ConsoleCalculator {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Calculator calculator = new Calculator();

        System.out.println("Operacije osnovnog kalkultora:");

        while (true) {
            String choice;
            System.out.println("1- Calculate");
            System.out.println("2 - Exit");
            System.out.print("Enter: ");
            choice = scanner.next();

            if (choice.equals("2")) {
                System.out.println("Exiting...");
                break;
            }

            if (choice.equals("1")) {
                enterNumbersAndOperation(calculator);
            }
        }
    }


    public static void enterNumbersAndOperation(Calculator calculator) {
        System.out.print("A: ");
        double a = scanner.nextDouble();
        calculator.setA(a);

        System.out.print("Op: ");
        String op = scanner.next();
        calculator.setOperation(op);

        System.out.print("B: ");
        double b = scanner.nextDouble();
        calculator.setB(b);


        double result = calculator.calculate();
        if (!Double.isNaN(result)) {
            System.out.println("Rezultat: " + result);
        }
    }
}
