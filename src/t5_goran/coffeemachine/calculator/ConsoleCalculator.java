package t5_goran.coffeemachine.calculator;

import java.util.Scanner;

public class ConsoleCalculator {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.print("1 - Basic\n" +
                    "2 - Advanced\n" +
                    "3 - Exit\n" +
                    "Enter: ");
            int choice = scanner.nextInt();

            if (choice == 3) {
                System.out.println("Exiting...");
                break;
            }

            if (choice == 1) {
                handleBasicCalculator();
            } else if (choice == 2) {
                handleAdvancedCalculator();
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void handleBasicCalculator() {
        Calculator calculator = new Calculator();
        boolean moreCalculations = true;

        while (moreCalculations) {
            System.out.println("Operations!!!");
            calculator.printOperations();

            enterNumbersAndOperation(calculator);
            System.out.println("Result: " + calculator.calculate());

            moreCalculations = askToContinue("More calculate with basic calculator | Yes/No");
        }
    }

    public static void handleAdvancedCalculator() {
        AdvancedCalculator calculator = new AdvancedCalculator();
        boolean moreCalculations = true;

        while (moreCalculations) {
            System.out.println("Operations!!!");
            calculator.printOperations();

            enterNumbersAndOperation(calculator);
            System.out.println("Result: " + calculator.calculate());

            if (askToContinue("History | Yes/No")) {
                for (CalculationLog log : calculator.getHistoryLogList()) {
                    System.out.println(log);
                }
            }

            moreCalculations = askToContinue("More calculate with advanced calculator | Yes/No");
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

    public static boolean askToContinue(String message) {
        System.out.println(message);
        String response = scanner.next();
        return response.equalsIgnoreCase("Yes");
    }
}
