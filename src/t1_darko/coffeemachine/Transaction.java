package t1_darko.coffeemachine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private LocalDateTime localDateTime;
    private CoffeeType coffeeType;
    private String action;
    private String ingredients;

    public Transaction(LocalDateTime localDateTime, CoffeeType coffeeType, String action) {
        this.localDateTime = localDateTime;
        this.coffeeType = coffeeType;
        this.action = action;
    }

    public Transaction(LocalDateTime localDateTime, CoffeeType coffeeType, String action, String ingredients) {
        this.localDateTime = localDateTime;
        this.coffeeType = coffeeType;
        this.action = action;
        this.ingredients = ingredients;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public CoffeeType getCoffeeType() {
        return coffeeType;
    }

    public String getAction() {
        return action;
    }

    public String getIngredients() {
        return ingredients;
    }

    // format output
    public String toFormattedString() {
        String formattedString = "Date/time: " + DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(localDateTime) +
                ", coffee type: " + coffeeType.getName() +
                ", action" + action;
        if (ingredients != null) {
            formattedString += ", no enough ingredients: " + ingredients;
        }
        return formattedString;
    }
}
