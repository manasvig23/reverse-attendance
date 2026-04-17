package com.attendance.dao;

import com.attendance.model.Student;
import com.attendance.util.DBConnection;

import java.sql.*;

public class StudentDAO {

    // Returns Student if credentials match, null if not found
    public Student login(String enrollmentNo, String password) {
        String sql = "SELECT * FROM students WHERE enrollment_no = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, enrollmentNo);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("enrollment_no"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    // Inserts a new student row
    public void register(Student s) {
        String sql = "INSERT INTO students (name, enrollment_no, password) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getEnrollmentNo());
            ps.setString(3, s.getPassword());
            ps.executeUpdate();
            System.out.println("✅ Student registered successfully!");

        } catch (SQLException e) {
            System.out.println("Register error: " + e.getMessage());
        }
    }
}