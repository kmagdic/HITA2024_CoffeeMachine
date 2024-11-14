package zadatak2.bicycleTerminal;

import java.util.ArrayList;
import java.util.List;

public class Admin {

    private String name;
    private String lastName;
    private String username = "admin";
    private String password = "admin";
    private int age;

    List<Terminal> terminals = new ArrayList<>();
    List<Bicycle> bicycles = new ArrayList<>();

    public Admin(String name, String lastName, int age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public void addTerminal(int id, String name, int maxCapacity) {
        Terminal terminal = new Terminal(id, name, maxCapacity);
        terminals.add(terminal);
    }

    public void addTerminal(int id, String name, int maxCapacity, String address, boolean isOpen, List<Bicycle> bicycles) {
        Terminal terminal = new Terminal(id, name, maxCapacity);
        terminals.add(terminal);
    }

    public void addBicycleToTerminal(int terminalId, Bicycle bicycle) {
        if (terminals.isEmpty()) {
            System.out.println("No terminals found.");
            return;
        }

        for (Terminal terminal : terminals) {
            if (terminal.getId() == terminalId) {
                terminal.addBicycle(bicycle);
            }
        }
    }

    public void addBicycle(Bicycle bicycle) {
        bicycles.add(bicycle);
        System.out.println(bicycle + " added to bicycles.");
    }

    public void addBicycleByTypeToTerminal(int terminalId, String type) {

        if (terminals.isEmpty()) {
            System.out.println("No terminals found.");
            return;
        }
        if (bicycles.isEmpty()) {
            System.out.println("No bicycles found.");
            return;
        }

        for (Terminal terminal : terminals) {
            if (terminal.getId() == terminalId) {
                for (Bicycle bicycle : bicycles) {
                    if (bicycle.getType().equals(type)) {
                        terminal.addBicycle(bicycle);
                    }
                }
            }
        }
    }

    public void addBicycleByIdToTerminal(int terminalId, int bicycleId) {

        if (terminals.isEmpty()) {
            System.out.println("No terminals found.");
            return;
        }
        if (bicycles.isEmpty()) {
            System.out.println("No bicycles found.");
            return;
        }

        for (Terminal terminal : terminals) {
            if (terminal.getId() == terminalId) {
                for (Bicycle bicycle : bicycles) {
                    if (bicycle.getId() == bicycleId) {
                        terminal.addBicycle(bicycle);
                    }
                }
            }
        }
    }

    public void addAddress(int id, String address) {
        if (terminals.isEmpty()) {
            System.out.println("No terminals found.");
            return;
        }

        for (Terminal terminal : terminals) {
            if (terminal.getId() == id) {
                terminal.addAddress(address);
            }
        }
    }

    public void removeBicycleByType(String typeToRemove) {

        if (bicycles.isEmpty()) {
            System.out.println("No bicycles found.");
            return;
        }

        for (Bicycle bicycle : bicycles) {
            if (bicycle.getType().equals(typeToRemove)) {
                bicycles.remove(bicycle);
                break;
            }
        }
    }

    public void  removeBicycleById(int idToRemove) {

        if (bicycles.isEmpty()) {
            System.out.println("No bicycles found.");
            return;
        }

        for (Bicycle bicycle : bicycles) {
            if (bicycle.getId() == idToRemove) {
                bicycles.remove(bicycle);
                break;
            }
        }
    }

    public List<Terminal> getTerminals() {
        return terminals;
    }

    public void printBicycles() {
        for (Bicycle bicycle : bicycles) {
            System.out.println(bicycle);
        }
    }

    public void printTerminals() {
        for (Terminal terminal : terminals) {
            System.out.println(terminal);
        }
    }

    public void removeTerminalById(int idToRemove) {

        if (terminals.isEmpty()) {
            System.out.println("No terminals found.");
            return;
        }

        for (Terminal terminal : terminals) {
            if (terminal.getId() == idToRemove) {
                terminals.remove(terminal);
                break;
            }
        }
    }
}
