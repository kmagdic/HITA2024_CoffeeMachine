package t1_darko.calculator;

public class AdvancedCalculator extends Calculator {

    public AdvancedCalculator() {
       operationList.add("pow");
       operationList.add("%");
    }

    public double calculate() {
        double result = 0;
        switch (super.getOperation()) {
            case "pow":
                result = Math.pow(getA(), getB());
                break;
            case "%":
                result = getA() % getB();
                break;
            default:
                result = super.calculate();
        }
        return result;
    }



}
