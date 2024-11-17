package t4_vedran.calculator;

public class HistoryLog {
    private final String operationRecord;

    public HistoryLog(String operationRecord) {
        this.operationRecord = operationRecord;
    }

    @Override
    public String toString() {
        return operationRecord;
    }
}