package _karlo_dragan.studentmanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    private Connection connection;
    private StudentRepository studentRepository;

    EventRepository(Connection connection) {
        this.connection = connection;
    }

    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void createTable() {

        try {
            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS events (\n" +
                    "id integer PRIMARY KEY auto_increment, \n" +
                    "student_id int  NOT NULL,\n " +
                    "date_time timestamp NOT NULL,\n" +
                    "event varchar(255)  NOT NULL\n)";

            Statement st = connection.createStatement();
            st.execute(sqlCreateTable);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    public void insert(Event e) {

        String insertSql = "INSERT INTO events (student_id, date_time, event) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(insertSql);
            ps.setInt(1, e.getStudent().getId());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(e.getDatetime()));
            ps.setString(3, e.getEvent());

            ps.executeUpdate();

        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }
    }


    public List<Event> getList() {
        String sqlPrint = "SELECT * FROM events";
        List<Event> resultList = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sqlPrint);

            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setDatetime(rs.getTimestamp("date_time").toLocalDateTime());

                Student s = new Student();
                s.setId(rs.getInt("student_id"));
                e.setStudent(s);

                e.setEvent(rs.getString("event"));

                resultList.add(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
}
