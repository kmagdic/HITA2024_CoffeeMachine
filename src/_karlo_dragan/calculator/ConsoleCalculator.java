package _karlo_dragan.calculator;


import java.util.InputMismatchException;
import java.util.Scanner;

import java.util.Scanner;

public class ConsoleCalculator {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args)  {

        Calculator calculator = null;

        System.out.print("1 - Basic\n" +
                         "2 - Advanced\n" +
                         "Enter: ");

        // choose which calculator
        int ch = scanner.nextInt();
        if (ch == 1) {
            calculator = new Calculator();
        } else if (ch == 2) {
            calculator = new AdvancedCalculator();
        }
        System.out.println("Supported operations:");
        calculator.printOperations();

        boolean doExit = false;
        do {
            // enter values until they are correct
            boolean valuesEntered = false;
            do {
                enterNumbersAndOperation(calculator);
                valuesEntered = true;
            } while (valuesEntered == false);

            // display result
            System.out.println("Result:" + calculator.calculate());

            // display calculation log
            for (CalculationLog h : calculator.getHistoryLogList()) {
                System.out.println("History: " + h.getRecord());
            }

            System.out.println("Another calculation (Y/N) ?");
            String anotherOp = scanner.next();
            if (anotherOp.equalsIgnoreCase("N"))
                doExit = true;

        } while(doExit == false);


    }
    public static void enterNumbersAndOperation (Calculator c)  {

        System.out.print("A: ");
        c.setA(scanner.nextDouble());
        System.out.print("Op: ");
        c.setOperation(scanner.next());
        System.out.print("B: ");
        c.setB(scanner.nextDouble());

    }
}

