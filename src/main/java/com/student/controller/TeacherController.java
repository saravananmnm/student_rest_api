package com.student.controller;

import com.student.dto.Teacher_DTO;
import com.student.entity.Student;
import com.student.entity.Teacher;
import com.student.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/getAll")
    public List<Teacher_DTO> findAll() {
        return teacherService.findAll();
    }

    @PostMapping("/create")
    public Teacher_DTO create(@RequestBody Teacher_DTO teacherDTO) {
        return teacherService.create(teacherDTO);
    }

    @GetMapping("/getByTeacherCode/{teacherCode}")
    public Teacher_DTO getStudentById(@PathVariable String TeacherCode) {
        return teacherService.findByTeacherCode(TeacherCode);
    }
}
