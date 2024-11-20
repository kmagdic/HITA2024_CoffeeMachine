package t5_goran.coffeemachine.calculator;

import java.util.ArrayList;
import java.util.List;

public class AdvancedCalculator extends Calculator {

    private List<CalculationLog> calculationLogList = new ArrayList<>();

    public List<CalculationLog> getHistoryLogList() {
        return calculationLogList;
    }

    public AdvancedCalculator() {
        operationsList.add("pow");
        operationsList.add("%");
    }

    public double calculate() {
        double result = 0;
        if (operation.equals("pow")) {
            result = Math.pow(a, b);
            addRecordToHistoryList(a + " pow " + b + " = " + result);
            return result;
        } else if (operation.equals("%")) {
            try{
                result = (int) a % (int) b;
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
            addRecordToHistoryList(a + " % " + b + " = " + result);
            return result;
        } else {
            return super.calculate();
        }
    }

    public void printOperations() {
        for (String s : operationsList) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public void addRecordToHistoryList(String res){
        CalculationLog calculationLog = new CalculationLog(res);
        calculationLogList.add(calculationLog);
    }
}

