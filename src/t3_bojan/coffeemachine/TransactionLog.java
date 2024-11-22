package t3_bojan.coffeemachine;

import java.util.ArrayList;
import java.util.List;

public class TransactionLog {

    private List<String> logList = new ArrayList<>();//sadrzaj loga>

    public TransactionLog() {
        logList.add("Transaction log:");
    }

    public void setLog(String log) {
        logList.add(log);
    }

    public List<String> getLog() {
        return logList;
    }
}
