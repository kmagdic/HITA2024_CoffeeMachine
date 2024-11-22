package zadatak2.ticketmachine;

import java.util.ArrayList;
import java.util.List;

public class Terminal {
    String name;
    String address;
    private final List<Ticket> tickets;

    public Terminal() {
        this.tickets = new ArrayList<>();
    }

    public Ticket issueTicket(String type, double price) {
        Ticket ticket = new Ticket();
        ticket.type = type;
        ticket.price = price;
        ticket.terminal = this;
        tickets.add(ticket);
        return ticket;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return "Terminal{name='" + name + "', address='" + address + "', ticketsCount=" + tickets.size() + "}";
    }
}
