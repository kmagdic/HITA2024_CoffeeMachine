package t2_patricija.coffeemachine.calculator;

public class AdvancedCalculator extends Calculator {

    public AdvancedCalculator() {
        operationList.add("pow");
        operationList.add("%");
    }

    @Override
    public double calculate() {
        double result = 0;
        if (operation.equals("pow")) {
            result = Math.pow(a, b);
        } else if (operation.equals("%")) {
            if (b != 0) {
                result = a % b;
            } else {
                System.out.println("Cannot mod by zero.");
            }
        } else {
            result = super.calculate();
        }
        return result;
    }

    @Override
    public void printOperations() {
        for (String operation : operationList) {
            System.out.print(operation + " ");
        }
        System.out.println();
    }
}