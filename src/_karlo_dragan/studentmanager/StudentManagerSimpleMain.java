package _karlo_dragan.studentmanager;

import javax.script.ScriptEngine;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagerSimpleMain {
    static Connection connection;
    public static void main(String[] args) {

        Student student = new Student("Janica", "Kostelic2", "4343423423423");
        System.out.println(student);

        connection = makeDBConnection("docs/testkarlo");
        createTable();

        insertStudent(student);

       /* List<Student> students = getList();
        System.out.println("Students in DB: " + students);
        */
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


    public static void insertStudent(Student s) {

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


    public static List<Student> getList() {
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
