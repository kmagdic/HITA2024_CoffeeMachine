package t5_marin.calculator;

public class AdvancedCalculator extends Calculator {

    public AdvancedCalculator() {
        operationsList.add("pow");
        operationsList.add("%");
        operationsList.add("sqrt");
        operationsList.add("abs");
        operationsList.add("log");
        operationsList.add("fact");
    }

    @Override
    public double calculate() {
        double result;
        switch (operation) {
            case "pow" -> result = calculatePower();
            case "%" -> result = calculateModulus();
            case "sqrt" -> result = calculateSquareRoot();
            case "abs" -> result = calculateAbsolute();
            case "log" -> result = calculateLogarithm();
            case "fact" -> result = calculateFactorial();
            default -> {
                return super.calculate(); // Delegate basic operations to parent
            }
        }
        addRecordToHistoryList(a + " " + operation + (operation.equals("fact") || operation.equals("sqrt") || operation.equals("abs") || operation.equals("log") ? " = " : " " + b + " = ") + result);
        return result;
    }

    private double calculatePower() {
        return Math.pow(a, b);
    }

    private double calculateModulus() {
        try {
            return (int) a % (int) b;
        } catch (ArithmeticException e) {
            System.out.println("Error: Division by zero in modulus.");
            return 0;
        }
    }

    private double calculateSquareRoot() {
        if (a < 0) {
            System.out.println("Error: Square root of negative number is not supported.");
            return 0;
        }
        return Math.sqrt(a);
    }

    private double calculateAbsolute() {
        return Math.abs(a);
    }

    private double calculateLogarithm() {
        if (a <= 0) {
            System.out.println("Error: Logarithm of non-positive number is not supported.");
            return 0;
        }
        return Math.log10(a);
    }

    private double calculateFactorial() {
        if (a < 0 || a != (int) a) {
            System.out.println("Error: Factorial is only defined for non-negative integers.");
            return 0;
        }
        int result = 1;
        for (int i = 2; i <= (int) a; i++) {
            result *= i;
        }
        return result;
    }

    @Override
    public void printOperations() {
        System.out.println("Supported operations: " + String.join(" ", operationsList));
    }
}

