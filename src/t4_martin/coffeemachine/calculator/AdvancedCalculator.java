package t4_martin.coffeemachine.calculator;


public class AdvancedCalculator extends Calculator {


    public AdvancedCalculator() {
        operationsList.add("%");
        operationsList.add("pow");
    }

    public double calculate(){
        if(operation.equals("%")){
            return a % b;
        } else if (operation.equals("pow")) {
            return Math.pow(a,b);
        }else return super.calculate();
    }

}
