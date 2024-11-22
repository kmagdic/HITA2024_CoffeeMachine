package t4_martin.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    protected double a;
    protected double b;
    protected String operation;

    protected List<String> operationsList = new ArrayList<>();

    public Calculator() {
        operationsList.add("-");
        operationsList.add("*");
        operationsList.add("/");
        operationsList.add("+");

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

    public double calculate(){
        double result = 0;
        switch (operation) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                System.out.println("Wrong operation");
        }
        return result;
    }
    public void printOperations(){
        System.out.println(operationsList);
    }
}
