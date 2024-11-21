package t4_vedran.calculator_kd;


public class AdvancedCalculator extends Calculator {

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
            return super.calculate();
        }

        addRecordToHistoryList(a + " " + operation + " " + b + " = " + res);

        return res;
    }

    public void printOperations() {
        for (String s : operationsList) {
            System.out.print(s + " ");
        }
        System.out.println();
    }


}

