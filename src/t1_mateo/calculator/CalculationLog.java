package t1_mateo.calculator;

public class CalculationLog {
    private String record;

    public CalculationLog(String record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return record;
    }
}
