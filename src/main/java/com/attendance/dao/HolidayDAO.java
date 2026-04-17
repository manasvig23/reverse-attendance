package com.attendance.dao;

import com.attendance.model.PlannedHoliday;
import com.attendance.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HolidayDAO {

    // Add a planned holiday
    public void addHoliday(int studentId, int subjectId, String date) {
        // Check for duplicate first
        if (isDuplicate(studentId, subjectId, date)) {
            System.out.println("Holiday already exists for this date and subject.");
            return;
        }
        String sql = "INSERT INTO planned_holidays (student_id, subject_id, holiday_date) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ps.setString(3, date);
            ps.executeUpdate();
            System.out.println("Holiday added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get total number of planned holidays for a student in a subject
    public int getHolidayCount(int studentId, int subjectId) {
        String sql = "SELECT COUNT(*) FROM planned_holidays WHERE student_id = ? AND subject_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get list of all planned holidays for a student in a subject
    public List<PlannedHoliday> getHolidays(int studentId, int subjectId) {
        List<PlannedHoliday> holidays = new ArrayList<>();
        String sql = "SELECT * FROM planned_holidays WHERE student_id = ? AND subject_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PlannedHoliday h = new PlannedHoliday();
                h.setHolidayId(rs.getInt("holiday_id"));
                h.setStudentId(rs.getInt("student_id"));
                h.setSubjectId(rs.getInt("subject_id"));
                h.setHolidayDate(rs.getString("holiday_date"));
                holidays.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return holidays;
    }

    // Delete a holiday by its ID
    public void deleteHoliday(int holidayId) {
        String sql = "DELETE FROM planned_holidays WHERE holiday_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, holidayId);
            ps.executeUpdate();
            System.out.println("Holiday removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if a holiday already exists for the same date and subject
    private boolean isDuplicate(int studentId, int subjectId, String date) {
        String sql = "SELECT COUNT(*) FROM planned_holidays WHERE student_id = ? AND subject_id = ? AND holiday_date = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ps.setString(3, date);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}