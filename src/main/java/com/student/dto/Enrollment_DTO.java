package com.student.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class Enrollment_DTO {
    private Long id;
    @NotNull(message = "Student ID is required")
    private Long student_id;
    @NotNull(message = "Course ID is required")
    private Long course_id;
    private String course_name;
    private String student_name;
    @NotNull(message = "Enrollment date is required")
    private LocalDate enrollment_date;
    private String status;
    private String grade;
    public Enrollment_DTO() {}

    public Enrollment_DTO(Long id, Long student_id, Long course_id, String course_name, String student_name, LocalDate enrollment_date, String status, String grade) {
        this.id = id;
        this.student_id = student_id;
        this.course_id = course_id;
        this.course_name = course_name;
        this.student_name = student_name;
        this.enrollment_date = enrollment_date;
        this.status = status;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public LocalDate getEnrollment_date() {
        return enrollment_date;
    }

    public void setEnrollment_date(LocalDate enrollment_date) {
        this.enrollment_date = enrollment_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
