package t5_marin.calculator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleCalculator {

    // Global scanner for user input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Calculator calculator = null;

        // Main loop to keep the application running until the user chooses to exit
        while (true) {
            // Display menu and choose calculator type
            System.out.print("""
                1 - Basic
                2 - Advanced
                3 - Exit
                Enter: """);

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> calculator = new Calculator(); // Basic calculator
                case 2 -> calculator = new AdvancedCalculator(); // Advanced calculator
                case 3 -> {
                    System.out.println("Exiting the calculator. Goodbye!");
                    return;
                }
                default -> {
                    System.out.println("Invalid choice. Please select again.");
                    continue;
                }
            }

            // Show supported operations for the selected calculator
            System.out.println("Supported operations:");
            calculator.printOperations();

            boolean continueUsingCalculator = true;

            // Loop for performing multiple calculations with the chosen calculator
            while (continueUsingCalculator) {

                boolean inputValid;
                do {
                    inputValid = enterNumbersAndOperation(calculator);
                } while (!inputValid);

                // Display the result of the calculation
                System.out.println("Result: " + calculator.calculate());

                // Display calculation history
                System.out.println("Calculation History:");
                calculator.getHistoryLogList().forEach(log ->
                        System.out.println("  " + log.getRecord())
                );

                // Ask user if they want to continue using the calculator
                System.out.println("Perform another calculation with this calculator (Y/N)?");
                String response = scanner.next();
                if (response.equalsIgnoreCase("N")) {
                    continueUsingCalculator = false; // Exit current calculator loop
                }
            }
        }
    }

    public static boolean enterNumbersAndOperation(Calculator calculator) {
        try {
            // Prompt for the first number
            System.out.print("Enter first number (A): ");
            calculator.setA(scanner.nextDouble());

            // Prompt for the operation
            System.out.print("Enter operation: ");
            calculator.setOperation(scanner.next());

            // Prompt for the second number
            System.out.print("Enter second number (B): ");
            calculator.setB(scanner.nextDouble());

            return true; // Input was successful
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
            scanner.next(); // Clear invalid input
            return false; // Indicate invalid input
        }
    }
}
