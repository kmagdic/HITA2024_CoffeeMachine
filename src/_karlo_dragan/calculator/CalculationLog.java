package _karlo_dragan.calculator;

public class CalculationLog {
    private String record;

    public String getRecord() {
        return record;
    }
    public CalculationLog(String record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "HistoryLog{" +
                "record='" + record + '\'' +
                '}';
    }
}
