package t1_mateo.coffeemachine.calculator;

import java.util.List;

public class Calculator {
    private double a;
    private double b;
    private String operation;

    private List<String> operationsList;

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

    }
}
