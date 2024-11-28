package zadatak2.ticketmachine;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
public class TerminalMain {
    public static void main(String[] args) {
        try {

            Connection connection = DriverManager.getConnection("jdbc:h2:./docs/testpatricija");

            TerminalRepository terminalRepo = new TerminalRepository(connection);

            terminalRepo.createTable();

            Terminal t1 = new Terminal("Zagreb", "Ilica 121");
            terminalRepo.insertTerminal(t1);

            Terminal t2 = new Terminal("Velika Gorica", "Zagrebačka 121");
            terminalRepo.insertTerminal(t2);

            List<Terminal> terminals = terminalRepo.getList();
            System.out.println("Terminals in DB: " + terminals);
        } catch (SQLException e) {
            throw new RuntimeException("Database connection error", e);
        }
    }
}