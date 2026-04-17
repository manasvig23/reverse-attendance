package com.attendance.model;

public class AttendanceRecord {
    private int recordId;
    private int enrollmentId;
    private String date;      // changed from LocalDate to String
    private String status;

    public AttendanceRecord() {}

    public AttendanceRecord(int recordId, int enrollmentId, String date, String status) {
        this.recordId     = recordId;
        this.enrollmentId = enrollmentId;
        this.date         = date;
        this.status       = status;
    }

    public int getRecordId()     { return recordId; }
    public int getEnrollmentId() { return enrollmentId; }
    public String getDate()      { return date; }
    public String getStatus()    { return status; }

    public void setRecordId(int recordId)         { this.recordId = recordId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }
    public void setDate(String date)              { this.date = date; }
    public void setStatus(String status)          { this.status = status; }
}