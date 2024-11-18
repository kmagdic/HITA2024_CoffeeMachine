package t1_mateo.coffeemachine.calculator;

public class AdvancedCalculator extends Calculator{

    public AdvancedCalculator () {
        super.operationsList.add("pow");
        super.operationsList.add("%");
    }

    public double calculate () {
        double result = 0;
        switch(operation) {
            case "pow":
                result = Math.pow(a, b);
                break;
            case "%":
                result = a % b;
                break;

        }
        return result;
    }
}
