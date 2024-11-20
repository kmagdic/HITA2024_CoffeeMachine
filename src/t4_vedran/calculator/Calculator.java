package t4_vedran.calculator;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Calculator {
    protected double a;
    protected double b;
    protected String operation;
    protected List<String> operationsList;

    public Calculator() {
        // Inicijalizacija kao ArrayList za omogućavanje dinamičkog dodavanja operacija
        operationsList = new ArrayList<>();
        operationsList.add("+");
        operationsList.add("-");
        operationsList.add("*");
        operationsList.add("/");
    }

    // Setteri za postavljanje brojeva i operatora
    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setOperation(String operation) {
        if (operationsList.contains(operation)) {
            this.operation = operation;
        } else {
            System.out.println("Invalid operation.");
        }
    }

    // Apstraktna metoda koja mora biti implementirana u podklasama
    public double calculate() {
        return 0;
    }

    // Metoda za ispis operacija
    public void printOperations() {
        System.out.println("Dostupne operacije:");
        for (String op : operationsList) {
            System.out.print(op + " ");
        }
        System.out.println();
    }
}
