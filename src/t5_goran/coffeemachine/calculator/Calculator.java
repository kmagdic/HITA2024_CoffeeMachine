package t5_goran.coffeemachine.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    protected double a;
    protected double b;
    protected String operation;
    protected List<String> operationsList = new ArrayList<>();

    private List<CalculationLog> calculationLogList = new ArrayList<>();

    public List<CalculationLog> getHistoryLogList() {
        return calculationLogList;
    }

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
                try {
                    result = a / b;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                System.out.println("Wrong operation");
        }
        return result;
    }

    public void printOperations() {
        for (String operation : operationsList) {
            System.out.print(operation + " ");
        }
        System.out.println();
    }
}