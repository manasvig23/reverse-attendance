package com.attendance.model;

public class Student {
    private int studentId;
    private String name;
    private String enrollmentNo;
    private String password;

    public Student(int studentId, String name, String enrollmentNo, String password) {
        this.studentId    = studentId;
        this.name         = name;
        this.enrollmentNo = enrollmentNo;
        this.password     = password;
    }

    // Getters & Setters
    public int getStudentId()          { return studentId; }
    public String getName()            { return name; }
    public String getEnrollmentNo()    { return enrollmentNo; }
    public String getPassword()        { return password; }

    public void setStudentId(int studentId)       { this.studentId = studentId; }
    public void setName(String name)              { this.name = name; }
    public void setEnrollmentNo(String e)         { this.enrollmentNo = e; }
    public void setPassword(String password)      { this.password = password; }
}