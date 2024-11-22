package t3_bojan.calculator;

public class AdvancedCalculator extends Calculator {

    public AdvancedCalculator() {
        operationsList.add("pow");
        operationsList.add("%");
    }

    @Override
    public double calculate() throws ArithmeticException {
        double result = 0;

        switch (operation) {
            case "pow" : {
                result = Math.pow(a, b);
                addRecordToHistoryList(a + " pow " + b + " = " + result);
                break;
            }
            case "%" : {
                if (b == 0) {
                    throw new ArithmeticException();
                }
                result = (int) a % (int) b;
                addRecordToHistoryList((int) a + " % " + (int) b + " = " + result);
                break;
            }
            default : {
                result = super.calculate();
            }
        }
        return result;
    }
}
