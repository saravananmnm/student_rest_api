package com.student.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private boolean active;
    @Column(name = "creation_at")
    private LocalDate creationAt;
    @Column(name = "update_at")
    private LocalDate updateAt;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Student> students =  new ArrayList<>();

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Course> courses =  new ArrayList<>();

    public Department() {
    }

    public Department(Long id, String name, String code, boolean active, LocalDate creationAt, LocalDate updateAt, List<Student> students, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.active = active;
        this.creationAt = creationAt;
        this.updateAt = updateAt;
        this.students = students;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getCreationAt() {
        return creationAt;
    }

    public void setCreationAt(LocalDate creationAt) {
        this.creationAt = creationAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
