package com.student.dto;

public class Student_Result_DTO {
    private Long studentId;

    private String studentName;

    private int totalSubjects;

    private double totalMarks;

    private double average;

    private double percentage;

    private String overallGrade;

    public Student_Result_DTO() {
    }

    public Student_Result_DTO(Long studentId, String studentName, int totalSubjects, double totalMarks, double average, double percentage, String overallGrade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.totalSubjects = totalSubjects;
        this.totalMarks = totalMarks;
        this.average = average;
        this.percentage = percentage;
        this.overallGrade = overallGrade;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getTotalSubjects() {
        return totalSubjects;
    }

    public void setTotalSubjects(int totalSubjects) {
        this.totalSubjects = totalSubjects;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getOverallGrade() {
        return overallGrade;
    }

    public void setOverallGrade(String overallGrade) {
        this.overallGrade = overallGrade;
    }
}
