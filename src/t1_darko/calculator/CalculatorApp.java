package t1_darko.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ili CalculatorMenu ili CalculatorDriver ili Calculator
public class CalculatorApp {

    private Scanner scanner = new Scanner(System.in);
    private Calculator calculator;

    private List<CalculationLog> calculationLogs = new ArrayList<>();

    public  void run() {
        String selection = "";
        while (!selection.equals("3")) {
            displayMenu();
            selection = scanner.nextLine();
            switch (selection) {
                case "1": // basic calculator
                    calculator = new Calculator();
                    enterNumbersAndOperation();
                    break;
                case "2":
                    calculator = new AdvancedCalculator();
                    enterNumbersAndOperation();
                    break;
                case "3":
                    System.out.println("Exit Calculator App");
                    break;
                default:
                    System.out.println("Selection not found");
            }
        }
    }

    private void displayMenu() {
        System.out.println("1 - Basic");
        System.out.println("2 - Advanced");
        System.out.println("3 - Exit");
        System.out.print("Enter: ");
    }

    private void enterNumbersAndOperation() {
        boolean isInvalidNumber = true;
        do {
            try {
                String chCalculateMore = "";
                while (!chCalculateMore.equals("no")) {
                    System.out.println("Calculator operations: ");
                    calculator.printOperations();
                    System.out.print("A: ");
                    calculator.setA(parseInputAndSet(scanner.nextDouble()));
                    System.out.print("Op: ");
                    calculator.setOperation(scanner.next());
                    System.out.print("B: ");
                    calculator.setB(parseInputAndSet(scanner.nextDouble()));
                    double result = calculator.calculate();
                    System.out.println("Result: " + result);
                    addRecordToList(result);
                    System.out.println("History | Yes/No");
                    String history = scanner.next().trim().toLowerCase();
                    if (history.equals("yes")) {
                        printCalculationLogs();
                    }
                    System.out.println("\nMore calculate | Yes/No");
                    scanner.nextLine(); // clear buffer
                    chCalculateMore = scanner.nextLine().trim().toLowerCase();
                    isInvalidNumber = false;
                }
            } catch (NumberToLongException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("The input is not a number");
                scanner.nextLine();
            }
        } while (isInvalidNumber);
    }

    private double parseInputAndSet(double number) throws NumberToLongException {
        if (String.valueOf(number).length() > 7)
            throw new NumberToLongException("The number has more than 7. digits");
        return number;
    }

    private void addRecordToList(double result) {
        String msg = "History: " + calculator.getA() + " " + calculator.getOperation() + " " + calculator.getB() + " = " + result;
        CalculationLog calculationLog = new CalculationLog(msg);
        calculationLogs.add(calculationLog);
    }

    public void printCalculationLogs() {
        for (CalculationLog log : calculationLogs) {
            System.out.println(log.toString());
        }
    }
}
