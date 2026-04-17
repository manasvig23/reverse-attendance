package com.attendance.dao;

import com.attendance.model.Subject;
import com.attendance.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    // Get all subjects a student is enrolled in
    public List<Subject> getSubjectsByStudent(int studentId) {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT s.subject_id, s.subject_name, s.total_lectures " +
                     "FROM subjects s " +
                     "JOIN enrollments e ON s.subject_id = e.subject_id " +
                     "WHERE e.student_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setSubjectId(rs.getInt("subject_id"));
                sub.setSubjectName(rs.getString("subject_name"));
                sub.setTotalLectures(rs.getInt("total_lectures"));
                subjects.add(sub);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    // Get total planned lectures for a subject
    public int getTotalLectures(int subjectId) {
        String sql = "SELECT total_lectures FROM subjects WHERE subject_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, subjectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("total_lectures");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get enrollment ID for a student-subject pair
    public int getEnrollmentId(int studentId, int subjectId) {
        String sql = "SELECT enrollment_id FROM enrollments WHERE student_id = ? AND subject_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("enrollment_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}