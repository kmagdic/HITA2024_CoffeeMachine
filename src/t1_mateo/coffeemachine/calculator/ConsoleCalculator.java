package t1_mateo.coffeemachine.calculator;

import java.util.Scanner;

public class ConsoleCalculator {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        AdvancedCalculator advancedCalculator = new AdvancedCalculator();

        System.out.println("Operacije osnovnog kalkultora:");

        while (true) {
            String choice;
            System.out.println("1 - Basic");
            System.out.println("2 - Advanced");
            System.out.println("3 - Exit");
            System.out.print("Enter:");
            choice = scanner.next();

            if (choice.equals("1")) {
                System.out.println(calculator.operationsList);
                enterNumbersAndOperation(calculator);
                System.out.println(calculator.calculate());
            }
            if (choice.equals("2")) {
                System.out.println(advancedCalculator.operationsList);
                enterNumbersAndOperation(calculator);
                advancedCalculator.calculate();
            }


            if (choice.equals("3")) return;
        }
    }

    public static void enterNumbersAndOperation (Calculator calculator){
        System.out.print("Num1: ");
        double a = scanner.nextDouble();
        calculator.setA(a);
        System.out.print("Num2: ");
        double b = scanner.nextDouble();
        calculator.setB(b);
        System.out.print("Enter operation: ");
        String operation = scanner.next();
        calculator.setOperation(operation);
    }
}
