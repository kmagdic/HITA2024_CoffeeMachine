package zadatak2.booklibrary;

import _karlo_dragan.studentmanager.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManagerWithDB {

    static Connection connection;

    public static void main(String[] args) {
        Author author = new Author("Ivo", "Ivić");
        Author author2 = new Author("Padre", "Pio");
        Author author3 = new Author("Don", "Bosco");

        connection = makeDBConnection("docs/testmateo");

        createTable();
        insertAuthor(author);
        insertAuthor(author2);
        insertAuthor(author3);

        for (Author a : getList()) {
            System.out.println(a.toString());
        }
    }

    public static Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:c:/" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTable() {

        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS author (\n" +
                    "first_name text  NOT NULL,\n " +
                    "last_name text  NOT NULL)";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void insertAuthor(Author a) {

        String insertSql = "INSERT INTO author (first_name, last_name) VALUES (?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, a.getFirstName());
            ps.setString(2, a.getLastName());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Author> getList() {
        String sqlPrint = "SELECT * FROM author";
        List<Author> resultList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                Author a = new Author();
                a.setFirstName(rs.getString("first_name"));
                a.setLastName(rs.getString("last_name"));

                resultList.add(a);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
