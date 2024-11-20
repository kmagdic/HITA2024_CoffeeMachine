package t2_patricija.coffeemachine.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    protected double a;
    protected double b;
    protected String operation;
    protected List<String> operationList;

    public Calculator() {
        operationList = new ArrayList<>();
        operationList.add("+");
        operationList.add("-");
        operationList.add("/");
        operationList.add("*");
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
                    System.out.println("Cannot divide by zero.");
                }
                break;
            default:
                System.out.println("Wrong operation");
        }
        return result;
    }

    public void printOperations() {
        for (String operation : operationList) {
            System.out.print(operation + " ");
        }
        System.out.println();
    }
}