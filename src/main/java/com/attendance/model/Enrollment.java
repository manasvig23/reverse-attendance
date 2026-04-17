package com.attendance.model;

public class Enrollment {
    private int enrollmentId;
    private int studentId;
    private int subjectId;

    public Enrollment(int enrollmentId, int studentId, int subjectId) {
        this.enrollmentId = enrollmentId;
        this.studentId    = studentId;
        this.subjectId    = subjectId;
    }

    public int getEnrollmentId() { return enrollmentId; }
    public int getStudentId()    { return studentId; }
    public int getSubjectId()    { return subjectId; }

    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }
    public void setStudentId(int studentId)       { this.studentId = studentId; }
    public void setSubjectId(int subjectId)       { this.subjectId = subjectId; }
}