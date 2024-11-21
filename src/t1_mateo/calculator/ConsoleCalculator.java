package t1_mateo.calculator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleCalculator {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Calculator calculator = null;
        AdvancedCalculator ac = null;

        while (true) {
            String choice;
            System.out.println("1 - Basic");
            System.out.println("2 - Advanced");
            System.out.println("3 - Exit");
            System.out.print("Enter:");
            choice = scanner.next();

            if (choice.equals("1")) {
                calculator = new Calculator();
                while (true) {
                    System.out.println("Basic calculator operations:");
                    calculator.printOperations();
                    enterNumbersAndOperation(calculator);
                    System.out.println("Result: " + calculator.calculate());

                    System.out.println("Show history? YES/NO");
                    String history = scanner.next();
                    if (history.equalsIgnoreCase("yes")) {
                        System.out.println("History: ");
                        for (CalculationLog cl: calculator.getCalculationLogList()) {
                            System.out.println(cl);
                        }
                    }
                    System.out.println("More calculations with basic calculator? YES/NO");
                    String response = scanner.next();
                    if (response.equalsIgnoreCase("no")) break;
                }

            }
            if (choice.equals("2")) {
                while (true) {
                    if (ac == null) {
                        ac = new AdvancedCalculator();
                    }
                    calculator = ac;
                    System.out.println("Advance calculator operations:");
                    calculator.printOperations();
                    enterNumbersAndOperation(calculator);
                    System.out.println("Result: " + calculator.calculate());

                    System.out.println("Show history? YES/NO");
                    String history = scanner.next();
                    if (history.equalsIgnoreCase("yes")) {
                        System.out.println("History: ");
                        for (CalculationLog cl: ac.getCalculationLogList()) {
                            System.out.println(cl);
                        }
                    }
                    System.out.println("More calculations with advanced calculator? YES/NO");
                    String response = scanner.next();
                    if (response.equalsIgnoreCase("no")) break;
                }
            }
            if (choice.equals("3")) return;
        }
    }

    public static void enterNumbersAndOperation (Calculator calculator){
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Num1: ");
                double a = scanner.nextDouble();
                int intA = (int) a;
                if (String.valueOf(intA).length() > 7) {
                    throw new InputTooLongException("Number is too long.");
                }
                calculator.setA(a);

                System.out.print("Enter operation: ");
                String operation = scanner.next();
                boolean validOperation = false;
                for (String op : calculator.operationsList) {
                    if (operation.trim().equalsIgnoreCase(op)) {
                        calculator.setOperation(operation);
                        validOperation = true;
                        break;
                    }
                }
                if (!validOperation) {
                    throw new InputTooLongException(operation + " is not a valid operator.");
                }

                System.out.print("Num2: ");
                double b = scanner.nextDouble();
                int intB = (int) b;
                if (String.valueOf(intB).length() > 7) {
                    throw new InputTooLongException("Number is too long.");
                }
                calculator.setB(b);
                validInput = true;
            } catch (InputTooLongException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Error: Enter numbers only.");
                scanner.nextLine();
            }
        }



    }
}
