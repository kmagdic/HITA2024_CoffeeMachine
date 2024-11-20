package t3_dinko.coffeemachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// HistoryLog Class
class HistoryLog {
    private String log;

    public HistoryLog(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }

    @Override
    public String toString() {
        return log;
    }
}

// Base Calculator Class
class Calculator {
    protected double a;
    protected double b;
    protected String operation;

    public Calculator(double a, double b, String operation) {
        this.a = a;
        this.b = b;
        this.operation = operation;
    }

    public double calculate() {
        switch (operation) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Unsupported operation: " + operation);
        }
    }

    public void printOperations() {
        System.out.println("Supported operations: +, -, *, /");
    }
}

// AdvancedCalculator Class
class AdvancedCalculator extends Calculator {
    private List<HistoryLog> history;

    public AdvancedCalculator(double a, double b, String operation) {
        super(a, b, operation);
        this.history = new ArrayList<>();
    }

    @Override
    public double calculate() {
        double result;
        switch (operation) {
            case "pow":
                result = Math.pow(a, b);
                break;
            case "sqrt":
                if (a < 0) {
                    throw new ArithmeticException("Square root of negative numbers is not allowed.");
                }
                result = Math.sqrt(a);
                break;
            default:
                result = super.calculate();
                break;
        }
        addHistory(a, operation, b, result);
        return result;
    }

    public void addHistory(double a, String operation, double b, double result) {
        String logEntry = a + " " + operation + " " + b + " = " + result;
        history.add(new HistoryLog(logEntry));
    }

    public void printHistory() {
        System.out.println("Calculation History:");
        for (HistoryLog log : history) {
            System.out.println(log);
        }
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AdvancedCalculator calculator = null;
        boolean running = true;

        while (running) {
            System.out.println("Enter first number (a): ");
            double a = scanner.nextDouble();

            System.out.println("Enter operation (+, -, *, /, pow): ");
            String operation = scanner.next();

            double b = 0;
            if (!operation.equals("sqrt")) {
                System.out.println("Enter second number (b): ");
                b = scanner.nextDouble();
            }

            calculator = new AdvancedCalculator(a, b, operation);

            try {
                double result = calculator.calculate();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("Do you want to perform another operation? (yes/no): ");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("no")) {
                running = false;
            }
        }

        System.out.println("\nFinal History:");
        calculator.printHistory();
        scanner.close();
    }
}
