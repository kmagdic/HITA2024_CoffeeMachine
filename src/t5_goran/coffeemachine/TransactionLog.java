package t5_goran.coffeemachine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionLog {

    private int id;
    private LocalDateTime dateTime;
    private String coffeeType;
    private String status;
    private String missingIngredient;

    public TransactionLog(int id, LocalDateTime dateTime, String coffeeType, String status, String missingIngredient) {
        this.id = id;
        this.dateTime = dateTime;
        this.coffeeType = coffeeType;
        this.status = status;
        this.missingIngredient = missingIngredient;
    }

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
