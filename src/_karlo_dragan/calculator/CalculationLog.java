package _karlo_dragan.calculator;

import java.util.Date;

public class CalculationLog {
    Date date;

    private String record;

    private double a;
    private double b;
    private String op;


    public String getRecord() {
        return date + " " + record;
    }
    public CalculationLog(String record) {
        this.record = record;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "HistoryLog{" +
                "record='" + record + '\'' +
                '}';
    }
}
