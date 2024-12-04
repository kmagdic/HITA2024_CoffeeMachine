package t2_patricija.coffeemachine;

import java.time.LocalDateTime;

public class TransactionLog {
    private LocalDateTime dateTime;
    private int id;
    private CoffeeType coffeeType;
    private String success;
    private String ingredient;
    private int coffeeId; // Dodano polje za ID kave

    public TransactionLog() {
        this.id = 0;
        this.dateTime = null;
        this.coffeeType = null;
        this.success = null;
        this.ingredient = null;
    }

    public TransactionLog(LocalDateTime dateTime, CoffeeType coffeeType, String success, String ingredient) {
        this.id = 0;
        this.dateTime = dateTime;
        this.coffeeType = coffeeType;
        this.success = success;
        this.ingredient = ingredient;
    }

    // Getters i Setters za coffeeId
    public int getCoffeeId() {
        return coffeeId;
    }

    public void setCoffeeId(int coffeeId) {
        this.coffeeId = coffeeId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public CoffeeType getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(CoffeeType coffeeType) {
        this.coffeeType = coffeeType;
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
                "dateTime=" + dateTime +
                ", id=" + id +
                ", coffeeType=" + coffeeType +
                ", success='" + success + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", coffeeId=" + coffeeId + // Dodano u toString
                '}';
    }
}
