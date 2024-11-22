package t5_goran.calculator;

public class AdvancedCalculator extends Calculator {

    public AdvancedCalculator() {
        operationsList.add("pow");
        operationsList.add("%");
    }

    @Override
    public double calculate() {
        double result;
        try {
            if (operation.equals("pow")) {
                result = Math.pow(a, b);
                addRecordToHistoryList(a + " pow " + b + " = " + result);
            } else if (operation.equals("%")) {
                if (b == 0) {
                    throw new ArithmeticException("Modulo by zero is not allowed!");
                }
                result = a % b;
                addRecordToHistoryList(a + " % " + b + " = " + result);
            } else {
                result = super.calculate();
            }
        } catch (Exception e) {
            System.out.println("Error during calculation: " + e.getMessage());
            result = Double.NaN;
        }
        return result;
    }
}
