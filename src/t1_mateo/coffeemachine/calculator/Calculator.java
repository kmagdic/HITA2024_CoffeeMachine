package t1_mateo.coffeemachine.calculator;

import java.util.List;

public class Calculator {
    protected double a;
    protected double b;
    protected String operation;

    protected List<String> operationsList;

    public Calculator() {
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
        return result;
    }
    public void printOperations(){
        operationsList.add("+");
        operationsList.add("-");
        operationsList.add("/");
        operationsList.add("*");
        System.out.println("+ - / *");
        System.out.println();
    }
}
