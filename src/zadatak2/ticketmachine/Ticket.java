package zadatak2.ticketmachine;

public class Ticket {
    String type;
    double price;
    Terminal terminal;

    @Override
    public String toString() {
        return "Ticket{type='" + type + "', price=" + price + ", terminalName=" + terminal.name + "}";
    }
}
