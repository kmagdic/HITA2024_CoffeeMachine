package zadatak2.ticketmachine;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class TerminalRepository {
    private Connection connection;

    public TerminalRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS terminal (\n" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT, \n" +
                    "name TEXT NOT NULL,\n" +
                    "address TEXT NOT NULL\n" +
                    ");";
            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating terminal table", e);
        }
    }

    public void insertTerminal(Terminal t) {
        String insertSql = "INSERT INTO terminal (name, address) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
            ps.setString(1, t.getName());
            ps.setString(2, t.getAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting terminal", e);
        }
    }

    public List<Terminal> getList() {
        String sqlSelect = "SELECT * FROM terminal";
        List<Terminal> resultList = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sqlSelect)) {
            while (rs.next()) {
                Terminal t = new Terminal();
                t.setId(rs.getInt("id"));
                t.setName(rs.getString("name"));
                t.setAddress(rs.getString("address"));
                resultList.add(t);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching terminal list", e);
        }
        return resultList;
    }
}
