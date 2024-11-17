package t1_darko.calculator;

import java.util.ArrayList;
import java.util.List;

public class AdvancedCalculator extends Calculator {

    private List<CalculationLog> calculationLogs = new ArrayList<>();

    public AdvancedCalculator() {
       operationList.add("pow");
       operationList.add("%");
    }

    public double calculate() {
        double result = 0;
        switch (super.getOperation()) {
            case "pow":
                result = Math.pow(getA(), getB());
                addRecordToList(result);
                break;
            case "%":
                result = getA() % getB();
                addRecordToList(result);
                break;
            default:
                result = super.calculate();
                addRecordToList(result);
        }
        return result;
    }

    public void printOperations() {
        for (String o : operationList) {
            System.out.print(o + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void addRecordToList(double result) {
        String msg = "History: " + getA() + " " + getOperation() + " " + getB() + " = " + result;
        CalculationLog calculationLog = new CalculationLog(msg);
        calculationLogs.add(calculationLog);
    }

    public void printCalculationLogs() {
        for (CalculationLog log : calculationLogs) {
            System.out.println(log.toString());
        }
    }

}
