package com.attendance.model;

import java.time.LocalDate;

public class AttendanceRecord {
    private int recordId;
    private int enrollmentId;
    private LocalDate date;
    private String status; // "PRESENT" or "ABSENT"

    public AttendanceRecord(int recordId, int enrollmentId, LocalDate date, String status) {
        this.recordId     = recordId;
        this.enrollmentId = enrollmentId;
        this.date         = date;
        this.status       = status;
    }

    public int getRecordId()       { return recordId; }
    public int getEnrollmentId()   { return enrollmentId; }
    public LocalDate getDate()     { return date; }
    public String getStatus()      { return status; }

    public void setRecordId(int recordId)         { this.recordId = recordId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }
    public void setDate(LocalDate date)           { this.date = date; }
    public void setStatus(String status)          { this.status = status; }
}