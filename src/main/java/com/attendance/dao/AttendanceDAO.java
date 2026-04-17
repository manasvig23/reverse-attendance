package com.attendance.dao;

import com.attendance.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;

public class AttendanceDAO {

    public void markAttendance(int enrollmentId, LocalDate date, String status) {
        String sql = "INSERT INTO attendance_records (enrollment_id, date, status) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            ps.setDate(2, Date.valueOf(date));
            ps.setString(3, status);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("markAttendance error: " + e.getMessage());
        }
    }

    public int getTotalAttended(int enrollmentId) {
        String sql = "SELECT COUNT(*) FROM attendance_records WHERE enrollment_id = ? AND status = 'PRESENT'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("getTotalAttended error: " + e.getMessage());
        }
        return 0;
    }

    public int getTotalLecturesHeld(int enrollmentId) {
        String sql = "SELECT COUNT(*) FROM attendance_records WHERE enrollment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, enrollmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("getTotalLecturesHeld error: " + e.getMessage());
        }
        return 0;
    }
}