package com.attendance;
import com.attendance.dao.StudentDAO;
import com.attendance.model.Student;

public class Main {
    public static void main(String[] args) {

        StudentDAO studentDAO = new StudentDAO();

        // Test login with seeded data (EN001 / 1234)
        Student s = studentDAO.login("EN001", "1234");
        if (s != null) {
            System.out.println("✅ Login works! Welcome, " + s.getName());
        } else {
            System.out.println("❌ Login failed.");
        }

        // Test register
        Student newStudent = new Student(0, "John Doe", "EN002", "5678");
        studentDAO.register(newStudent);
    }
}