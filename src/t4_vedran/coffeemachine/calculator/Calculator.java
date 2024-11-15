package t4_vedran.coffeemachine.calculator;

import java.util.List;
import java.util.Arrays;

public class Calculator {
    private double a;
    private double b;
    private String operation;

    private List<String> operationsList;

    public Calculator() {
        operationsList = Arrays.asList("+", "-", "*", "/");
    }

    // Setteri za postavljanje brojeva i operatora
    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setOperation(String operation) {
        if (operationsList.contains(operation)) {
            this.operation = operation;
        } else {
            System.out.println("Invalid operation.");
        }
    }


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
                    result = Double.NaN; // Nevalidan rezultat
                }
                break;
            default:
                System.out.println("Wrong operation");
        }
        return result;
    }


    public void printOperations() {
        System.out.println("Dostupne operacije:");
        for (String op : operationsList) {
            System.out.print(op + " ");
        }
        System.out.println();
    }
}
