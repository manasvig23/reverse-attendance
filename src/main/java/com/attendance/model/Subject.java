package com.attendance.model;

public class Subject {
    private int subjectId;
    private String subjectName;
    private int totalLectures;

    public Subject() {}

    public Subject(int subjectId, String subjectName, int totalLectures) {
        this.subjectId     = subjectId;
        this.subjectName   = subjectName;
        this.totalLectures = totalLectures;
    }

    public int getSubjectId()      { return subjectId; }
    public String getSubjectName() { return subjectName; }
    public int getTotalLectures()  { return totalLectures; }

    public void setSubjectId(int subjectId)         { this.subjectId = subjectId; }
    public void setSubjectName(String subjectName)  { this.subjectName = subjectName; }
    public void setTotalLectures(int totalLectures) { this.totalLectures = totalLectures; }
}