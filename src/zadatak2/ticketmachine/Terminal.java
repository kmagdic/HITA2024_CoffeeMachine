package zadatak2.ticketmachine;

import java.util.ArrayList;
import java.util.List;

public class Terminal {
    private int id;
    String name;
    String address;
    private List<Ticket> tickets;

    public Terminal() {
    }

    public Terminal(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Ticket issueTicket(String type, double price) {
        Ticket ticket = new Ticket();
        ticket.type = type;
        ticket.price = price;
        ticket.terminal = this;
        tickets.add(ticket);
        return ticket;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return "Terminal{name='" + name + "', address='" + address + "}";
    }
}
