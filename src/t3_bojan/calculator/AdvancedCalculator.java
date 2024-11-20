package t3_bojan.calculator;

import java.util.ArrayList;
import java.util.List;

public class AdvancedCalculator extends Calculator {

    private final List<CalculationLog> operationLogList = new ArrayList<>();
    private boolean isAdvancedUsed = false;


    public AdvancedCalculator() {
        operationsList.add("pow");
        operationsList.add("%");
    }

    @Override
    public double calculate() {
        double result = 0;

        switch (operation) {
            case "pow" : {
                result = Math.pow(a, b);
                addRecordToHistoryList(a + " pow " + b + " = " + result);
                isAdvancedUsed = true;
                break;
            }
            case "%" : {
                if (b == 0) {
                    System.out.println("Error: Division by zero!");
                    return Double.NaN;
                }
                isAdvancedUsed = true;
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

    private void addRecordToHistoryList(String inputLog) {
        CalculationLog calculationLog = new CalculationLog(inputLog);
        operationLogList.add(calculationLog);
    }

    public List<CalculationLog> getCalculationLog(){
        return operationLogList;
    }

    public boolean isAdvancedUsed() {
        return isAdvancedUsed;
    }

    public void setIsAdvancedFalse() {
        isAdvancedUsed = false;
    }
}
