package t5_marin.calculator;

import java.util.Date;

public class CalculationLog {
    private final Date date; // Timestamp
    private final String record; // Description

    public CalculationLog(String record) {
        this.record = record;
        this.date = new Date();
    }

    public String getRecord() {
        String separator = "\n----------------------\n"; // Separator between records
        return date + " " + record + separator;
    }

    @Override
    public String toString() {
        return "CalculationLog{record='" + record + "'}";
    }
}
