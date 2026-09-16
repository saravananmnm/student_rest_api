package com.student.controller;

import com.student.dto.PageResponse_DTO;
import com.student.dto.Student_DTO;
import com.student.entity.Student;
import com.student.service.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public Student_DTO create(@Valid @RequestBody Student_DTO student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/getAll")
    public PageResponse_DTO<Student_DTO> getAll( int page,int size,String sortByField,String direction) {
        return studentService.getAll(page, size, sortByField,direction);
    }

    @GetMapping("/getById/{id}")
    public Student_DTO getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }
    @PutMapping("/update")
    public Student_DTO updateStudentById(@RequestBody Student student) {
        return studentService.updateStudentById(student);
    }
    @DeleteMapping("delete/{id}")
    public String deleteStudentById(@PathVariable Long id) {
        return studentService.deleteStudentById(id);
    }
    @GetMapping("/getStudentsByDepartmentId/{id}")
    public Student_DTO getStudentsByDepartment(@PathVariable Long id){
        return studentService.findDepartmentById(id);
    }

    @GetMapping("/serachByName")
    public PageResponse_DTO<Student_DTO> searchByName(int page,int size,String keyword) {
        return studentService.searchByName(page, size, keyword);
    }
}
