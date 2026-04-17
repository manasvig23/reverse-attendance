package com.attendance.service;

import com.attendance.dao.AttendanceDAO;
import com.attendance.dao.HolidayDAO;
import com.attendance.dao.SubjectDAO;
import com.attendance.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class AttendanceService {

    private static final boolean TEST_MODE = false;

    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final HolidayDAO    holidayDAO    = new HolidayDAO();
    private final SubjectDAO    subjectDAO    = new SubjectDAO();

    public AttendanceReport calculate(int studentId, int subjectId,
                                      String subjectName, int totalPlanned,
                                      double targetPercent) {
        if (TEST_MODE) {
            return fakeCalculate(subjectName, totalPlanned, targetPercent);
        }

        int enrollmentId    = subjectDAO.getEnrollmentId(studentId, subjectId);
        int attended        = attendanceDAO.getTotalAttended(enrollmentId);
        int lecturesHeld    = attendanceDAO.getTotalLecturesHeld(enrollmentId);
        int plannedAbsences = holidayDAO.getHolidayCount(studentId, subjectId);

        return compute(subjectName, attended, lecturesHeld, totalPlanned,
                       plannedAbsences, targetPercent);
    }

    // Runs calculate() for every subject a student is enrolled in
    public List<AttendanceReport> calculateAll(int studentId, double targetPercent) {
        List<AttendanceReport> reports = new ArrayList<>();
        List<Subject> subjects = subjectDAO.getSubjectsByStudent(studentId);
        for (Subject s : subjects) {
            reports.add(calculate(studentId, s.getSubjectId(),
                                  s.getSubjectName(), s.getTotalLectures(),
                                  targetPercent));
        }
        return reports;
    }

    // Core math — shared by real and test paths
    private AttendanceReport compute(String subjectName, int attended, int lecturesHeld,
                                     int totalPlanned, int plannedAbsences,
                                     double targetPercent) {
        double currentPercent   = lecturesHeld == 0 ? 0
                                  : Math.round((attended * 100.0 / lecturesHeld) * 10) / 10.0;
        int    futureLectures   = totalPlanned - lecturesHeld;
        int    availableFuture  = futureLectures - plannedAbsences;
        int    minTotalNeeded   = (int) Math.ceil(targetPercent / 100.0 * totalPlanned);
        int    stillRequired    = Math.max(0, minTotalNeeded - attended);
        int    canAffordToSkip  = availableFuture - stillRequired;
        boolean isAchievable    = stillRequired <= availableFuture;

        return new AttendanceReport(subjectName, currentPercent, targetPercent,
                                    attended, lecturesHeld, totalPlanned,
                                    futureLectures, plannedAbsences,
                                    stillRequired, canAffordToSkip, isAchievable);
    }

    // Fake data for TEST_MODE — simulates a realistic mid-semester scenario
    private AttendanceReport fakeCalculate(String subjectName, int totalPlanned,
                                           double targetPercent) {
        int attended        = 28;
        int lecturesHeld    = 35;
        int plannedAbsences = 3;
        return compute(subjectName, attended, lecturesHeld,
                       totalPlanned, plannedAbsences, targetPercent);
    }
}