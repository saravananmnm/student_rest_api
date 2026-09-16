package com.student.dto;



public class Mark_DTO {
    private Long id;
    private Long student_Id;
    private String student_name;
    private Long course_Id;
    private String course_name;
    private String course_code;
    private Double internalMarks;
    private Double externalMarks;
    private Double totalMarks;
    private String grade;

    public Mark_DTO() {
    }

    public Mark_DTO(Long id, Long student_Id, String student_name, Long course_Id, String course_name, String course_code, Double internalMarks, Double externalMarks, Double totalMarks, String grade) {
        this.id = id;
        this.student_Id = student_Id;
        this.student_name = student_name;
        this.course_Id = course_Id;
        this.course_name = course_name;
        this.course_code = course_code;
        this.internalMarks = internalMarks;
        this.externalMarks = externalMarks;
        this.totalMarks = totalMarks;
        this.grade = grade;
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

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public Long getCourse_Id() {
        return course_Id;
    }

    public void setCourse_Id(Long course_Id) {
        this.course_Id = course_Id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public Double getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(Double internalMarks) {
        this.internalMarks = internalMarks;
    }

    public Double getExternalMarks() {
        return externalMarks;
    }

    public void setExternalMarks(Double externalMarks) {
        this.externalMarks = externalMarks;
    }

    public Double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
