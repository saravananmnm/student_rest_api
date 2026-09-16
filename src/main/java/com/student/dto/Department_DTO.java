package com.student.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Department_DTO {
    private Long department_id;
    private String department_name;
    private String department_code;
    private boolean department_active;
    private LocalDate department_create_at;
    private LocalDate department_update_at;

    public Department_DTO() {
    }

    public Department_DTO(Long department_id, String department_name, String department_code, boolean department_active, LocalDate department_create_at, LocalDate department_update_at) {
        this.department_id = department_id;
        this.department_name = department_name;
        this.department_code = department_code;
        this.department_active = department_active;
        this.department_create_at = department_create_at;
        this.department_update_at = department_update_at;
    }

    public Long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Long department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getDepartment_code() {
        return department_code;
    }

    public void setDepartment_code(String department_code) {
        this.department_code = department_code;
    }

    public boolean isDepartment_active() {
        return department_active;
    }

    public void setDepartment_active(boolean department_active) {
        this.department_active = department_active;
    }

    public LocalDate getDepartment_create_at() {
        return department_create_at;
    }

    public void setDepartment_create_at(LocalDate department_create_at) {
        this.department_create_at = LocalDate.from(LocalDateTime.now());
    }

    public LocalDate getDepartment_update_at() {
        return department_update_at;
    }

    public void setDepartment_update_at(LocalDate department_update_at) {
        this.department_update_at = LocalDate.from(LocalDateTime.now());
    }
}


