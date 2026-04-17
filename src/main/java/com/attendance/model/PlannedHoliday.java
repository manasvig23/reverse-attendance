package com.attendance.model;

import java.time.LocalDate;

public class PlannedHoliday {
    private int holidayId;
    private int studentId;
    private int subjectId;
    private LocalDate holidayDate;

    public PlannedHoliday(int holidayId, int studentId, int subjectId, LocalDate holidayDate) {
        this.holidayId   = holidayId;
        this.studentId   = studentId;
        this.subjectId   = subjectId;
        this.holidayDate = holidayDate;
    }

    public int getHolidayId()         { return holidayId; }
    public int getStudentId()         { return studentId; }
    public int getSubjectId()         { return subjectId; }
    public LocalDate getHolidayDate() { return holidayDate; }

    public void setHolidayId(int holidayId)         { this.holidayId = holidayId; }
    public void setStudentId(int studentId)         { this.studentId = studentId; }
    public void setSubjectId(int subjectId)         { this.subjectId = subjectId; }
    public void setHolidayDate(LocalDate holidayDate) { this.holidayDate = holidayDate; }
}