package t1_darko.calculator;

import java.util.Scanner;

public class ConsoleCalculatorMain {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Calculator calculator = null;

        String selection = "";
        while (!selection.equals("3")) {
            System.out.println("1 - Basic");
            System.out.println("2 - Advanced");
            System.out.println("3 - Exit");
            System.out.print("Enter: ");

            selection = scanner.nextLine();

            String chCalculateMore = "";
            switch (selection) {
                case "1" : // basic calculator
                    calculator = new Calculator();
                    while (!chCalculateMore.equals("no")) {
                        System.out.println("Calculator operations");
                        calculator.printOperations();
                        enterNumbersAndOperation(calculator);
                        System.out.println("Result: " + calculator.calculate());
                        System.out.println("\nMore calculate with advanced calculator | Yes/No");
                        scanner.nextLine(); // clear buffer
                        chCalculateMore = scanner.nextLine().trim().toLowerCase();
                    }
                    break;
                case "2" : // advanced calculator
                    calculator = new AdvancedCalculator();
                    while (!chCalculateMore.equals("no")) {
                        System.out.println("Advanced calculator operations");
                        calculator.printOperations();
                        enterNumbersAndOperation(calculator);
                        double result = calculator.calculate();
                        System.out.println("Result: " + result);
                        System.out.println("History | Yes/No");
                        String history = scanner.next().trim().toLowerCase();
                        if (history.equals("yes")) {
                            ((AdvancedCalculator) calculator).printCalculationLogs();
                        }
                        System.out.println("\nMore calculate with advanced calculator | Yes/No");
                        scanner.nextLine(); // clear buffer
                        chCalculateMore = scanner.nextLine().trim().toLowerCase();
                    }
                    break;
                case "3": // exit the loop
                    break;
                default:
                    System.out.println("Selection not found");
            }
        }
    }

    public static void enterNumbersAndOperation(Calculator calculator) {
        System.out.print("A: ");
        calculator.setA(scanner.nextDouble());
        System.out.print("Op: ");
        calculator.setOperation(scanner.next());
        System.out.print("B: ");
        calculator.setB(scanner.nextDouble());
    }



}
