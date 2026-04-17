// package com.attendance.dao;

// import com.attendance.model.PlannedHoliday;
// import com.attendance.util.DBConnection;
// import java.sql.*;
// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// public class HolidayDAO {

//     private static final boolean TEST_MODE = true;

//     // In-memory list to simulate DB in test mode
//     private static final List<PlannedHoliday> fakeDB = new ArrayList<>();
//     private static int fakeIdCounter = 1;

//     public boolean addHoliday(int studentId, int subjectId, LocalDate date) {
//         if (TEST_MODE) {
//             fakeDB.add(new PlannedHoliday(fakeIdCounter++, studentId, subjectId, date));
//             System.out.println("  [TEST] Holiday added: " + date);
//             return true;
//         }
//         String sql = "INSERT INTO planned_holidays (student_id, subject_id, holiday_date) VALUES (?, ?, ?)";
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setInt(1, studentId);
//             ps.setInt(2, subjectId);
//             ps.setDate(3, Date.valueOf(date));
//             ps.executeUpdate();
//             return true;
//         } catch (SQLException e) {
//             System.out.println("addHoliday error: " + e.getMessage());
//             return false;
//         }
//     }

//     public int getHolidayCount(int studentId, int subjectId) {
//         if (TEST_MODE) {
//             return (int) fakeDB.stream()
//                 .filter(h -> h.getStudentId() == studentId && h.getSubjectId() == subjectId)
//                 .count();
//         }
//         String sql = "SELECT COUNT(*) FROM planned_holidays WHERE student_id = ? AND subject_id = ?";
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setInt(1, studentId);
//             ps.setInt(2, subjectId);
//             ResultSet rs = ps.executeQuery();
//             if (rs.next()) return rs.getInt(1);
//         } catch (SQLException e) {
//             System.out.println("getHolidayCount error: " + e.getMessage());
//         }
//         return 0;
//     }

//     public List<PlannedHoliday> getHolidays(int studentId, int subjectId) {
//         if (TEST_MODE) {
//             List<PlannedHoliday> result = new ArrayList<>();
//             for (PlannedHoliday h : fakeDB) {
//                 if (h.getStudentId() == studentId && h.getSubjectId() == subjectId)
//                     result.add(h);
//             }
//             return result;
//         }
//         List<PlannedHoliday> list = new ArrayList<>();
//         String sql = "SELECT * FROM planned_holidays WHERE student_id = ? AND subject_id = ?";
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setInt(1, studentId);
//             ps.setInt(2, subjectId);
//             ResultSet rs = ps.executeQuery();
//             while (rs.next()) {
//                 list.add(new PlannedHoliday(
//                     rs.getInt("holiday_id"),
//                     rs.getInt("student_id"),
//                     rs.getInt("subject_id"),
//                     rs.getDate("holiday_date").toLocalDate()
//                 ));
//             }
//         } catch (SQLException e) {
//             System.out.println("getHolidays error: " + e.getMessage());
//         }
//         return list;
//     }

//     public void deleteHoliday(int holidayId) {
//         if (TEST_MODE) {
//             fakeDB.removeIf(h -> h.getHolidayId() == holidayId);
//             System.out.println("  [TEST] Holiday deleted: id=" + holidayId);
//             return;
//         }
//         String sql = "DELETE FROM planned_holidays WHERE holiday_id = ?";
//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement ps = conn.prepareStatement(sql)) {
//             ps.setInt(1, holidayId);
//             ps.executeUpdate();
//         } catch (SQLException e) {
//             System.out.println("deleteHoliday error: " + e.getMessage());
//         }
//     }
// }
package com.attendance.dao;

import com.attendance.model.PlannedHoliday;
import com.attendance.util.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HolidayDAO {

    public boolean addHoliday(int studentId, int subjectId, LocalDate date) {
        String sql = "INSERT INTO planned_holidays (student_id, subject_id, holiday_date) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ps.setDate(3, Date.valueOf(date));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("addHoliday error: " + e.getMessage());
            return false;
        }
    }

    public int getHolidayCount(int studentId, int subjectId) {
        String sql = "SELECT COUNT(*) FROM planned_holidays WHERE student_id = ? AND subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("getHolidayCount error: " + e.getMessage());
        }
        return 0;
    }

    public List<PlannedHoliday> getHolidays(int studentId, int subjectId) {
        List<PlannedHoliday> list = new ArrayList<>();
        String sql = "SELECT * FROM planned_holidays WHERE student_id = ? AND subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new PlannedHoliday(
                    rs.getInt("holiday_id"),
                    rs.getInt("student_id"),
                    rs.getInt("subject_id"),
                    rs.getDate("holiday_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.out.println("getHolidays error: " + e.getMessage());
        }
        return list;
    }

    public void deleteHoliday(int holidayId) {
        String sql = "DELETE FROM planned_holidays WHERE holiday_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, holidayId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteHoliday error: " + e.getMessage());
        }
    }
}