package t2_patricija.coffeemachine;

import java.util.Date;

public class TransactionLog {
    Date date;
    private int id;
    String coffeeTypeName;
    String success;
    String ingredient;

    // We need this parameterless constructor to allow the printing of TransactionLog objects in the log list.
    // Without it, the default toString() method would be used, which may not provide useful output for logging.
    public TransactionLog() {}

    // Constructor for the TransactionLog class that initializes a new log entry.
    //We pass it as parameter for inserting into DB later
    // N.B.: We use a String for coffeeTypeName instead of a CoffeeType object
    // because we only need the coffee's name, not the entire object.
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

    // Explanation: java.util.Date includes both date and time, whereas java.sql.Date only stores the date (year, month, day).
    // java.util.Date is incompatible with SQL DATE format, which expects only the date without the time component.

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TransactionLog{" +
                "date=" + date +
                ", coffeeTypeName='" + coffeeTypeName + '\'' +
                ", success='" + success + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }

}
