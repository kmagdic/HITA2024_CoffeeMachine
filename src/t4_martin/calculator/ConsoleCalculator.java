package t4_martin.calculator;

import java.util.Scanner;



public class ConsoleCalculator {



    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Calculator calculator;


        System.out.println("Operacije osnovnog kalkultora:");

        while (true) {
            String choice;
            System.out.println("1- Basic calculator");
            System.out.println("2 - Advanced calculator");
            System.out.println("3 - Exit");
            System.out.print("Enter:");
            choice = scanner.next();

            if (choice.equals("1")){
                calculator = new Calculator();
                enterNumbersAndOperation(calculator);
                System.out.println(calculator.calculate());

            }else if (choice.equals("2")) {
                calculator = new AdvancedCalculator();
                enterNumbersAndOperation(calculator);
                System.out.println(calculator.calculate());
            }else break;
        }
    }


    public static void enterNumbersAndOperation (Calculator calculator){
        System.out.println("Enter first number: ");
        calculator.setA(scanner.nextDouble());

        System.out.println("Enter second number: ");
        calculator.setB(scanner.nextDouble());

        System.out.println("Enter operation");
        calculator.printOperations();

        calculator.setOperation(scanner.next());

    }
}
