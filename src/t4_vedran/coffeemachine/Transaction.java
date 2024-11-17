package t4_vedran.coffeemachine;

class Transaction  {
    private String dateTime;
    private String coffeeType;
    private String action;

    public Transaction(String dateTime, String coffeeType, String action) {
        this.dateTime = dateTime;
        this.coffeeType = coffeeType;
        this.action = action;
    }

    @Override
    public String toString() {
        return "Date/time: " + dateTime + ", coffee type: " + coffeeType + ", action: " + action;
    }
}
