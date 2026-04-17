package com.attendance.service;

public class AttendanceReport {
    private String  subjectName;
    private double  currentPercent;
    private double  targetPercent;
    private int     lecturesAttended;
    private int     lecturesHeld;
    private int     totalPlanned;
    private int     futureLectures;
    private int     plannedAbsences;
    private int     stillRequired;
    private int     canAffordToSkip;
    private boolean isAchievable;

    public AttendanceReport(String subjectName, double currentPercent, double targetPercent,
                            int lecturesAttended, int lecturesHeld, int totalPlanned,
                            int futureLectures, int plannedAbsences, int stillRequired,
                            int canAffordToSkip, boolean isAchievable) {
        this.subjectName      = subjectName;
        this.currentPercent   = currentPercent;
        this.targetPercent    = targetPercent;
        this.lecturesAttended = lecturesAttended;
        this.lecturesHeld     = lecturesHeld;
        this.totalPlanned     = totalPlanned;
        this.futureLectures   = futureLectures;
        this.plannedAbsences  = plannedAbsences;
        this.stillRequired    = stillRequired;
        this.canAffordToSkip  = canAffordToSkip;
        this.isAchievable     = isAchievable;
    }

    public String  getSubjectName()      { return subjectName; }
    public double  getCurrentPercent()   { return currentPercent; }
    public double  getTargetPercent()    { return targetPercent; }
    public int     getLecturesAttended() { return lecturesAttended; }
    public int     getLecturesHeld()     { return lecturesHeld; }
    public int     getTotalPlanned()     { return totalPlanned; }
    public int     getFutureLectures()   { return futureLectures; }
    public int     getPlannedAbsences()  { return plannedAbsences; }
    public int     getStillRequired()    { return stillRequired; }
    public int     getCanAffordToSkip()  { return canAffordToSkip; }
    public boolean isAchievable()        { return isAchievable; }
}