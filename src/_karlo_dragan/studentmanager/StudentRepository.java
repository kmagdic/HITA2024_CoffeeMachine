package _karlo_dragan.studentmanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private Connection connection;

    StudentRepository(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {

        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS students (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "first_name text  NOT NULL,\n " +
                    "last_name text  NOT NULL,\n" +
                    "oib text  NOT NULL\n)";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public void insertStudent(Student s) {

        String insertSql = "INSERT INTO students (first_name, last_name, OIB) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setString(1, s.getFirstName());
            ps.setString(2, s.getLastName());
            ps.setString(3, s.getOib());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Student> getList() {
        String sqlPrint = "SELECT * FROM students";
        List<Student> resultList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setFirstName(rs.getString("first_name"));
                s.setLastName(rs.getString("last_name"));
                s.setOib(rs.getString("OIB"));

                resultList.add(s);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
