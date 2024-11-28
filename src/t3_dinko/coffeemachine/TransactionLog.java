package t3_dinko.coffeemachine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionLog {
    private int id;
    private LocalDateTime dateTime;
    private CoffeeType coffeeType;
    private String transactionAction;
    private String missingIngredient;
    private int coffeeTypeId;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public TransactionLog() {
        this.id = 0;
        this.dateTime = null;
        this.coffeeType = null;
        this.transactionAction = null;
        this.missingIngredient = null;
    }

    public TransactionLog(LocalDateTime dateTime, CoffeeType coffeeType, String transactionAction) {
        this.id = 0;
        this.dateTime = dateTime;
        this.coffeeType = coffeeType;
        this.transactionAction = transactionAction;
        missingIngredient = null;
    }

    public TransactionLog(LocalDateTime date, CoffeeType coffeeType, String transactionStatement, String missingIngredient) {
        this.id = 0;
        this.dateTime = date;
        this.coffeeType = coffeeType;
        this.transactionAction = transactionStatement;
        this.missingIngredient = missingIngredient;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public CoffeeType getCoffeeType() {
        return coffeeType;
    }

    public String getTransactionAction() {
        return transactionAction;
    }

    public String getMissingIngredient() {
        return missingIngredient;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setCoffeeType(CoffeeType coffeeType) {
        this.coffeeType = coffeeType;
    }

    public void setTransactionAction(String transactionAction) {
        this.transactionAction = transactionAction;
    }

    public void setMissingIngredient(String missingIngredient) {
        this.missingIngredient = missingIngredient;
    }

    public int getCoffeeTypeId() {
        return coffeeTypeId;
    }

    public void setCoffeeTypeId(int coffeeTypeId) {
        this.coffeeTypeId = coffeeTypeId;
    }

    @Override
    public String toString() {

        if (missingIngredient == null) {
            return "Transaction log:" +
                    "Date/time: " + dateTime.format(formatter) +
                    ", coffeeType: " + coffeeType.getName() +
                    ", action: " + transactionAction;
        } else {
            return "Transaction log:" +
                    "Date/time: " + dateTime.format(formatter) +
                    ", coffeeType: " + coffeeType.getName() +
                    ", action: " + transactionAction +
                    ", ingredients: " + missingIngredient;
        }
    }
}
