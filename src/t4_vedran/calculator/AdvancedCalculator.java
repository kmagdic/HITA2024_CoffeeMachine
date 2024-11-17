package t4_vedran.calculator;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class AdvancedCalculator extends Calculator {
    private final List<HistoryLog> history;

    public AdvancedCalculator() {
        operationsList.add("pow");
        operationsList.add("%");
        history = new ArrayList<>();  // Inicijalizacija liste povijesti
    }

    @Override
    public double calculate() {
        double result = 0;
        String record = "";
        switch (operation) {
            case "+":
                result= a + b ;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                if (b != 0) {
                    result = a / b;
                } else {
                    System.out.println("Error: Division by zero!");
                    result = Double.NaN;
                }
                record = a + " " + operation + " " + b + " = " + result;
                break;
            case "pow":
                result = Math.pow(a, b);  // Računanje a^b (eksponent)
                record = a + " pow " + b + " = " + result;
                break;
            case "%":
                try{
                    result = (int) a % (int) b;
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                record = a + " % " + b + " = " + result;
                break;
            default:
                System.out.println("Nepoznata operacija");
        }

        // Dodavanje zapisa u povijest
        addHistory(record);

        return result;
    }

    // Metoda za dodavanje zapisa u povijest
    public void addHistory(String record) {
        HistoryLog historyLog = new HistoryLog(record);
        history.add(historyLog);
    }

    // Metoda za ispis operacija i povijesti
    @Override
    public void printOperations() {
        System.out.println("Operations!!!");
        super.printOperations();  // Ispisujemo osnovne operacije
        System.out.println("Napredne operacije: pow, %");
    }

    // Metoda za ispis povijesti operacija
    public void printHistory() {
        System.out.print("History:\n");
        for (HistoryLog log : history) {
            System.out.println(log);
        }
    }

    public boolean moreCalculations(Scanner scanner) {
        System.out.print("More calculate with advanced calculator| Yes/No: ");
        String response = scanner.next();
        return response.equalsIgnoreCase("Yes");
    }
}