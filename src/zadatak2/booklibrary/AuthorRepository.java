package zadatak2.booklibrary;

import _karlo_dragan.studentmanager.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository {
     private Connection connection;

     public AuthorRepository(Connection connection) {
         this.connection = connection;
     }

     public void createTable() {
         String sqlCreateTableAuthor = "CREATE TABLE IF NOT EXISTS author (\n" +
                 "id integer PRIMARY KEY auto_increment, \n" +
                 "first_name text  NOT NULL,\n " +
                 "last_name text  NOT NULL); \n";

         Statement statement = null;
         try {
             statement = connection.createStatement();
             statement.execute(sqlCreateTableAuthor);
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
     }

     // insert
    public void insertAuthor(Author a) {
         String sqlInsert = "INSERT INTO author (first_name, last_name) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);

            preparedStatement.setString(1, a.getFirstName());
            preparedStatement.setString(2, a.getLastName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // get all
    public List<Author> getAll() {
         String sqlSelectAll = "SELECT * FROM author";
         List<Author> resultList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlSelectAll);

            while (rs.next()) {
                Author a = new Author();
                a.setId(rs.getInt("id"));
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
