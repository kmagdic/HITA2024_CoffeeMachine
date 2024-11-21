package t3_bojan.calculator;

import java.util.List;
import java.util.Scanner;

public class ConsoleCalculator {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        AdvancedCalculator advancedCalculator = new AdvancedCalculator();

        while (true) {
            String choice;
            System.out.println("1 - Simple Calculator");
            System.out.println("2 - Advanced Calculator");
            System.out.println("3 - Exit");
            System.out.print("Enter:");
            choice = scanner.next();

            if (choice.equals("3")) {
                break;
            } else if (choice.equals("1")) {
                calculator.printOperations("Simple calculator operations:\n");
                enterNumbersAndOperation(calculator);
                System.out.println(calculator.calculate());
            } else if (choice.equals("2")) {
                advancedCalculator.printOperations("Advanced calculator operations:\n");
                enterNumbersAndOperation(advancedCalculator);
                System.out.println(advancedCalculator.calculate());


                if (advancedCalculator.isAdvancedUsed()) {
                    System.out.println("History | Yes/No");
                    if (scanner.next().equals("Yes")) {
                        advancedCalculator.setIsAdvancedFalse();
                        List<CalculationLog> logList = advancedCalculator.getCalculationLog();
                        for (CalculationLog log : logList) {
                            System.out.println(log.toString());
                        }
                    }
                }
            }
        }
    }

    public static void enterNumbersAndOperation (Calculator calculator){

        System.out.print("A: ");
        calculator.setA(scanner.nextInt());
        System.out.print("Op: ");
        calculator.setOperation(scanner.next());
        System.out.print("B: ");
        calculator.setB(scanner.nextInt());
    }
}
