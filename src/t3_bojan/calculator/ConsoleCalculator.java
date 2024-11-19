package t3_bojan.calculator;

import java.util.Scanner;

public class ConsoleCalculator {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Calculator calculator;

        while (true) {
            String choice;
            System.out.println("1 - Simple Calculator");
            System.out.println("2 - Advanced Calculator");
            System.out.println("3 - Exit");
            System.out.print("Enter:");
            choice = scanner.next();

            if (choice.equals("3")) {
                break;
            }
            else if (choice.equals("1")) {
                calculator = new Calculator();
                enterNumbersAndOperation(calculator);
            }
            else if (choice.equals("2")) {
                calculator = new AdvancedCalculator();
                enterNumbersAndOperation(calculator);
                calculator.printOperations();
            }
        }


    }

    public static void enterNumbersAndOperation (Calculator calculator){

        System.out.print("A: ");
        calculator.setA(scanner.nextInt());
        System.out.print("Op: ");
        calculator.setOperation(scanner.next());
        System.out.print("B: ");
        calculator.setB(scanner.nextInt());
        System.out.println(calculator.calculate());
    }
}
