package t4_martin.coffeemachine;

public class TransactionLog {

    private int id;
    private String datetime;
    private String coffee_type;
    private String success;
    private int amount;

    public TransactionLog(String datetime, String coffee_type, String success, int amount) {
        this.datetime = datetime;
        this.coffee_type = coffee_type;
        this.success = success;
        this.amount = amount;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getCoffee_type() {
        return coffee_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuccess() {
        return success;
    }

    public int getAmount() {
        return amount;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setCoffee_type(String coffee_type) {
        this.coffee_type = coffee_type;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TransactionLog() {
    }

    @Override
    public String toString() {
        return "TransactionLog{" +
                "id=" + id +
                ", datetime='" + datetime + '\'' +
                ", coffee_type='" + coffee_type + '\'' +
                ", success='" + success + '\'' +
                ", amount=" + amount +
                '}';
    }
}


