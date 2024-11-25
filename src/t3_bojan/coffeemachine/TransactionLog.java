package t3_bojan.coffeemachine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionLog {

    private final LocalDateTime dateTime;
    private final CoffeeType coffeeType;
    private final String transactionAction;
    private final String missingIngredient;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public TransactionLog(LocalDateTime dateTime, CoffeeType coffeeType, String transactionAction) {
        this.dateTime = dateTime;
        this.coffeeType = coffeeType;
        this.transactionAction = transactionAction;
        missingIngredient = null;
    }

    public TransactionLog(LocalDateTime date, CoffeeType coffeeType, String transactionStatement, String missingIngredient) {
        this.dateTime = date;
        this.coffeeType = coffeeType;
        this.transactionAction = transactionStatement;
        this.missingIngredient = missingIngredient;
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
