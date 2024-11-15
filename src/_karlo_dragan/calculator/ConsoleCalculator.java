package cofeemachinesvi.src._karlo_dragan.calculator;

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

            if (choice.equals("2")) return;
        }
    }

    public static void enterNumbersAndOperation (){

    }
}
