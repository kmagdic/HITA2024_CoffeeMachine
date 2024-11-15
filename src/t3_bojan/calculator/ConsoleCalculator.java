package t3_bojan.calculator;

import java.util.Scanner;

public class ConsoleCalculator {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Calculator calculator = new Calculator();

        System.out.println("Operacije osnovnog kalkultora:");
        System.out.println("+ - / *\n");

        while (true) {
            String choice;
            System.out.println("1- Calculate");
            System.out.println("2 - Exit");
            System.out.print("Enter:");
            choice = scanner.next();

            if (choice.equals("2")) {
                return;
            }
            else if (choice.equals("1")){
                calculator = new Calculator()
                enterNumbersAndOperation(calculator);
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
        System.out.println(calculator.calculate());
    }
}
