package com.student.dto;

import com.student.entity.AttendanceStatus;

import java.time.LocalDate;

public class Attendance_DTO {
    private Long id;
    private Long student_Id;
    private LocalDate date;
    private String status;


    public Attendance_DTO(Long id, Long student_Id, LocalDate date, String status) {
        this.id = id;
        this.student_Id = student_Id;
        this.date = date;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudent_Id() {
        return student_Id;
    }

    public void setStudent_Id(Long student_Id) {
        this.student_Id = student_Id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
