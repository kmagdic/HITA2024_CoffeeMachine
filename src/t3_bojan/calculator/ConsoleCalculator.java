package t3_bojan.calculator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleCalculator {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Calculator activeCalculator = null;
        Calculator basecalculator = new Calculator();
        Calculator advancedcalculator = new AdvancedCalculator();

        while (true) {
            String choice;
            System.out.println("1 - Simple Calculator");
            System.out.println("2 - Advanced Calculator");
            System.out.println("3 - Exit");
            System.out.print("Enter:");
            choice = scanner.next();

            if (choice.equals("3")) {
                break;
            } else if (choice.equals("1")) {
                if (activeCalculator != null) {
                    if (activeCalculator instanceof AdvancedCalculator) {
                        activeCalculator = basecalculator;
                    }
                } else {
                    activeCalculator = new Calculator();
                    basecalculator = activeCalculator;
                }

            } else if (choice.equals("2")) {
                if (activeCalculator != null) {
                    if (activeCalculator instanceof Calculator) {
                        activeCalculator = advancedcalculator;
                    }
                } else {
                    activeCalculator = new AdvancedCalculator();
                    advancedcalculator = activeCalculator;
                }
            }

            while (true) {
                try {
                    enterNumbersAndOperation(activeCalculator);
                    break;
                }catch (ToBigNumberException e) {
                    System.out.println("The number is too big. Try again!");
                }catch (UnsupportedOperationException e) {
                    System.out.println("Unsupported operation. Try again!");
                }catch (InputMismatchException e) {
                    System.out.println("Invalid input. Try again!");
                    scanner.nextLine();
                }
            }

            try {
                System.out.println("Result: " + activeCalculator.calculate());
            } catch (ArithmeticException e) {
                System.out.println("Division by Zero: " + e.getMessage());
            }

            for (CalculationLog log : activeCalculator.getCalculationLog()) {
                System.out.println(log.toString());
            }
        }
    }

    public static void enterNumbersAndOperation (Calculator calculator) throws ToBigNumberException {

        calculator.printOperations();
        System.out.print("A: ");

        double numb = scanner.nextDouble();
        int value = (int) numb;

        if (String.valueOf(value).length() > 7) {
            throw new ToBigNumberException();
        }
        calculator.setA(numb);

        System.out.print("Op: ");

        String userInput = scanner.next();

        boolean operationFound = false;
        for (String operation : calculator.operationsList) {
            if (operation.equals(userInput)) {
                operationFound = true;
                break;
            }
        }
        if (!operationFound) {
            throw new UnsupportedOperationException();
        }
        calculator.setOperation(userInput);

        System.out.print("B: ");

        numb = scanner.nextDouble();
        value = (int) numb;

        if (String.valueOf(value).length() > 7) {
            throw new ToBigNumberException();
        }
        calculator.setB(numb);
    }
}
