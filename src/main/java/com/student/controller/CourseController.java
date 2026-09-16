package com.student.controller;

import com.student.dto.Course_DTO;
import com.student.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create")
    public Course_DTO create(@RequestBody Course_DTO course_DTO) {
        return courseService.create(course_DTO);
    }

    @GetMapping("/getAll")
    public List<Course_DTO> list() {
        return courseService.findAll();
    }

    @GetMapping("/getByCourseCode/{courseCode}")
    public Course_DTO getCourse(@PathVariable String courseCode) {
        return courseService.findByCourseCode(courseCode);
    }
    @GetMapping("/getByDepartmentId/{departmentId}")
    public List<Course_DTO> getByDepartment( @PathVariable Long departmentId) {
        return courseService.getByDepartment(departmentId);
    }
}
