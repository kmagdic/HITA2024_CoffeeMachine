package t3_bojan.calculator;

public class AdvancedCalculator extends Calculator {

    double result = 0;

    public AdvancedCalculator() {

        System.out.println("Operacije osnovnog kalkulatora: \npow, %\n");

        operationsList.add("pow");
        operationsList.add("%");
    }

    public double calculate() {

        switch (operation) {
            case "pow" : {
                result = Math.pow(a, b);
                break;
            }
            case "%" : {
                if (b == 0) {
                    System.out.println("Error: Division by zero!");
                    return Double.NaN;
                }
                result = (int) a % (int) b;
                break;
            }
            default : {
                result = super.calculate();
            }
        }
        return result;
    }

    public void printOperations() {
        System.out.println(a + " " + operation + " " + b + " = " + result);
    }
}
