package t4_vedran.calculator;

import java.util.Scanner;

public class BasicCalculator extends Calculator {

    @Override
    public double calculate() {
        double result = 0;
        switch (operation) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b != 0) {
                    result = a / b;
                } else {
                    System.out.println("Error: Division by zero!");
                    result = Double.NaN;
                }
                break;
            default:
                System.out.println("Invalid operation");
        }
        return result;
    }

    public boolean moreCalculations(Scanner scanner) {
        System.out.print("More calculate with basic calculator| Yes/No: ");
        String response = scanner.next();
        return response.equalsIgnoreCase("Yes");
    }
}
