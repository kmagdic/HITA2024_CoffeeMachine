package t2_tomislav.coffeemachine;

import java.sql.Timestamp;

public class TransactionLog {

    // Privatni atributi koji predstavljaju polja zapisa u bazi
    private int id; // ID zapisa u bazi
    private Timestamp date_time; // Vrijeme kada je transakcija obavljena
    private String coffeeTypeName; // Naziv vrste kave
    private String success; // Status transakcije (uspješno ili neuspješno)
    private String ingredient; // Sastojak koji nedostaje (ako transakcija nije uspjela)

    // Prazan konstruktor za potrebe čitanja podataka iz baze
    public TransactionLog() {}

    // Konstruktor za inicijalizaciju novih zapisa
    public TransactionLog(String coffeeTypeName, String success, String ingredient) {
        this.date_time = new Timestamp(System.currentTimeMillis()); // Automatski postavlja trenutno vrijeme
        this.coffeeTypeName = coffeeTypeName; // Postavlja naziv vrste kave
        this.success = success; // Postavlja status transakcije
        this.ingredient = ingredient != null ? ingredient : "null"; // Ako nema nedostajućeg sastojka, postavlja "null"
    }

    // Getter za ID zapisa
    public int getId() {
        return id;
    }

    // Setter za ID zapisa
    public void setId(int id) {
        this.id = id;
    }

    // Getter za vrijeme transakcije
    public Timestamp getDate_time() {
        return date_time;
    }

    // Setter za vrijeme transakcije
    public void setDate_time(Timestamp date_time) {
        this.date_time = date_time;
    }

    // Getter za naziv vrste kave
    public String getCoffeeTypeName() {
        return coffeeTypeName;
    }

    // Setter za naziv vrste kave
    public void setCoffeeTypeName(String coffeeTypeName) {
        this.coffeeTypeName = coffeeTypeName;
    }

    // Getter za status transakcije
    public String getSuccess() {
        return success;
    }

    // Setter za status transakcije
    public void setSuccess(String success) {
        this.success = success;
    }

    // Getter za nedostajući sastojak
    public String getIngredient() {
        return ingredient;
    }

    // Setter za nedostajući sastojak
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    // Metoda za formatiranje i ispis podataka o transakciji
    @Override
    public String toString() {
        return "TransactionLog{" +
                "id=" + id + // Ispisuje ID zapisa
                ", date_time=" + date_time + // Ispisuje vrijeme transakcije
                ", coffeeTypeName='" + coffeeTypeName + '\'' + // Ispisuje naziv vrste kave
                ", success='" + success + '\'' + // Ispisuje status transakcije
                ", ingredient='" + ingredient + '\'' + // Ispisuje nedostajući sastojak (ako postoji)
                '}';
    }
}
