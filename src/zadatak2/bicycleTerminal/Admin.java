package zadatak2.bicycleTerminal;

import java.util.ArrayList;
import java.util.List;

public class Admin {

    private String name;
    private String lastName;
    private int age;

    List<Terminal> terminals = new ArrayList<>();

    public Admin(String name, String lastName, int age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public void addTerminal(int id, String name, int maxCapacity, String address, boolean isOpen, List<Bicycle> bicycles) {
        Terminal terminal = new Terminal(id, name, maxCapacity);
        terminals.add(terminal);
    }

    public void printTerminals() {
        for (Terminal terminal : terminals) {
            System.out.println(terminal);
        }
    }
}
