package TicketMachine;

import java.util.ArrayList;
import java.util.List;

public class Terminal {
    String name;
    String address;
    private final List<TicketMachine.Ticket> tickets;

    public Terminal() {
        this.tickets = new ArrayList<>();
    }

    public TicketMachine.Ticket issueTicket(String type, double price) {
        TicketMachine.Ticket ticket = new TicketMachine.Ticket();
        ticket.type = type;
        ticket.price = price;
        ticket.terminal = this;
        tickets.add(ticket);
        return ticket;
    }

    public List<TicketMachine.Ticket> getTickets() {
        return tickets;
    }

    @Override
    public String toString() {
        return "Terminal{name='" + name + "', address='" + address + "', ticketsCount=" + tickets.size() + "}";
    }
}