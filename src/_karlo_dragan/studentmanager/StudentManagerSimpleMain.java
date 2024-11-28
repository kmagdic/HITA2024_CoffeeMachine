package _karlo_dragan.studentmanager;

import org.h2.engine.ConnectionInfo;

import javax.script.ScriptEngine;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManagerSimpleMain {


    public static void main(String[] args) {
        Student student = new Student("Janica", "Kostelic2", "4343423423423");
        System.out.println(student);

        // Db setup
        Connection connection = makeDBConnection("docs/testkarlo");
        StudentRepository studentRepository = new StudentRepository(connection);
        EventRepository eventRepository = new EventRepository(connection);
        studentRepository.createTable();
        eventRepository.createTable();


        // students in db
        studentRepository.insertStudent(student);
        List<Student> students = studentRepository.getList();
        System.out.println("Students in DB: " + students);

        // events in db
        Event e1 = new Event(LocalDateTime.now(), student, "Student sign in ");
        Event e2 = new Event(LocalDateTime.now(), student, "Student sign out");
        eventRepository.insert(e1);
        eventRepository.insert(e2);
        System.out.println("Events in DB: " + eventRepository.getList());

        System.out.println("Saved!");

    }


    public static Connection makeDBConnection(String fileName) {
        try {
            return DriverManager.getConnection("jdbc:h2:./" + fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    /*
    public static List<Course> loadStudentCourses(Student student) {
        String sqlPrint = "SELECT c.* FROM course c, student_course sc where sc.course_id=c.id and sc.student_id=" + student.getId();
        List<Student> resultList = new ArrayList<>();
    }*/
}
