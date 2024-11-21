package t1_mateo.calculator;

public class AdvancedCalculator extends Calculator{

    public AdvancedCalculator () {
        super.operationsList.add("pow");
        super.operationsList.add("%");
    }

    public double calculate () {
        double result = 0;
        if (operation.equals("pow")) {
            result = Math.pow(a, b);
        } else if (operation.equals("%")) {
            try {
                result = (int) a % (int) b;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            return super.calculate();
        }
        addRecordToHistoryList(a  + " " + operation + " " + b + " = " + result);
        return result;
    }

    public void printOperations() {
        for (String s: operationsList) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

}
