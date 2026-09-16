package com.student.dto;

public class Attendance_Summary_DTO {
    private Long studentId;
    private int totalClasses;
    private int present;
    private int absent;
    private int late;
    private double percentage;

    public Attendance_Summary_DTO() {
    }

    public Attendance_Summary_DTO(Long studentId, int totalClasses, int present, int absent, int late, double percentage) {
        this.studentId = studentId;
        this.totalClasses = totalClasses;
        this.present = present;
        this.absent = absent;
        this.late = late;
        this.percentage = percentage;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
