package t2_patricija.calculator;

import java.util.Scanner;

public class ConsoleMain {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Calculator calculator = null;

        while (true) {
            System.out.print("1 - Basic\n2 - Advanced\n3 - Exit\nEnter: ");
            int ch = scanner.nextInt();

            if (ch == 1) {
                calculator = new Calculator();
                System.out.println("Operacije osnovnog kalkultora:");
                calculator.printOperations();
                enterNumbersAndOperation(calculator);
                System.out.println("Result: " + calculator.calculate());

            } else if (ch == 2) {
                calculator = new AdvancedCalculator();
                System.out.println("Operacije naprednog kalkultora:");
                calculator.printOperations();
                enterNumbersAndOperation(calculator);
                System.out.println("Result: " + calculator.calculate());
            } else if (ch == 3) {
                break;
            } else {
                System.out.println("Incorrect operation.");
            }
        }
    }

    public static void enterNumbersAndOperation(Calculator c) {
        System.out.print("A: ");
        c.setA(scanner.nextDouble());
        System.out.print("Op: ");
        c.setOperation(scanner.next());
        System.out.print("B: ");
        c.setB(scanner.nextDouble());
    }
}