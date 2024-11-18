package t4_martin.coffeemachine.calculator;

import java.util.Scanner;



public class ConsoleCalculator {



    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        Calculator calculator = new Calculator();


        System.out.println("Operacije osnovnog kalkultora:");

        while (true) {
            String choice;
            System.out.println("1- Calculate");
            System.out.println("2 - Exit");
            System.out.print("Enter:");
            choice = scanner.next();

            if (choice.equals("1")){
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
