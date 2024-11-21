package t5_goran.calculator;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleCalculator {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Calculator basicCalculator = new Calculator();
        AdvancedCalculator advancedCalculator = new AdvancedCalculator();

        while (true) {
            System.out.println("1 - Basic\n2 - Advanced\n3 - Exit");
            System.out.print("Enter: ");
            int choice = scanner.nextInt();

            if (choice == 3) {
                System.out.println("Exiting...");
                break;
            }

            switch (choice) {
                case 1:
                    handleCalculator(basicCalculator, "Basic Calculator");
                    break;
                case 2:
                    handleCalculator(advancedCalculator, "Advanced Calculator");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void handleCalculator(Calculator calculator, String calculatorType) {
        boolean moreCalculations = true;

        while (moreCalculations) {
            System.out.println(calculatorType + " operations:");
            calculator.printOperations();

            try {
                enterNumbersAndOperation(calculator);

                double result = calculator.calculate();
                System.out.println("Result: " + result);


                showHistory(calculator);
            } catch (ToLargeNumberException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter valid numbers.");
                scanner.nextLine();
            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }

            moreCalculations = askToContinue("More calculations? | Yes/No");
        }
    }

    public static void enterNumbersAndOperation(Calculator calculator) throws ToLargeNumberException {
        System.out.print("A: ");
        long a = getValidatedNumber();
        calculator.setA(a);

        System.out.print("Op: ");
        calculator.setOperation(scanner.next());

        System.out.print("B: ");
        long b = getValidatedNumber();
        calculator.setB(b);
    }

    public static long getValidatedNumber() throws ToLargeNumberException {
        String input = scanner.next();
        if (input.length() > 7) {
            throw new ToLargeNumberException("Number exceeds 7 digits!");
        }

        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Invalid number format!");
        }
    }

    public static void showHistory(Calculator calculator) {
        List<CalculationLog> history = calculator.getHistoryLogList();
        if (history.isEmpty()) {
            System.out.println("No calculations in history.");
        } else {
            System.out.println("History:");
            history.forEach(System.out::println);
        }
    }

    public static boolean askToContinue(String message) {
        System.out.println(message);
        String response = scanner.next();
        return response.equalsIgnoreCase("Yes");
    }
}
