package zadatak2.ticketmachine;

import _karlo_dragan.studentmanager.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import static jdk.internal.org.jline.utils.Colors.s;

public class TerminalManagerInDB {

    static Connection connection;

    public static void main(String[] args) {
        Terminal t = new Terminal("Zagreb", "Ilica 121");
        System.out.println(t);

        connection = makeDBConnection("docs/testpatricija");
        createTable();

        insertTerminal(t);

        List<Terminal> terminals = getList();

        System.out.println("Terminals in DB: " + terminals);

        System.out.println("Saved!");
    }

    public static Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTable() {
        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS terminal (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "name text NOT NULL,\n" +
                    "address text NOT NULL\n" +
                    ");";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertTerminal(Terminal t) {

        String insertSql = "INSERT INTO terminal (name, address) VALUES (?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, t.getName());
            ps.setString(2, t.getAddress());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Terminal> getList() {
        String sqlPrint = "SELECT * FROM terminal";
        List<Terminal> resultList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                Terminal t = new Terminal();
                t.setId(rs.getInt("id"));
                t.setName(rs.getString("name"));
                t.setAddress(rs.getString("address"));

                resultList.add(t);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

}
