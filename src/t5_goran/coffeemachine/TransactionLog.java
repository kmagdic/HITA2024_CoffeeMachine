package t5_goran.coffeemachine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionLog {

    private final int id;
    private final LocalDateTime dateTime;
    private final String coffeeType;
    private final String status;
    private final String missingIngredient;

    // Constructor to initialize all attributes of the transaction log
    public TransactionLog(int id, LocalDateTime dateTime, String coffeeType, String status, String missingIngredient) {
        this.id = id;
        this.dateTime = dateTime;
        this.coffeeType = coffeeType;
        this.status = status;
        this.missingIngredient = missingIngredient;
    }

    // Method to format the transaction log into a readable string
    public String toFormattedString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);

        if (missingIngredient == null) {
            return "Date/time: " + formattedDateTime + ", coffee type: " + coffeeType + ", action: " + status;
        } else {
            return "Date/time: " + formattedDateTime + ", coffee type: " + coffeeType + ", action: " + status +
                    ", not enough: " + missingIngredient;
        }
    }
}
