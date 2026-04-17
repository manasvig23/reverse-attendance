package com.attendance;

import com.attendance.dao.HolidayDAO;
import com.attendance.dao.StudentDAO;
import com.attendance.dao.SubjectDAO;
import com.attendance.dao.AttendanceDAO;
import com.attendance.model.PlannedHoliday;
import com.attendance.model.Student;
import com.attendance.model.Subject;
import com.attendance.service.AttendanceReport;
import com.attendance.service.AttendanceService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner         scanner       = new Scanner(System.in);
    static StudentDAO      studentDAO    = new StudentDAO();
    static SubjectDAO      subjectDAO    = new SubjectDAO();
    static AttendanceDAO   attendanceDAO = new AttendanceDAO();
    static HolidayDAO      holidayDAO    = new HolidayDAO();
    static AttendanceService service     = new AttendanceService();

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("      REVERSE ATTENDANCE SYSTEM");
        System.out.println("==========================================");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 0 -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    // ─── AUTH ────────────────────────────────────────────────────────────────

    static void login() {
        System.out.print("Enrollment No: ");
        String enroll = scanner.nextLine().trim();
        System.out.print("Password     : ");
        String pass = scanner.nextLine().trim();

        Student student = studentDAO.login(enroll, pass);
        if (student == null) {
            System.out.println("❌ Invalid credentials. Try again.");
        } else {
            System.out.println("\n✅ Welcome, " + student.getName() + "!");
            studentMenu(student);
        }
    }

    static void register() {
        System.out.print("Full Name     : ");
        String name = scanner.nextLine().trim();
        System.out.print("Enrollment No : ");
        String enroll = scanner.nextLine().trim();
        System.out.print("Password      : ");
        String pass = scanner.nextLine().trim();

        Student s = new Student(0, name, enroll, pass);
        studentDAO.register(s);
    }

    // ─── STUDENT MENU ────────────────────────────────────────────────────────

    static void studentMenu(Student student) {
        while (true) {
            System.out.println("\n------------------------------------------");
            System.out.println("  Hi " + student.getName() + "!");
            System.out.println("------------------------------------------");
            System.out.println("1. View Attendance Report");
            System.out.println("2. Mark Today's Attendance");
            System.out.println("3. Add Planned Holiday");
            System.out.println("4. View / Remove Planned Holidays");
            System.out.println("5. Logout");
            System.out.print("Choice: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> viewReport(student);
                case 2 -> markAttendance(student);
                case 3 -> addHoliday(student);
                case 4 -> manageHolidays(student);
                case 5 -> { System.out.println("Logged out."); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // ─── ATTENDANCE REPORT ───────────────────────────────────────────────────

    static void viewReport(Student student) {
        System.out.print("\nEnter your target attendance % (e.g. 75): ");
        double target = readDouble();

        List<AttendanceReport> reports = service.calculateAll(student.getStudentId(), target);

        if (reports.isEmpty()) {
            System.out.println("No subjects found for your account.");
            return;
        }

        for (AttendanceReport r : reports) {
            System.out.println("\n==========================================");
            System.out.println("  " + r.getSubjectName());
            System.out.println("==========================================");
            System.out.printf("  Attended   : %d / %d lectures%n",
                              r.getLecturesAttended(), r.getLecturesHeld());
            System.out.printf("  Current %%  : %.1f%%%n", r.getCurrentPercent());
            System.out.printf("  Target %%   : %.1f%%%n", r.getTargetPercent());
            System.out.println("  ----------------------------------------");
            System.out.printf("  Semester total  : %d lectures%n", r.getTotalPlanned());
            System.out.printf("  Remaining       : %d lectures%n", r.getFutureLectures());
            System.out.printf("  Planned holidays: %d%n", r.getPlannedAbsences());
            System.out.println("  ----------------------------------------");

            if (r.getLecturesHeld() == 0) {
                System.out.println("  No lectures held yet.");
            } else if (r.isAchievable()) {
                System.out.printf("  Still must attend : %d more lecture(s)%n", r.getStillRequired());
                if (r.getCanAffordToSkip() > 0) {
                    System.out.printf("  Can afford to skip: %d lecture(s)%n", r.getCanAffordToSkip());
                } else {
                    System.out.println("  Cannot afford to skip any more lectures.");
                }
                System.out.println("  Status  : ✅ TARGET ACHIEVABLE");
            } else {
                System.out.printf("  Still need: %d but only %d lectures available%n",
                                  r.getStillRequired(), r.getFutureLectures() - r.getPlannedAbsences());
                System.out.println("  Status  : ❌ TARGET NOT ACHIEVABLE");
                System.out.println("  Tip     : Consider removing some planned holidays.");
            }
        }
        System.out.println("==========================================");
    }

    // ─── MARK ATTENDANCE ─────────────────────────────────────────────────────

    static void markAttendance(Student student) {
        List<Subject> subjects = subjectDAO.getSubjectsByStudent(student.getStudentId());
        if (subjects.isEmpty()) {
            System.out.println("No subjects found.");
            return;
        }

        System.out.println("\nSelect subject:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i).getSubjectName());
        }
        System.out.print("Choice: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= subjects.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Subject chosen = subjects.get(idx);
        System.out.println("1. PRESENT");
        System.out.println("2. ABSENT");
        System.out.print("Choice: ");
        int statusChoice = readInt();
        String status = (statusChoice == 1) ? "PRESENT" : "ABSENT";

        int enrollmentId = subjectDAO.getEnrollmentId(student.getStudentId(), chosen.getSubjectId());
        attendanceDAO.markAttendance(enrollmentId, LocalDate.now(), status);
        System.out.println("✅ Marked " + status + " for " + chosen.getSubjectName() + " today.");
    }

    // ─── ADD HOLIDAY ─────────────────────────────────────────────────────────

    static void addHoliday(Student student) {
        List<Subject> subjects = subjectDAO.getSubjectsByStudent(student.getStudentId());
        if (subjects.isEmpty()) {
            System.out.println("No subjects found.");
            return;
        }

        System.out.println("\nSelect subject to add holiday for:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i).getSubjectName());
        }
        System.out.print("Choice: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= subjects.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Subject chosen = subjects.get(idx);
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = readDate();
        if (date == null) return;

        if (!date.isAfter(LocalDate.now())) {
            System.out.println("❌ Date must be in the future.");
            return;
        }

        boolean added = holidayDAO.addHoliday(student.getStudentId(),
                                               chosen.getSubjectId(), date);
        if (added) {
            System.out.println("✅ Holiday added for " + chosen.getSubjectName() + " on " + date);
        }
    }

    // ─── MANAGE HOLIDAYS ─────────────────────────────────────────────────────

    static void manageHolidays(Student student) {
        List<Subject> subjects = subjectDAO.getSubjectsByStudent(student.getStudentId());
        if (subjects.isEmpty()) {
            System.out.println("No subjects found.");
            return;
        }

        System.out.println("\nSelect subject:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i).getSubjectName());
        }
        System.out.print("Choice: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= subjects.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Subject chosen = subjects.get(idx);
        List<PlannedHoliday> holidays = holidayDAO.getHolidays(
                                            student.getStudentId(), chosen.getSubjectId());

        if (holidays.isEmpty()) {
            System.out.println("No planned holidays for " + chosen.getSubjectName());
            return;
        }

        System.out.println("\nPlanned holidays for " + chosen.getSubjectName() + ":");
        for (int i = 0; i < holidays.size(); i++) {
            System.out.println((i + 1) + ". " + holidays.get(i).getHolidayDate()
                               + "  [ID: " + holidays.get(i).getHolidayId() + "]");
        }

        System.out.print("\nEnter number to delete (or 0 to go back): ");
        int delChoice = readInt();
        if (delChoice == 0) return;
        if (delChoice < 1 || delChoice > holidays.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        holidayDAO.deleteHoliday(holidays.get(delChoice - 1).getHolidayId());
        System.out.println("✅ Holiday removed.");
    }

    // ─── INPUT HELPERS ───────────────────────────────────────────────────────

    static int readInt() {
        try {
            int val = Integer.parseInt(scanner.nextLine().trim());
            return val;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return -1;
        }
    }

    static double readDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return 75.0;
        }
    }

    static LocalDate readDate() {
        try {
            return LocalDate.parse(scanner.nextLine().trim());
        } catch (DateTimeParseException e) {
            System.out.println("❌ Invalid date format. Use YYYY-MM-DD.");
            return null;
        }
    }
}