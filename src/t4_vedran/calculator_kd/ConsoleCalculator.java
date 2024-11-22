package t4_vedran.calculator_kd;

import java.util.Scanner;

public class ConsoleCalculator {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Calculator calculator = null;

        while (true) {

            System.out.print("1 - Basic\n" +
                    "2 - Advanced\n" +
                    "3 - Exit\n" +
                    "Enter: ");
            
            int ch = scanner.nextInt();
            if (ch == 1) {
                calculator = new Calculator();
            } else if (ch == 2) {
                calculator = new AdvancedCalculator();
            } else if (ch == 3) return;

            System.out.println("Supported operations:");
            calculator.printOperations();

            boolean doExit = false;

            do {
                boolean valuesEntered = false;
                do {
                    enterNumbersAndOperation(calculator);
                    valuesEntered = true;
                } while (valuesEntered == false);

                // display result
                System.out.println("Result: " + calculator.calculate());

                // display calculation log
                for (CalculationLog h : calculator.getHistoryLogList()) {
                    System.out.println("History: " + h.getRecord());
                }

                System.out.println("Another calculation with this calculator  (Y/N) ?");
                String anotherOp = scanner.next();
                if (anotherOp.equalsIgnoreCase("N"))
                    doExit = true;

            } while (doExit == false);
        }

    }

    public static void enterNumbersAndOperation(Calculator c) {
        while (true) {
            try {
                System.out.print("A: ");
                if (!scanner.hasNextDouble()) {
                    System.out.println("Invalid input! Please enter a valid number for A.");
                    scanner.next();
                    continue;
                }
                double a = scanner.nextDouble();
                validateNumber(a);
                c.setA(a);


                System.out.print("Op: ");
                String op = scanner.next();
                c.setOperation(op);


                System.out.print("B: ");
                if (!scanner.hasNextDouble()) {
                    System.out.println("Invalid input! Please enter a valid number for B.");
                    scanner.next();
                    continue;
                }
                double b = scanner.nextDouble();
                validateNumber(b);
                c.setB(b);

                break;

            } catch (Exception e) {
                System.out.println("Input error: " + e.getMessage());
            }
        }
    }


    public static void validateNumber(double num) throws Exception {
        String numStr = String.valueOf(num);
        if (numStr.contains(".") && numStr.substring(numStr.indexOf(".") + 1).length() > 7) {
            throw new Exception("Number has more than 7 digits after the decimal point.");
        }
        if (numStr.length() > 7) {
            throw new Exception("Number has more than 7 digits in total.");
        }
    }
}
