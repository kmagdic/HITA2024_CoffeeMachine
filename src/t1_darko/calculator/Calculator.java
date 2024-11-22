package t1_darko.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

    private double a;
    private double b;
    private String operation;
    protected List<String> operationList = new ArrayList<>();


    public Calculator() {
        operationList.add("+");
        operationList.add("-");
        operationList.add("*");
        operationList.add("/");
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public double calculate() {
        double result = 0;
        switch (operation) {
            case "+" :
                result = a + b;
                break;
            case "-" :
                result = a - b;
                break;
            case "*" :
                result = a * b;
                break;
            case "/" :
                result = a / b;
                break;
        }
        return result;
    }

    public void printOperations() {
        for(String s: operationList) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println();
    }

}
