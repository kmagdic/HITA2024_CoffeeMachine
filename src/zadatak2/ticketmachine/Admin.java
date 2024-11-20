package zadatak2.ticketmachine;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final List<Terminal> terminals;

    public Admin(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.terminals = new ArrayList<>();
    }

    public boolean checkUsernameAndPassword(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void addTerminal(Terminal terminal) {
        terminals.add(terminal);
        System.out.println("Terminal added: " + terminal);
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }
}