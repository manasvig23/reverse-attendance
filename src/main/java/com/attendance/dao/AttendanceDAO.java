package com.attendance.dao;

import com.attendance.util.DBConnection;
import java.sql.*;

public class AttendanceDAO {

    // 1. Mark attendance for a student
    public void markAttendance(int enrollmentId, String date, String status) {
        String sql = "INSERT INTO attendance_records (enrollment_id, date, status) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            ps.setString(2, date);
            ps.setString(3, status);
            ps.executeUpdate();
            System.out.println("Attendance marked successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Get total lectures the student attended (PRESENT count)
    public int getTotalAttended(int enrollmentId) {
        String sql = "SELECT COUNT(*) FROM attendance_records WHERE enrollment_id = ? AND status = 'PRESENT'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 3. Get total lectures held so far (PRESENT + ABSENT)
    public int getTotalLecturesHeld(int enrollmentId) {
        String sql = "SELECT COUNT(*) FROM attendance_records WHERE enrollment_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}