package t1_mateo.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    protected double a;
    protected double b;
    protected String operation;
    protected List<String> operationsList = new ArrayList<>();

    private List<CalculationLog> calculationLogList = new ArrayList<>();

    public List<CalculationLog> getCalculationLogList() {
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

    public double calculate(){
        double result = 0;
        try {
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
                    result = a / b;
                    break;
                default:
                    System.out.println("Wrong operation");
            }
        } catch (ArithmeticException e) {
            if (e.getMessage().equals("/ by zero"))
                System.out.println("Calculator doesn't support division by 0");
            else
                System.out.println("Calculator error: " + e.getMessage());
        }
        addRecordToHistoryList(a  + " " + operation + " " + b + " = " + result);
        return result;
    }

    public void printOperations(){
        for (String s: operationsList) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public void addRecordToHistoryList (String result) {
        CalculationLog calculationLog = new CalculationLog(result);
        calculationLogList.add(calculationLog);
    }
}
