package t1_mateo.coffeemachine;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TransactionLog {
    private LocalDateTime date;
    private String coffeeType;
    private String action;
    private String ingredients;
    private int coffeeTypeId;

    public TransactionLog() {}

    public TransactionLog(String coffeeType, String action, String ingredients) {
        this.date = LocalDateTime.now();
        this.coffeeType = coffeeType;
        this.action = action;
        this.ingredients = ingredients;
    }
    public TransactionLog(String coffeeType, String action) {
        this.date = LocalDateTime.now();
        this.coffeeType = coffeeType;
        this.action = action;
    }

    public Timestamp getDate() {
        return Timestamp.valueOf(date);
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public String getAction() {
        return action;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setCoffeeTypeId(int coffeeTypeId) {
        this.coffeeTypeId = coffeeTypeId;
    }

    public String toString() {
        Date formattedDate = null;
        if (date instanceof LocalDateTime) {
            formattedDate = Date.from(((LocalDateTime) date).atZone(ZoneId.systemDefault()).toInstant());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDateString = sdf.format(formattedDate);

        return "Date: " + formattedDateString + ", CoffeeTypeId: " + coffeeTypeId + ", Action: " + action
                + (ingredients != null && !ingredients.isEmpty() ? ", Ingredients: " + ingredients : "");
    }

}
