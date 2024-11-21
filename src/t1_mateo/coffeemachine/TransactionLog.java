package t1_mateo.coffeemachine;

import java.util.Date;

public class TransactionLog {
    Date date;
    private String record;

    public TransactionLog(String record) {
        this.record = record;
        this.date = new Date();
    }
    public String getRecord() {
        return date + ", " + record;
    }

}
