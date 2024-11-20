package t1_mateo.calculator;

import java.util.ArrayList;
import java.util.List;

public class AdvancedCalculator extends Calculator{

    private List<CalculationLog> calculationLogList = new ArrayList<>();

    public AdvancedCalculator () {
        super.operationsList.add("pow");
        super.operationsList.add("%");
    }

    public double calculate () {
        double result = 0;
        if (operation.equals("pow")) {
            result = Math.pow(a, b);
            addRecordToHistoryList(a + " pow " + b + " = " + result);
            return result;
        } else if (operation.equals("%")) {
            try {
            result = (int) a % (int) b;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            addRecordToHistoryList(a + " % " + b + " = " + result);
            return result;
        } else {
            result = super.calculate();
            addRecordToHistoryList(a + " " + operation + " " + b + " = " + result);
            return result;
        }
    }

    public void printOperations() {
        for (String s: operationsList) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public void addRecordToHistoryList (String result) {
        CalculationLog calculationLog = new CalculationLog(result);
        calculationLogList.add(calculationLog);
    }

    public List<CalculationLog> getCalculationLogList() {
        return calculationLogList;
    }

}
