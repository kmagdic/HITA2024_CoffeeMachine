package t3_bojan.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    protected double a;
    protected double b;
    protected String operation;

    protected List<String> operationsList = new ArrayList<>();

    public Calculator() {
        operationsList.add("+");
        operationsList.add("-");
        operationsList.add("/");
        operationsList.add("*");
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
        return switch (operation) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> b == 0 ? Double.NaN : a / b;
            default -> getZeroAndWrongOperation();
        };
    }

    private double getZeroAndWrongOperation() {
        System.out.println("Wrong operation!");
        return Double.NaN;
    }

    public void printOperations(String calculatorText){
        String operations = "";
        for (String operation : operationsList) {
            operations += operation + " ";
        }
        System.out.println(calculatorText + operations + "\n");
    }
}
