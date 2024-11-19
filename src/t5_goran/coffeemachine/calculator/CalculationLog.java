package t5_goran.coffeemachine.calculator;

public class CalculationLog {
    private String record;

    public String getRecord() {
        return record;
    }
    public CalculationLog(String record) {
        this.record = record;
    }
    public String toString() {
        return "History: " + record;
    }
}
