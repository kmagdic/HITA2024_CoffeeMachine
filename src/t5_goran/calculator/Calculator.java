package t5_goran.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    protected double a;
    protected double b;
    protected String operation;
    protected List<String> operationsList = new ArrayList<>();
    private List<CalculationLog> calculationLogList = new ArrayList<>();

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
        double result;
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
                if (b == 0) {
                    throw new ArithmeticException("Division by zero is not allowed!");
                }
                result = a / b;
                break;
            default:
                throw new UnsupportedOperationException("Operation not supported: " + operation);
        }
        addRecordToHistoryList(a + " " + operation + " " + b + " = " + result);
        return result;
    }

    public void printOperations() {
        for (String operation : operationsList) {
            System.out.print(operation + " ");
        }
        System.out.println();
    }


    public void addRecordToHistoryList(String res) {
        CalculationLog calculationLog = new CalculationLog(res);
        calculationLogList.add(calculationLog);
    }


    public List<CalculationLog> getHistoryLogList() {
        return calculationLogList;
    }
}
