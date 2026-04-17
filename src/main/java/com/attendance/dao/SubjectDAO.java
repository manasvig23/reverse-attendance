// package com.attendance.dao;

// import com.attendance.model.Subject;
// import com.attendance.util.DBConnection;

// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;

// public class SubjectDAO {

//     private static final boolean TEST_MODE = true;

//     // Get all subjects a student is enrolled in
//     public List<Subject> getSubjectsByStudent(int studentId) {
//         List<Subject> list = new ArrayList<>();

//         // 🧪 TEST MODE (no database needed)
//         if (TEST_MODE) {
//             list.add(new Subject(1, "Math", 40));
//             list.add(new Subject(2, "Science", 35));
//             list.add(new Subject(3, "English", 30));
//             return list;
//         }

//         // 💾 REAL DATABASE MODE
//         String sql = "SELECT s.subject_id, s.subject_name, s.total_lectures " +
//                      "FROM subjects s JOIN enrollments e ON s.subject_id = e.subject_id " +
//                      "WHERE e.student_id = ?";

//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement ps = conn.prepareStatement(sql)) {

//             ps.setInt(1, studentId);
//             ResultSet rs = ps.executeQuery();

//             while (rs.next()) {
//                 list.add(new Subject(
//                     rs.getInt("subject_id"),
//                     rs.getString("subject_name"),
//                     rs.getInt("total_lectures")
//                 ));
//             }

//         } catch (SQLException e) {
//             System.out.println("SubjectDAO error: " + e.getMessage());
//         }

//         return list;
//     }

//     // Get enrollment_id for a specific student+subject pair
//     public int getEnrollmentId(int studentId, int subjectId) {

//         // 🧪 TEST MODE
//         if (TEST_MODE) {
//             return 1; // fake enrollment id
//         }

//         String sql = "SELECT enrollment_id FROM enrollments WHERE student_id = ? AND subject_id = ?";

//         try (Connection conn = DBConnection.getConnection();
//              PreparedStatement ps = conn.prepareStatement(sql)) {

//             ps.setInt(1, studentId);
//             ps.setInt(2, subjectId);

//             ResultSet rs = ps.executeQuery();
//             if (rs.next()) return rs.getInt("enrollment_id");

//         } catch (SQLException e) {
//             System.out.println("getEnrollmentId error: " + e.getMessage());
//         }

//         return -1;
//     }
// }
package com.attendance.dao;

import com.attendance.model.Subject;
import com.attendance.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    // Get all subjects a student is enrolled in
    public List<Subject> getSubjectsByStudent(int studentId) {
        List<Subject> list = new ArrayList<>();
        String sql = "SELECT s.subject_id, s.subject_name, s.total_lectures " +
                     "FROM subjects s JOIN enrollments e ON s.subject_id = e.subject_id " +
                     "WHERE e.student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Subject(
                    rs.getInt("subject_id"),
                    rs.getString("subject_name"),
                    rs.getInt("total_lectures")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SubjectDAO error: " + e.getMessage());
        }
        return list;
    }

    // Get enrollment_id for a specific student+subject pair
    public int getEnrollmentId(int studentId, int subjectId) {
        String sql = "SELECT enrollment_id FROM enrollments WHERE student_id = ? AND subject_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, subjectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("enrollment_id");
        } catch (SQLException e) {
            System.out.println("getEnrollmentId error: " + e.getMessage());
        }
        return -1;
    }
}