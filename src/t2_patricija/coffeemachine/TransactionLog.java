package t2_patricija.coffeemachine;

import java.util.Date;

public class TransactionLog {
    Date date;
    private int id;
    String coffeeTypeName;
    String success;
    String ingredient;
    private String record;

    public TransactionLog() {
    }

    public TransactionLog(String coffeeTypeName, String success, String ingredient) {
        this.date = new Date();
        this.coffeeTypeName = coffeeTypeName;
        this.success = success;
        if (ingredient == null || ingredient.isEmpty()) {
            this.ingredient = "null";
        } else {
            this.ingredient = ingredient;
        }
    }

    public TransactionLog(String record) {
        this.record = record;
        this.date = new Date();
    }

    public String getRecord() {
        return date + record;
    }

    public java.sql.Date getDate() {
        return new java.sql.Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCoffeeTypeName() {
        return coffeeTypeName;
    }

    public void setCoffeeTypeName(String coffeeTypeName) {
        this.coffeeTypeName = coffeeTypeName;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "HistoryLog{" +
                "record='" + record + '\'' +
                '}';
    }
}
