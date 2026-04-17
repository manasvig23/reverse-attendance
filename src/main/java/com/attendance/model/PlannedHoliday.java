package com.attendance.model;

public class PlannedHoliday {
    private int holidayId;
    private int studentId;
    private int subjectId;
    private String holidayDate;   // changed from LocalDate to String

    public PlannedHoliday() {}

    public PlannedHoliday(int holidayId, int studentId, int subjectId, String holidayDate) {
        this.holidayId   = holidayId;
        this.studentId   = studentId;
        this.subjectId   = subjectId;
        this.holidayDate = holidayDate;
    }

    public int getHolidayId()      { return holidayId; }
    public int getStudentId()      { return studentId; }
    public int getSubjectId()      { return subjectId; }
    public String getHolidayDate() { return holidayDate; }

    public void setHolidayId(int holidayId)          { this.holidayId = holidayId; }
    public void setStudentId(int studentId)          { this.studentId = studentId; }
    public void setSubjectId(int subjectId)          { this.subjectId = subjectId; }
    public void setHolidayDate(String holidayDate)   { this.holidayDate = holidayDate; }
}