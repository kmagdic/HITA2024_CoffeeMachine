package t3_bojan.calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    protected double a;
    protected double b;
    protected String operation;

    protected List<String> operationsList = new ArrayList<>();
    protected final List<CalculationLog> operationLogList = new ArrayList<>();

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

    public double calculate() throws ArithmeticException {
        double result = 0;

        switch (operation) {
            case "+":
                result = a + b;
                addRecordToHistoryList(a + " + " + b + " = " + result);
                break;
            case "-":
                result = a - b;
                addRecordToHistoryList(a + " - " + b + " = " + result);
                break;
            case "*":
                result = a * b;
                addRecordToHistoryList(a + " * " + b + " = " + result);
                break;
            case "/":
                if (b != 0) {
                    result = a / b;
                    addRecordToHistoryList(a + " / " + b + " = " + result);
                } else {
                    throw new ArithmeticException();
                }
                break;
        }
        return result;
    }

    public void printOperations(){
        String operations = "";
        for (String operation : operationsList) {
            operations += operation + " ";
        }
        System.out.println(operations + "\n");
    }

    protected void addRecordToHistoryList(String inputLog) {
        CalculationLog calculationLog = new CalculationLog(inputLog);
        operationLogList.add(calculationLog);
    }

    public List<CalculationLog> getCalculationLog(){
        return operationLogList;
    }
}
