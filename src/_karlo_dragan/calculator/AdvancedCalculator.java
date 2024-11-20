package _karlo_dragan.calculator;


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
        double res = 0;

        if (operation.equals("pow")) {
            res = Math.pow(a, b);
        } else if (operation.equals("%")) {
            // program se dalje izvršava ako se dijeli s 0
            try {
                res = (int) a % (int) b;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            res = super.calculate();
        }

        addRecordToHiystoryList(a + " " + operation + " " + b + " = " + res);

        return res;
    }

    public void printOperations() {
        for (String s : operationsList) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public void addRecordToHiystoryList(String res){
        CalculationLog calculationLog = new CalculationLog(res);
        calculationLogList.add(calculationLog);
    }
}

