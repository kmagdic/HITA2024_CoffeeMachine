package t2_tomislav.coffeemachine;

import java.sql.Timestamp;

public class TransactionLog {
    private int id; // ID zapisa u bazi
    private Timestamp date_time;
    private String coffeeTypeName;
    private String success;
    private String ingredient;

    // Prazan konstruktor za potrebe čitanja iz baze
    public TransactionLog() {}

    // Konstruktor za inicijalizaciju novih zapisa
    public TransactionLog(String coffeeTypeName, String success, String ingredient) {
        this.date_time = new Timestamp(System.currentTimeMillis());
        this.coffeeTypeName = coffeeTypeName;
        this.success = success;
        this.ingredient = ingredient != null ? ingredient : "null";
    }

    // Getteri i setteri
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDate_time() {
        return date_time;
    }

    public void setDate_time(Timestamp date_time) {
        this.date_time = date_time;
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

    @Override
    public String toString() {
        return "TransactionLog{" +
                "id=" + id +
                ", date_time=" + date_time +
                ", coffeeTypeName='" + coffeeTypeName + '\'' +
                ", success='" + success + '\'' +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }
}
