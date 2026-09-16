package com.student.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Course_DTO {
    private Long id;
    @NotBlank(message = "Course name is required")
    private String courseName;
    @NotBlank(message = "Course code is required")
    private String courseCode;
    @NotNull(message = "Credits are required")
    @Min(value = 1, message = "Credits must be at least 1")
    private Integer credit;
    @NotNull(message = "Department ID is required")
    private Long departmentId;
    private String departmentName;

    public Course_DTO() {
    }


    public Course_DTO(Long id, String courseName, String courseCode, Integer credit, Long departmentId, String departmentName) {
        this.id = id;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.credit = credit;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
