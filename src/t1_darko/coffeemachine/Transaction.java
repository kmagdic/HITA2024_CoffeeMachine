package t1_darko.coffeemachine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private LocalDateTime localDateTime;
    private String coffeeType;
    private String action;
    private String ingredients;

    public Transaction(LocalDateTime localDateTime, String coffeeType, String action) {
        this.localDateTime = localDateTime;
        this.coffeeType = coffeeType;
        this.action = action;
    }

    public Transaction(LocalDateTime localDateTime, String coffeeType, String action, String ingredients) {
        this.localDateTime = localDateTime;
        this.coffeeType = coffeeType;
        this.action = action;
        this.ingredients = ingredients;
    }

    // format output
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Date/time: ")
                .append(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(localDateTime))
                .append(", coffee type: ")
                .append(coffeeType)
                .append(", action: ")
                .append(action);
        if (ingredients != null) {
            sb.append(", no enough ingredients: ")
                    .append(ingredients);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "stage2.Transaction{" +
                "localDateTime=" + localDateTime +
                ", coffeeType='" + coffeeType + '\'' +
                ", action='" + action + '\'' +
                ", ingredients='" + ingredients + '\'' +
                '}';
    }
}
